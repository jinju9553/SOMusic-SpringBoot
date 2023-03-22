package com.example.SOMusic.domain;

import java.util.Date;

public class TestJoin {

    private static final int JOIN_ID = 67492;
    private static final int JOIN_ID2 = 16343;
    private static final int GP_ID = 568;
    private static final int GP_PRICE = 10_000;
    private static final int SHIPPING_METHOD = 1;
    private static final int SHIPPING_COST = ShippingCost.findCostByCode(SHIPPING_METHOD);
    private static final int TOTAL_AMOUNT = 51_800;
    private static final String CONSUMER_ID = "amy1234";

    private static Join join = new Join();

    public static Join createTestJoin() {
        join.setJoinId(JOIN_ID);
        join.setConsumerId(CONSUMER_ID);
        join.setConsumerName("amy");
        join.setConsumerBank("BankName");
        join.setTotalAmount(TOTAL_AMOUNT);
        join.setShippingMethod(SHIPPING_METHOD);
        join.setShippingCost(SHIPPING_COST);

        join.setRegDate(new Date());

        GroupPurchase groupPurchase = new GroupPurchase();

        groupPurchase.setGpId(GP_ID);
        groupPurchase.setPrice(GP_PRICE);
        join.setGroupPurchase(groupPurchase);

        return join;
    }

    public static Join createAnotherTestJoin() {
        join.setJoinId(JOIN_ID2);
        join.setConsumerId(CONSUMER_ID);
        join.setConsumerName("amy");
        join.setConsumerBank("BankName");
        join.setTotalAmount(5);
        join.setShippingMethod(1);
        join.setShippingCost(SHIPPING_COST);

        join.setRegDate(new Date());

        GroupPurchase groupPurchase = new GroupPurchase();

        groupPurchase.setGpId(GP_ID);
        groupPurchase.setPrice(GP_PRICE);
        join.setGroupPurchase(groupPurchase);

        return join;
    }
}
