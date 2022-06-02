package com.example.SOMusic.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class Join { //extends Order{
	
	/*공통 필드*/
	private String consumerId;
	private String consumerName;
	private int totalAmount;
	private String address;
	private int zipcode;
	private String phone;
	
	private int joinId;
	private int shippingCost;
	private int quantity;
	private int status;
	private String refundAccount; //참여자의 환불계좌 번호
	private String refundBank; //참여자의 환불계좌 은행명
	
	private GroupPurchase groupPurchase; //주최자 정보 포함
}
