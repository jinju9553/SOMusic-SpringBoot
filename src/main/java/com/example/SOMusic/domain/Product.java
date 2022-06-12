package com.example.SOMusic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="product") //클래스 명과 테이블 명이 다르다면 지정
@Getter @Setter
@SuppressWarnings("serial")
public class Product implements Serializable {
	
	@Id
	private int productId;
	
	@Column(name="product_name")
	private String productName;
	private int price;
	private String image;
	private String description;
	private int condition;
	@Column(name="shipping_cost")
	private int shippingCost;
	private int status;
	private int account;
	private String bank;
	transient private String sellerId; //추후에 transient를 지우고 Account와 조인
	@Column(name="artist_name")
	private String artistName;
//	private String location;
//	private int shippingMethod;
	
//	@JoinColumn(name="artist_id")
//	private Artist artist;
}
