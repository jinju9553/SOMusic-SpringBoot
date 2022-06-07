package com.example.SOMusic.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.SOMusic.domain.GroupPurchase;

@Repository
public class GPDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void insertGP(GroupPurchase gp) throws DataAccessException {
		em.persist(gp);
	}
	
	@Transactional
	public void updateGP(GroupPurchase gp) throws DataAccessException {
		em.merge(gp);
	}

}
