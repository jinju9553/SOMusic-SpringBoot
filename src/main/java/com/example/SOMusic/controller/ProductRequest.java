package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequest {
	private int productId;
	private String productName;
	private String sellerId;
	private int price;
	private int shippingCost;
	private String image;
	private String description;
	private int condition;
	private int status;
	private int account;
	private String bank;
	private String artistName;
	
	public ProductRequest () {}
	public ProductRequest(int productId, String productName, String sellerId,
			int price, int shippingCost, String description, int condition,
			int status, int account, String bank,
			String artistName) {
		this.productId = productId;
		this.productName = productName;
		this.sellerId = sellerId;
		this.price = price;
		this.shippingCost = shippingCost;
		this.description = description;
		this.condition = condition;
		this.account = account;
		this.bank = bank;
		this.condition = condition;
		this.artistName = artistName;
	}
	public ProductRequest(int productId, String productName, int price, int shippingCost, String description,
			int condition, String artistName) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.shippingCost = shippingCost;
		this.description = description;
		this.condition = condition;
		this.artistName = artistName;
	}
	
	
	// Public Methods
	public void initProductReq(Product p) {
		productName = p.getProductName();
		sellerId = p.getSellerId();
		price = p.getPrice();
		description = p.getDescription();
		condition = p.getCondition();
		artistName = p.getArtistName();
	}

}
