package com.example.SOMusic.domain;

import java.util.Arrays;

public enum ShippingCost {
    SEMI_REGISTERED("준등기", 1, 1_800),
    PARCEL("택배", 2, 3_000),
    ISLAND_MOUNTAINOUS("택배(제주산간)", 3, 6_000),
    EMPTY("", -1, -1);

    private final String name;
    private final int code;
    private final int cost;

    ShippingCost(String name, int code, int cost) {
        this.name = name;
        this.code = code;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCode(){
        return code;
    }

    public int getCost() {
        return cost;
    }

    public static int findCostByCode(int code) {
        ShippingCost result = Arrays.stream(ShippingCost.values())
                .filter(shippingCost -> shippingCost.code == code)
                .findAny()
                .orElse(EMPTY);

        return result.cost;
    }

}
