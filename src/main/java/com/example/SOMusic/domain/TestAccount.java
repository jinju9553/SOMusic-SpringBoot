package com.example.SOMusic.domain;

public class TestAccount {

    private static Account account = new Account();

    public static Account createTestAccount(){
        account.setUserId("mark123");
        account.setUserName("mark");
        account.setEmail("mark123@gmail.com");
        account.setPhone("01012345678");

        return account;
    }

    public static Account createAnotherTestAccount(){
        account.setUserId("amy1234");
        account.setUserName("amy");
        account.setEmail("amy1234@gmail.com");
        account.setPhone("01011112222");

        return account;
    }
}
