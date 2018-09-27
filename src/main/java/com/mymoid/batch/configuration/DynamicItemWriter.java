package com.mymoid.batch.configuration;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.UrlResource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicItemWriter  implements ItemStream, ItemWriter<Transaction> {

    private Map<String, FlatFileItemWriter<Transaction>> writers = new HashMap<>();

    private LineAggregator<Transaction> lineAggregator;

    private ExecutionContext executionContext;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
    }


    @Override
    public void close() throws ItemStreamException {
        for(FlatFileItemWriter f:writers.values()){
            f.close();
        }
    }

    @Override
    public void write(List<? extends Transaction> items) throws Exception {
        for (Transaction item : items) {
            FlatFileItemWriter<Transaction> ffiw = getFlatFileItemWriter(item);
            ffiw.write(Arrays.asList(item));
        }
    }



    //write item to custom file (xxx.csv)

    public FlatFileItemWriter<Transaction> getFlatFileItemWriter(Transaction item) throws IOException {
        String key = item.getFileName();
        FlatFileItemWriter<Transaction> itemWriter = writers.get(key);
        if(itemWriter == null){
            itemWriter = getTransactionFlatFileItemWriter(key);

            try {
                UrlResource resource = new UrlResource("file:"+key);
                itemWriter.setResource(resource);
                itemWriter.open(executionContext);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            writers.put(key, itemWriter);
            //itemWriter.afterPropertiesSet();
        }
        return itemWriter;
    }

    //extract custom flat file with custom mapper

    private FlatFileItemWriter<Transaction> getTransactionFlatFileItemWriter(String key) throws IOException {
        FlatFileItemWriter<Transaction> flatFileItemWriter;
        if (key.contains("pepe11"))
        {

           flatFileItemWriter= getFlatFile("columna1,columna2",new String[] { "createdAt", "concept" });
        }
        else
        {
            flatFileItemWriter= getFlatFile("columna1",new String[] { "concept"});

        }
        return flatFileItemWriter;
    }

    private FlatFileItemWriter getFlatFile(String header,String[] names) throws IOException {
        FlatFileItemWriter<Transaction> rr;
        rr = new FlatFileItemWriter<>();
        FlatFileHeaderCallback headerCallBack= writer -> writer.write(header);
        rr.setHeaderCallback(headerCallBack);
        rr.setLineAggregator(new DelimitedLineAggregator<Transaction>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<Transaction>() {{
                setNames(names);
            }});
        }});
        return rr;
    }

    public LineAggregator<Transaction> getLineAggregator() {
        return lineAggregator;
    }

    public void setLineAggregator(LineAggregator<Transaction> lineAggregator) {
        this.lineAggregator = lineAggregator;
    }
}
