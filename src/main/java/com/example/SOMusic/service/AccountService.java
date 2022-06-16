package com.example.SOMusic.service;

import com.example.SOMusic.domain.Account;

public interface AccountService {
	
	Account getAccount(String userId);

	Account getAccount(String userId, String password);
	
	Account findAccount(String phone, String email);
	
	Account findPassword(String userId, String phone);

	void insertAccount(Account account);

	void updateAccount(Account account);
	
	void deleteAccount(Account account);
}
