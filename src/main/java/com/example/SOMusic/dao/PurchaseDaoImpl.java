package com.example.SOMusic.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.example.SOMusic.domain.Purchase;

public class PurchaseDaoImpl implements PurchaseDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	@Override
	public void createPurchase(Purchase purchase) {
		em.persist(purchase);
	}

	@Transactional
	@Override
	public void updatePurchase(Purchase purchase) {
		em.merge(purchase);
	}
}
