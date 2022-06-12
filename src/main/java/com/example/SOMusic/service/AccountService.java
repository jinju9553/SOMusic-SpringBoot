package com.example.SOMusic.service;

import com.example.SOMusic.domain.Account;

public interface AccountService {
	
	Account getAccount(String username);

	Account getAccount(String username, String password);

	void insertAccount(Account account);

	void updateAccount(Account account);
}
