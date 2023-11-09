package com.example.SOMusic.service;

import com.example.SOMusic.dao.JoinDao;
import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.domain.ShippingCost;
import com.example.SOMusic.repository.JoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class JoinServiceImpl implements JoinService {

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
    public void modifyJoin(Join join, Join joinReq) {
        //TODO: 학교 DB 복구 후 테스트
        joinDao.updateJoin(joinReq);
    }

    @Override
    @Transactional
    public void deleteJoin(int joinId) {
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
        int shippingMethod = join.getShippingMethod();
        int shippingCost = ShippingCost.findCostByCode(shippingMethod);

        return shippingCost;
    }

    @Override
    public int calculateUpdatedTotal(Join join, int newShippingCost) {
        int originalPrice = join.getTotalAmount() - join.getShippingCost();

        return (originalPrice + newShippingCost);
    }

    @Override
    @Transactional
    public void updateAllStatus(List<Join> joins, int status) {
        for (Join join : joins) {
            join.setStatus(status);
        }
    }

    @Override
    @Transactional
    public void updateStatus(Join join, int status) {
        join.setStatus(status);
    }
}
