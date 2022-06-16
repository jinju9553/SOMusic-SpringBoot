package com.example.SOMusic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SOMusic.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

	Account findAccountByPhoneAndEmail(String phone, String email);

	Account findAccountByUserIdAndPhone(String userId, String phone);
}
