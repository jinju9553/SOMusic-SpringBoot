package com.example.SOMusic.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.SOMusic.domain.Product;

@Repository
public class WishlistDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void addWish(Product product) throws DataAccessException {
		em.persist(product);
	}
}
