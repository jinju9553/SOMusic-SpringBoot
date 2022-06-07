package com.example.SOMusic.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;

import com.example.SOMusic.domain.Join;

public class JoinDaoImpl implements JoinDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	@Override
	public void createJoin(Join join) throws DataAccessException {
		em.persist(join);
	}

	@Transactional
	@Override
	public void updateJoin(Join join) throws DataAccessException {
		em.merge(join);
	}

}
