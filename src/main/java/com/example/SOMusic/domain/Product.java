package com.example.SOMusic.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.SOMusic.controller.ProductRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="product") 
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
	private String sellerId; 
	@Column(name="artist_name")
	private String artistName;
	private String location;

	@OneToMany(mappedBy="product", cascade = CascadeType.ALL)
	private List<Purchase> purchaseList;
	
	@Override
	public String toString() {
		return "등록상품정보 [[productId=" + productId + ", sellerId=" + sellerId + ", productName=" + productName + ", image=" + image
				+ ", description=" + description + ", condition=" + condition + ", shippingCost=" + shippingCost + ", account="
				+ account + ", bank=" + bank + ", price=" + price + 
				  "]";
	}
	
	public Product() {}
	
	public Product(String productName, int price, String image, String desc, int condition,
			int cost, int status, int account, String bank, String sellerId, String artistName, String location) {
		this.productName = productName;
		this.price = price;
		this.image = image;
		this.description = desc;
		this.condition = condition;
		this.shippingCost = cost;
		this.status = status;
		this.account = account;
		this.bank = bank;
		this.sellerId = sellerId;
		this.artistName = artistName;
		this.location = location;
	}
	public Product(int prId, String productName, int price, String image, String desc, int condition,
			int cost, int status, int account, String bank, String sellerId, String artistName, String location) {
		this.productId = prId;
		this.productName = productName;
		this.price = price;
		this.image = image;
		this.description = desc;
		this.condition = condition;
		this.shippingCost = cost;
		this.status = status;
		this.account = account;
		this.bank = bank;
		this.sellerId = sellerId;
		this.artistName = artistName;
		this.location = location;
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
