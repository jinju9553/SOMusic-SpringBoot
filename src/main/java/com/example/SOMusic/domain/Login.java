package com.example.SOMusic.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@SuppressWarnings("serial")
public class Login implements Serializable {
	private Account account;
	private boolean autoLogin;
	private String sessionId;
	private int sessionLimit;
	
	public Login(Account account) {
		this.account = account;
	}
}
