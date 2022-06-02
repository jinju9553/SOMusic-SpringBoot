package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PurchaseRequest {

	//만약 직거래라면 주소 & 우편번호가 필요 없음 ==> 이 또한 구분할 필요가 있음
	private String consumerName; //주문자 이름
	private int totalAmount; //배송비 포함 금액
	private String address; //주소
	private String zipcode; //우편번호
	private String phone; //전화번호
	
	private int status;
	private int shippingMethod; //0: 직거래 & 1: 택배 & 2: 기타 (알아서 기재)
	
	private String shippingRequest; //배송 시 요청사항
	private int paymentOption; //결제수단 (입금 or 현금거래)
	private Product product; //참조하는 객체
}
