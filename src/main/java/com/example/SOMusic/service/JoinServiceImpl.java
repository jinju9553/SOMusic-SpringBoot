package com.example.SOMusic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.dao.JoinDao;
import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.repository.JoinRepository;

@Service
public class JoinServiceImpl implements JoinService{

	@Autowired
	private JoinRepository joinRepository;
	
	@Autowired
	private JoinDao joinDao;
	
	@Override
	public void registerJoin(Join join) {
		joinDao.createJoin(join);
	}

	@Override
	public List<Join> findAllByUserId(String consumerId) {
		return joinRepository.findAllByConsumerId(consumerId);
	}

	@Override
	public void modifyJoin(Join join) {
		joinDao.updateJoin(join);
	}

	@Transactional
	@Override
	public void deleteJoin(String joinId) {
		joinRepository.deleteByJoinId(joinId);
	}

	@Override
	public Join findJoinByJoinId(int joinId) {
		return joinRepository.findJoinByJoinId(joinId);
	}

	@Override
	public int calculateTotal(GroupPurchase groupPurchase, Join join) {
		return (groupPurchase.getPrice() * join.getQuantity() + join.getShippingCost());
	}

	@Override
	public int initShippingCost(Join join) {
		int m = join.getShippingMethod();
		switch(m) {
			case 1:
				return 1800;
			case 2: 
				return 3000;
			case 3:
				return 6000;
		}
		return 0;
	}

	@Override
	public int updateTotal(Join join, int newShippingCost) {
		int originalPrice = join.getTotalAmount() - join.getShippingCost();
		return (originalPrice + newShippingCost);
	}
	
	@Override
	public void updateAllStatus(int gpId, int status) {
//		joinRepository.updateAllStatus(gpId, status);
	}
	
	@Override
	public void updateStatus(int joinId, int status) {
		Join join = this.findJoinByJoinId(joinId);		// joinId로 join 불러옴
		join.setStatus(status);		// status 넣기
		
		joinDao.updateJoin(join);
	}
}
