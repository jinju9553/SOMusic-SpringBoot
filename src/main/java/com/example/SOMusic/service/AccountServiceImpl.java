package com.example.SOMusic.service;

import com.example.SOMusic.dao.AccountDao;
import com.example.SOMusic.domain.Account;
import com.example.SOMusic.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountDao accountDao;

    @Override
    public Account getAccount(String userId) {
        return accountDao.getAccount(userId);
    }

    @Override
    public Account getAccount(String userId, String password) {
        return accountDao.getAccount(userId, password);
    }

    @Override
    public Account findAccount(String phone, String email) {
        return accountRepository.findAccountByPhoneAndEmail(phone, email);
    }

    @Override
    public Account findPassword(String userId, String phone) {
        return accountRepository.findAccountByUserIdAndPhone(userId, phone);
    }

    @Override
    public void insertAccount(Account account) {
        accountDao.insertAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Account account) {
        accountDao.removeAccount(account);
    }

    @Override
    public void updatePassword(Account account, String password) {
        accountDao.updatePassword(account, password);
    }

    @Override
    public boolean isDuplicated(String userId) {
        return accountRepository.existsById(userId);
    }
}
