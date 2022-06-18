package com.example.SOMusic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.dao.AccountDao;
import com.example.SOMusic.domain.Account;
import com.example.SOMusic.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public Account getAccount(String userId) {
		return accountDao.getAccount(userId); //회원가입 이력 확인
	}

	@Override
	public Account getAccount(String userId, String password) {
		return accountDao.getAccount(userId, password); //로그인
	}

	@Override
	public Account findAccount(String phone, String email) { //아이디 찾기
		return accountRepository.findAccountByPhoneAndEmail(phone, email);
	}
	
	@Override
	public Account findPassword(String userId, String phone) { //비밀번호 찾기
		return accountRepository.findAccountByUserIdAndPhone(userId, phone);
	}
	
	@Override
	public void insertAccount(Account account) {
		accountDao.insertAccount(account); //회원 가입
	}

	@Override
	public void updateAccount(Account account) {
		accountDao.updateAccount(account); //회원 정보 수정
	}

	@Override
	public void deleteAccount(Account account) {
		accountDao.removeAccount(account); //회원 탈퇴
	}

	@Override
	public void updatePassword(Account account, String password) {
		accountDao.updatePassword(account, password); //비밀번호 수정
	}
}
