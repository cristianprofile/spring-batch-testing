package test;

import com.mymoid.batch.configuration.BatchConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({BatchTestConfig.class, BatchConfiguration.class})
public class TestServiceConfigIT {
}
