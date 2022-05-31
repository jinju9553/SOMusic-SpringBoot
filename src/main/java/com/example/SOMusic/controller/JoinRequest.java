package com.example.SOMusic.controller;

import com.example.SOMusic.domain.GroupPurchase;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class JoinRequest { //extends Order 해도 되는지?
	private int joinId;
	private int shippingCost;
	private int quantity;
	private int status;
	private int totalPrice; //전체 금액 ==> Order에 totalAmout가 이미 있는 것은 어떻게 할지?
	
	private String consumerName;
	private String address; //주소
	private int zipcode; //우편번호
	private String phone; //전화번호
	private String shippingRequest; //배송 시 요청사항
	
	private String account; //참여자의 계좌
	private String AccountHolder; //참여자의 예금주명
	private String refundAccount; //참여자의 환불계좌 번호
	private String refundBank; //참여자의 환불계좌 은행명
	private String refundHolder; //참여자의 환불 예금주명
	
	private GroupPurchase groupPurchase; //주최자 정보 포함
}