package com.example.SOMusic.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.SOMusic.controller.GPRequest;

@Repository
public class GPDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void insertGP(GPRequest gp) throws DataAccessException {
		em.persist(gp);
	}
	
	@Transactional
	public void updateGP(GPRequest gp) throws DataAccessException {
		em.merge(gp);
	}
	
	@Transactional
	public GPRequest findGP(int gpId) throws DataAccessException {
		return em.find(GPRequest.class, gpId);
	}
}
