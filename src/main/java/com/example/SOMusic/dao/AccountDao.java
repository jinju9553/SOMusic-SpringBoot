package com.example.SOMusic.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.SOMusic.domain.Account;

@Repository
public class AccountDao {
	@PersistenceContext //entity manager의 bean 주입 
    private EntityManager em;

	@Transactional
    public Account getAccount(String userId) throws DataAccessException {
        return em.find(Account.class, userId); //find(Class<T> entityClass, Object primaryKey) ==> PK로 객체를 찾는다.
	}

	@Transactional
	public Account getAccount(String userId, String password) 
			throws DataAccessException {
		TypedQuery<Account> query = em.createQuery(
                                "select a from Account a "
                                + "where a.userId=:id and a.password=:pw",
                                Account.class); //주의: DB 컬럼명 말고 JPA에 설정한 이름을 사용
        query.setParameter("id", userId);
        query.setParameter("pw", password);
        Account account = null;
        try {
        	account = query.getSingleResult();
        	System.out.println(account);
        } catch(NoResultException ex) {
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
	public void removeAccount(Account account) { //"Removing a detached instance" 예외 때문에 find() 필요
		Account a = em.find(Account.class, account.getUserId());
        em.remove(a);
    }

	@Transactional
	public void updatePassword(Account account, String password) throws DataAccessException {
		Query query = em.createNativeQuery("UPDATE Account a SET a.password = :pw WHERE a.user_id = :id");
		query.setParameter("id", account.getUserId()); //주의: TypedQuery가 아니라면 DB상의 컬럼명을 사용
		query.setParameter("pw", password);
		query.executeUpdate(); //JPQL 실행
	}
}
