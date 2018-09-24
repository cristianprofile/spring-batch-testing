# spring-batch-testing
Spring Batch integration testing simple job example.

Using @SpringBootTest the entire batch application runs When I test method is executed.You must
disable this feature using application.properties inside test/resources using this property value:

         spring.batch.job.enabled=false

