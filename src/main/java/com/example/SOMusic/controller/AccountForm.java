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
	
	public AccountForm(Account account) { //회원정보 수정용 생성자
		this.account = account;
		this.newAccount = false;
	}

	public AccountForm() { //회원정보 등록용 생성자
		this.account = new Account();
		this.newAccount = true;
	}
	
	public boolean isNewAccount() {
		return newAccount;
	}
}
