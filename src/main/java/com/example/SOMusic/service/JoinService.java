package com.example.SOMusic.service;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;

import java.util.List;

public interface JoinService {

    void registerJoin(Join join);

    void modifyJoin(Join join, Join joinReq);

    void deleteJoin(int joinId);

    Join findJoinByJoinId(int joinId);

    List<Join> findAllByUserId(String userId);

    List<Join> findAllByGroupPurchaseGpId(int gpId);

    int calculateTotal(GroupPurchase groupPurchase, Join join);

    int initShippingCost(Join join);

    int calculateUpdatedTotal(Join join, int newShippingCost);

    void updateAllStatus(List<Join> joins, int status);

    public void updateStatus(Join join, int status);
}
