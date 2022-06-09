package com.example.SOMusic.controller;

import java.util.Date;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class JoinRequest {
	private int joinId;
	private int shippingCost;
	private int quantity;
	private int status; //0: join 이전 & 1: 승인됨, 입금 대기 & 2: 입금 완료, 배송 대기중 & 3:배송 시작 & 4: 거래 완료
	private int totalAmount;
	
	private String consumerName;
	private String consumerBank;
	private String address; //주소
	private int zipcode; //우편번호
	private String phone; //전화번호
	private int shippingMethod; //1: 준등기, 2: 택배, 3: 택배(제주산간)
	private String shippingRequest; //배송 시 요청사항
	
	private String accountHolder; //참여자의 예금주명
	private String refundAccount; //참여자의 환불계좌 번호
	private String refundBank; //참여자의 환불계좌 은행명
	private String refundHolder; //참여자의 환불 예금주명
	
	private Date regDate;
	private GroupPurchase groupPurchase; //주최자 정보 포함
	
	/* Public Methods */
	public void initJoinReq(Join j) {
		joinId = j.getJoinId();
		shippingCost = j.getShippingCost();
		quantity = j.getQuantity();
		status = j.getStatus();
		totalAmount = j.getTotalAmount();
		
		consumerName = j.getConsumerName();
		consumerBank = j.getConsumerBank();
		address = j.getAddress();
		zipcode = j.getZipcode();
		phone = j.getPhone();
		shippingMethod = j.getShippingMethod();
		shippingRequest = j.getShippingRequest();
		
		accountHolder = j.getAccountHolder();
 		refundAccount = j.getRefundAccount();
 		refundBank = j.getRefundBank();
 		refundHolder = j.getRefundHolder();
 		
 		regDate = j.getRegDate();
 		groupPurchase = j.getGroupPurchase();
	}
}

