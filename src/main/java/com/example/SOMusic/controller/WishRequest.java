package com.example.SOMusic.controller;

import com.example.SOMusic.domain.WishProduct;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WishRequest {
	private int productId;
	private String userId;
	
	public WishRequest() {}
	
	public WishRequest(int productId, String userId) {
		this.productId = productId;
		this.userId = userId;
	}

	public void initWishReq(WishProduct w) {
		productId = w.getProductId();
		userId = w.getUserId();
	}
}


