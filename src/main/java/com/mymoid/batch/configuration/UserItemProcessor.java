package com.mymoid.batch.configuration;

import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) throws Exception {
        System.out.println("user = " + user);
        Address address =new Address();
        address.setId(110);
        address.setName("namemio");
        user.setAddress(address);


        return user;
    }
}
