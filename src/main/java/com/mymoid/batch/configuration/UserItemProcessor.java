package com.mymoid.batch.configuration;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.springframework.batch.item.ItemProcessor;

import static java.nio.charset.Charset.forName;

public class UserItemProcessor implements ItemProcessor<User, Transaction> {

    private int cont=0;

    @Override
    public Transaction process(User user) throws Exception {

        EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .charset(forName("UTF-8"))
                .stringLengthRange(5, 50)
                .collectionSizeRange(1, 1)
                .scanClasspathForConcreteTypes(true)
                .overrideDefaultInitialization(false)
                .build();
        Transaction transaction = random.nextObject(Transaction.class);


        System.out.println("transaction = " + transaction);

        if (cont == 0) {
            transaction.setFileName("pepe11.csv");
        }
        else {
            transaction.setFileName("pepe22.csv");
        }
        cont++;
        return transaction;
    }
}
