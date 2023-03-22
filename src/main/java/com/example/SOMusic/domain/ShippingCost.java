package com.example.SOMusic.domain;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ShippingCost {
    SEMI_REGISTERED("준등기 (+1,800원)", 1, 1_800),
    PARCEL("택배 (+3,000원)", 2, 3_000),
    ISLAND_MOUNTAINOUS("택배(제주산간) (+6,000원)", 3, 6_000);

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

    public static Object[] getNameList() {
        Object[] result = Arrays.stream(ShippingCost.values())
                .map(ShippingCost::getName)
                .collect(Collectors.toList())
                .toArray();

        return result;
    }

    public static Object[] getCodeList() {
        Object[] result = Arrays.stream(ShippingCost.values())
                .map(ShippingCost::getCode)
                .collect(Collectors.toList())
                .toArray();

        return result;
    }

    public static Object[] getCostList() {
        Object[] result = Arrays.stream(ShippingCost.values())
                .map(ShippingCost::getCost)
                .collect(Collectors.toList())
                .toArray();

        return result;
    }

    public static int findCostByCode(int code) {
        ShippingCost result = Arrays.stream(ShippingCost.values())
                .filter(shippingCost -> shippingCost.code == code)
                .findAny()
                .get();

        return result.cost;
    }

}
