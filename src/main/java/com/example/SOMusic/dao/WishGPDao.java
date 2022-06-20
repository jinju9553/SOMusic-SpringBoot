package com.example.SOMusic.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.SOMusic.domain.WishGroupPurchase;

@Repository
public class WishGPDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void insertWishGP(WishGroupPurchase gp) throws DataAccessException {
		em.persist(gp);
	}
	
}
