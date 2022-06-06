package com.example.SOMusic.controller;

import java.time.LocalDate;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PurchaseRequest {

	private int purchaseId;
	private String consumerId;
	
	//만약 직거래라면 주소 & 우편번호가 필요 없음 ==> 이 또한 구분할 필요가 있음
	private String consumerName; //주문자 이름
	private int totalAmount; //배송비 포함 금액
	private String address; //주소
	private String zipcode; //우편번호
	private String phone; //전화번호

	private int shippingMethod; //0: 직거래만 & 1: 택배만 & 2: 둘 다 가능 & 3: 기타(알아서 기재)
	private String shippingRequest; //배송 시 요청사항
	private int paymentOption; //결제수단 (입금 or 현금거래)
	
	private LocalDate regDate;
	private Product product; //참조하는 객체 ==> 이 안에 status도 있음
	
	/* Public Methods */
	public void initPurchaseReq(Purchase p) {
		purchaseId = p.getPurchaseId();
		consumerId = p.getConsumerId();
		
		consumerName = p.getConsumerName();
		totalAmount = p.getTotalAmount();
		address = p.getAddress();
		zipcode = p.getZipcode(); 
		phone = p.getPhone();

		shippingMethod = p.getShippingMethod();
		shippingRequest = p.getShippingRequest();
		//paymentOption도 Command에 직접 저장?
		regDate = p.getRegDate();
		product = p.getProduct();
	}
}
