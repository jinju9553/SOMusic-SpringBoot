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
		
		modifyByStatus(j, join);
	}
	
	public void modifyByStatus(Join newJoin, Join joinReq) {
		int currentStatus = newJoin.getStatus();
		int newShippingCost = initShippingCost(joinReq);
		
		//아래의 join 필드들을 객체 단위로 묶으면 더 좋을듯
		if(currentStatus < 3) {
			newJoin.setTotalAmount(updateTotal(newJoin, newShippingCost));
			newJoin.setShippingCost(newShippingCost);
			newJoin.setShippingMethod(joinReq.getShippingMethod());
			
			newJoin.setConsumerName(joinReq.getConsumerName());
			newJoin.setPhone(joinReq.getPhone());
			newJoin.setZipcode(joinReq.getZipcode());
			newJoin.setAddress(joinReq.getAddress());
			newJoin.setShippingRequest(joinReq.getShippingRequest());
			
			if(currentStatus < 2) {
				newJoin.setAccountHolder(joinReq.getAccountHolder());
			}
		}
		//상시 변경 가능
		newJoin.setRefundBank(joinReq.getRefundBank());
		newJoin.setRefundAccount(joinReq.getRefundAccount());
		newJoin.setRefundHolder(joinReq.getRefundHolder());
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
