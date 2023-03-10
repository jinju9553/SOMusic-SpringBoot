package com.example.SOMusic.domain;

import java.util.Date;

public class TestPurchase {

    private static final int PURCHASE_ID = 5723;
    private static final int PRODUCT_ID = 173;
    private static final String CONSUMER_ID = "mark123";
    private static Purchase purchase = new Purchase();

    public static Purchase createTestPurchase(){
        purchase.setPurchaseId(PURCHASE_ID);
        purchase.setConsumerId(CONSUMER_ID);
        purchase.setConsumerName("mark");
        purchase.setTotalAmount(3);
        purchase.setStatus(1);
        purchase.setShippingMethod(0);

        purchase.setRegDate(new Date());

        Product product = new Product();
        product.setProductId(PRODUCT_ID);

        purchase.setProduct(product);

        return purchase;
    }

    public static Purchase createAnotherTestPurchase(){
        purchase.setPurchaseId(PURCHASE_ID);
        purchase.setConsumerId(CONSUMER_ID);
        purchase.setConsumerName("mango");
        purchase.setTotalAmount(8);
        purchase.setStatus(2);
        purchase.setShippingMethod(2);

        purchase.setRegDate(new Date());
        purchase.setProduct(new Product());

        return purchase;
    }
}
