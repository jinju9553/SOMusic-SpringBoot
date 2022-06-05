package com.example.SOMusic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.SOMusic.controller.JoinRequest;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="join") 
@Getter @Setter 
@SuppressWarnings("serial")
public class Join implements Serializable { 
	
	@Id
	private int joinId;
	
	/*공통 필드*/
	@Column(name="consumer_id") 
	private String consumerId;
	
	@Column(name="consumer_name") 
	private String consumerName;
	
	@Column(name="total_amount")
	private int totalAmount;
	
	private String address;
	private int zipcode;
	private String phone;
	
	@Column(name="shipping_cost")
	private int shippingCost;
	
	@Column(name="shipping_request")
	private String shippingRequest;
	
	@Column(name="shipping_method")
	private int shippingMethod;
	
	private int quantity;
	private int status;
	
	@Column(name="account_holder")
	private String accountHolder; 
	
	@Column(name="refund_account")
	private String refundAccount; 
	
	@Column(name="refund_bank")
	private String refundBank; 
	
	@Column(name="refund_holder")
	private String refundHolder; 
	
	@ManyToOne //Many가 Join, One이 GroupPurchase
	@JoinColumn(name="grouppurchase_id") //DB 상에서 FK의 이름
	private GroupPurchase groupPurchase; //주최자 정보 포함
	
	/* Public Methods */
	public void initJoin(JoinRequest j) {
		joinId = j.getJoinId();
		shippingCost = j.getShippingCost();
		quantity = j.getQuantity();
		status = j.getStatus();
		totalAmount = j.getTotalAmount();
		
		consumerName = j.getConsumerName();
		address = j.getAddress();
		zipcode = j.getZipcode();
		phone = j.getPhone();
		shippingRequest = j.getShippingRequest();
		shippingMethod = j.getShippingMethod();
		
		accountHolder = j.getAccountHolder();
 		refundAccount = j.getRefundAccount();
 		refundBank = j.getRefundBank();
 		refundHolder = j.getRefundHolder();
 		
 		groupPurchase = j.getGroupPurchase();
	}
}
