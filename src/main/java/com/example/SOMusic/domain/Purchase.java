package com.example.SOMusic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="purchase") 
@Getter @Setter 
@SuppressWarnings("serial")
public class Purchase implements Serializable { 

	@Id
	private int purchaseId; 
	
	/*공통 필드*/
	@Column(name="consumer_id") 
	private String consumerId;
	
	@Column(name="consumer_name")
	private String consumerName;
	
	@Column(name="total_amount")
	private int totalAmount;
	
	private String address;
	private String zipcode;
	private String phone;

	//transient: DB에 저장되지 않음
	//transient private int status; //0: 승인 전 & 1: 승인됨, 입금 대기 & 2: 입금 완료 & 3:배송 시작 & 4: 거래 완료
	transient private int shippingMethod; //0: 직거래만 & 1: 택배만 & 2: 둘 다 가능 & 4: 기타(알아서 기재)
	
	@Column(name="shipping_request")
	private String shippingRequest;
	
	@ManyToOne //Many가 Purchase, One이 Product
	@JoinColumn(name="product_id") //DB 상에서 FK의 이름
	private Product product;
}
