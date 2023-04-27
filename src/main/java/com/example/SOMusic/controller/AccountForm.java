package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Account;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@SuppressWarnings("serial")
public class AccountForm implements Serializable {

    private Account account;
    private boolean newAccount;
    private String repeatedPassword;

    public AccountForm(Account account) {
        this.account = account;
        this.newAccount = false;
    }

    public AccountForm() {
        this.account = new Account();
        this.newAccount = true;
    }

    public boolean isNewAccount() {
        return newAccount;
    }
}
