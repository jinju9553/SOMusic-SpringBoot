package com.example.SOMusic.domain;

import java.util.Date;

public class TestJoin {

    private static final int JOIN_ID = 67492;
    private static final int JOIN_ID2 = 16343;
    private static final int GP_ID = 568;
    private static final int GP_PRICE = 10_000;
    private static final int QUANTITY = 5;
    private static final int SHIPPING_METHOD = 1;
    private static final int INITIAL_STATUS = 1;
    private static final int SHIPPING_COST = ShippingCost.findCostByCode(SHIPPING_METHOD);
    private static final int TOTAL_AMOUNT = 51_800;
    private static final String CONSUMER_ID = "amy1234";

    public static Join createTestJoin() {
        Join join = new Join();

        join.setJoinId(JOIN_ID);
        join.setConsumerId(CONSUMER_ID);
        join.setConsumerName("amy");
        join.setConsumerBank("BankName");
        join.setQuantity(QUANTITY);
        join.setTotalAmount(TOTAL_AMOUNT);
        join.setShippingMethod(SHIPPING_METHOD);
        join.setShippingCost(SHIPPING_COST);
        join.setStatus(INITIAL_STATUS);

        join.setRegDate(new Date());

        GroupPurchase groupPurchase = new GroupPurchase();

        groupPurchase.setGpId(GP_ID);
        groupPurchase.setPrice(GP_PRICE);
        join.setGroupPurchase(groupPurchase);

        return join;
    }

    public static Join createAnotherTestJoin() {
        Join join = new Join();
        
        join.setJoinId(JOIN_ID2);
        join.setConsumerId(CONSUMER_ID);
        join.setConsumerName("amy");
        join.setConsumerBank("AnotherBankName");
        join.setQuantity(QUANTITY);
        join.setTotalAmount(53_000);
        join.setShippingMethod(2);
        join.setShippingCost(SHIPPING_COST);
        join.setStatus(2);

        join.setRegDate(new Date());

        GroupPurchase groupPurchase = new GroupPurchase();

        groupPurchase.setGpId(GP_ID);
        groupPurchase.setPrice(GP_PRICE);
        join.setGroupPurchase(groupPurchase);

        return join;
    }
}
