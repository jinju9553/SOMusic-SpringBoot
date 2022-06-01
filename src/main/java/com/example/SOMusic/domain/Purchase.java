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
@Table(name="purchase") //클래스 명과 테이블 명이 다르다면 지정
@Getter @Setter 
@SuppressWarnings("serial")
public class Purchase implements Serializable { //extends Order { //상속되는 필드는 어떻게 처리? ==> 일단 상속 취소하고 합침

	@Id
	private int purchaseId; 
	
	/*공통 필드*/
	@Column(name="consumer_id") 
	public String consumerId;
	@Column(name="consumer_name")
	public String consumerName;
	@Column(name="total_amount")
	public int totalAmount;
	public String address;
	public String zipcode;
	public String phone;

	//transient: DB에 저장되지 않음
	transient private int status;
	
	@ManyToOne //Many가 Purchase, One이 Product
	@JoinColumn(name="product_id") //DB 상에서 FK의 이름
	private Product product;
}
