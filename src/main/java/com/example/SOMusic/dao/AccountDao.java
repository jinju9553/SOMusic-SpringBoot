package com.example.SOMusic.dao;

import com.example.SOMusic.domain.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;

@Repository
public class AccountDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Account getAccount(String userId) throws DataAccessException {
        return em.find(Account.class, userId);
    }

    @Transactional
    public Account getAccount(String userId, String password)
            throws DataAccessException {
        TypedQuery<Account> query = em.createQuery(
                "select a from Account a "
                        + "where a.userId=:id and a.password=:pw",
                Account.class);
        query.setParameter("id", userId);
        query.setParameter("pw", password);
        Account account = null;
        try {
            account = query.getSingleResult();
            System.out.println(account);
        } catch (NoResultException ex) {
            return null;
        }
        return account;
    }

    @Transactional
    public void insertAccount(Account account) throws DataAccessException {
        em.persist(account);
    }

    @Transactional
    public void updateAccount(Account account) throws DataAccessException {
        em.merge(account);
    }

    @Transactional
    public void removeAccount(Account account) {
        Account a = em.find(Account.class, account.getUserId());
        em.remove(a);
    }

    @Transactional
    public void updatePassword(Account account, String password) throws DataAccessException {
        Query query = em.createNativeQuery("UPDATE Account a SET a.password = :pw WHERE a.user_id = :id");
        query.setParameter("id", account.getUserId());
        query.setParameter("pw", password);
        query.executeUpdate();
    }
}
