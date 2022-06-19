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
	public List<Join> findAllByGroupPurchaseGpId(int gpId) {
		return joinRepository.findAllByGroupPurchaseGpId(gpId);
	}

	@Override
	@Transactional
	public void modifyJoin(Join join) {
		Join j = this.findJoinByJoinId(join.getJoinId());
		
		//아래의 join 필드들을 객체 단위로 묶으면 더 좋을듯
		j.setAccountHolder(join.getAccountHolder());
		j.setRefundBank(join.getRefundBank());
		j.setRefundAccount(join.getRefundAccount());
		j.setRefundHolder(join.getRefundHolder());
		
		int newShippingCost = initShippingCost(join);
		j.setTotalAmount(updateTotal(j, newShippingCost));
		j.setShippingCost(newShippingCost);
		j.setShippingMethod(join.getShippingMethod());
		
		j.setConsumerName(join.getConsumerName());
		j.setPhone(join.getPhone());
		j.setZipcode(join.getZipcode());
		j.setAddress(join.getAddress());
		j.setShippingRequest(join.getShippingRequest());
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
	@Transactional
	public void updateAllStatus(int gpId, int status) {
		List<Join> joinList = this.findAllByGroupPurchaseGpId(gpId);
		for (Join join : joinList)
			join.setStatus(status);
	}
	
	@Override
	@Transactional
	public void updateStatus(int joinId, int status) {
		Join join = this.findJoinByJoinId(joinId);		// joinId로 join 불러옴
		join.setStatus(status);		// status 넣기
	}
}
