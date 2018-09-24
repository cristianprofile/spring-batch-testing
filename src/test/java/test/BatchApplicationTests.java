package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchApplicationTests {


   @Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;



    private static final String EXPECTED_FILE = "src/test/resources/users.csv";
    private static final String OUTPUT_FILE = "src/main/resources//users.csv";
    private static final String EXPECTED_FILE2 = "src/test/resources/users2.csv";
    private static final String OUTPUT_FILE2 = "src/main/resources//users2.csv";

	@Test
	public void contextLoads()  throws Exception{
//		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
       AssertFile.assertFileEquals(new FileSystemResource(EXPECTED_FILE),
                new FileSystemResource(OUTPUT_FILE));

        AssertFile.assertFileEquals(new FileSystemResource(EXPECTED_FILE2),
                new FileSystemResource(OUTPUT_FILE2));

	}


}
