package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PurchaseRequest {
	private String consumerName; //주문자 이름
	private String address; //주소
	private int zipcode; //우편번호
	private String phone; //전화번호
	private String shippingRequest; //배송 시 요청사항
	
	private int paymentOption; //결제수단
	private Product product; //참조하는 객체
	
	private int totalAmount; //배송비 포함 금액
}
