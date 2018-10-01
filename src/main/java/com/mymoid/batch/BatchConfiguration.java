package com.mymoid.batch;

import com.mymoid.batch.configuration.DynamicItemWriter;
import com.mymoid.batch.configuration.OrderWriter;
import com.mymoid.batch.configuration.Transaction;
import com.mymoid.batch.configuration.User;
import com.mymoid.batch.configuration.UserItemProcessor;
import com.mymoid.batch.configuration.UserRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Value("${file.report.directory}")
    private String fileDirectory;

    @Value("${file.report.server}")
    private  String server;
    @Value("${file.report.port}")
    private  int port;
    @Value("${file.report.user}")
    private  String user;
    @Value("${file.report.password}")
    private  String password;



    @Bean
    public JdbcCursorItemReader<User> reader(){
        JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<User>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id,name FROM user");
        reader.setRowMapper(new UserRowMapper());
        return reader;
    }



    @Bean
    public UserItemProcessor processor(){
        return new UserItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<User> writer(){
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();

        final FileSystemResource fileSystemResource = new FileSystemResource("/Users/mymoid/projects/batch/src/main" +
                "/resources/users.csv");

        FlatFileHeaderCallback prueba= new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write("columna1,cloumna2");
            }
        };
        writer.setHeaderCallback(prueba);
        writer.setResource(fileSystemResource);
        writer.setLineAggregator(new DelimitedLineAggregator<User>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<User>() {{
                setNames(new String[] { "address.id", "address.name" });
            }});
        }});

        return writer;
    }



    @Bean
    public DynamicItemWriter dynamicItemWriter(){
        DynamicItemWriter writer = new DynamicItemWriter(fileDirectory);
        writer.setLineAggregator(new DelimitedLineAggregator<Transaction>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<Transaction>() {{
                setNames(new String[] { "address.id", "address.name" });
            }});
        }});

        return writer;
    }



    @Bean
    public FlatFileItemWriter<User> writer2(){
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
        final FileSystemResource fileSystemResource = new FileSystemResource("/Users/mymoid/projects/batch/src/main" +
                "/resources/users2.csv");

        FlatFileHeaderCallback prueba= new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write("columna1");
            }
        };
        writer.setHeaderCallback(prueba);
        writer.setResource(fileSystemResource);
        writer.setLineAggregator(new DelimitedLineAggregator<User>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<User>() {{
                setNames(new String[] { "id"});
            }});
        }});

        return writer;
    }


    @Bean
    public CompositeItemWriter compositeItemWritercomposite(){
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
        writer.setResource(new ClassPathResource("users2.csv"));
        writer.setLineAggregator(new DelimitedLineAggregator<User>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<User>() {{
                setNames(new String[] { "id"});
            }});
        }});

        final FlatFileItemWriter writer1 = writer();
        final FlatFileItemWriter<User>  userFlatFileItemWriter = writer2();
        List<ItemWriter<User>> writers = new ArrayList<>(2);
        writers.add(writer1);
        writers.add(userFlatFileItemWriter);
        CompositeItemWriter itemWriter = new CompositeItemWriter();

        itemWriter.setDelegates(writers);
        return itemWriter;
    }


    @Bean
    OrderWriter orderWriter()
    {
       return new OrderWriter(fileDirectory);
    }




    @Bean
    public Step step1() {

        final UserItemProcessor processor = processor();
        return stepBuilderFactory.get("step1").<User, Transaction> chunk(2)
                .reader(reader())
                .processor(processor)
                .writer(dynamicItemWriter())
                .build();
    }

    @Bean
    public Job exportUserJob() {
        return jobBuilderFactory.get("exportUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }



    @Bean
    public FtpClient FtpClient()
    {
        return new FtpClient(server, port, user, password);
    }




}
