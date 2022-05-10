package com.example.SOMusic.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class Login {
	private String userId;
	private String password;
	private boolean autoLogin;
	private String sessionId;
	private int sessionLimit;
}
