package com.example.SOMusic.controller;

import java.io.Serializable;

import com.example.SOMusic.domain.Account;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@SuppressWarnings("serial")
public class AccountForm implements Serializable {

	private Account account;
	private boolean newAccount;
	private String repeatedPassword;
}
