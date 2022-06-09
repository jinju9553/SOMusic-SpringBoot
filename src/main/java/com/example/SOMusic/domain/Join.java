package com.example.SOMusic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.SOMusic.controller.JoinRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="join") 
@Getter @Setter @ToString
@SequenceGenerator(name="SEQ_JOIN", sequenceName="SEQUENCE_JOIN", allocationSize=1)
@SuppressWarnings("serial")
public class Join implements Serializable { 
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_JOIN")
	private int joinId;
	
	/*공통 필드*/
	@Column(name="consumer_id") 
	private String consumerId;
	
	@Column(name="consumer_name") 
	private String consumerName;
	
	@Column(name="consumer_bank") 
	private String consumerBank;
	
	@Column(name="total_amount", nullable=false)
	private int totalAmount;
	
	private String address;
	private int zipcode;
	private String phone;
	
	@Column(name="shipping_cost")
	private int shippingCost;
	
	@Column(name="shipping_method", nullable=false)
	private int shippingMethod;
	
	@Column(name="shipping_request")
	private String shippingRequest;

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
	
	@Column(name="reg_date", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
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
