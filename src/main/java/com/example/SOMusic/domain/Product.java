package com.example.SOMusic.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.SOMusic.controller.ProductRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="product") //클래스 명과 테이블 명이 다르다면 지정
@Getter @Setter @ToString
@SequenceGenerator(name="SEQ_PR", sequenceName="SEQUENCE_PRODUCT", allocationSize=1)
@SuppressWarnings("serial")
public class Product implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PR")
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
	
	@Column(name="SELLER_ID") 
	private String sellerId; //추후에 transient를 지우고 Account와 조인
	@Column(name="artist_name")
	private String artistName;
	private String location;
//	private int shippingMethod;
	
//	@JoinColumn(name="artist_id")
//	private Artist artist;
	
	@OneToMany(mappedBy="product", cascade = CascadeType.ALL)
	private List<Purchase> purchaseList;
	
	@Override
	public String toString() {
		return "등록상품정보 [[productId=" + productId + ", sellerId=" + sellerId + ", productName=" + productName + ", image=" + image
				+ ", description=" + description + ", condition=" + condition + ", shippingCost=" + shippingCost + ", account="
				+ account + ", bank=" + bank + ", price=" + price + 
				  "]";
	}
	
	public void initPr(ProductRequest prReq, String filename) {
		productName = prReq.getProductName();
		productId = prReq.getProductId();
		price = prReq.getPrice();
		image = filename;
		location = prReq.getLocation();
		description = prReq.getDescription();
		condition = prReq.getCondition();
		bank = prReq.getBank();
		account = prReq.getAccount();
		shippingCost = prReq.getShippingCost();
		sellerId = prReq.getSellerId();
		artistName = prReq.getArtistName();
		
	}
}
