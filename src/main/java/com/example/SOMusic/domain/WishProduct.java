package com.example.SOMusic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.SOMusic.controller.ProductRequest;
import com.example.SOMusic.controller.WishRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="WISHPRODUCT")
@Getter @Setter
public class WishProduct {
	
	@Id
	@Column(name="USER_ID")
	private String userId;
	@Column(name="PRODUCT_ID")
	private int productId;
	
	@Override
	public String toString() {
		return "관심 상품 목록 [[productId= " + productId +"]], ";
	}

	public void initWish(WishRequest wishReq) {
		userId = wishReq.getUserId();
		productId = wishReq.getProductId();
	}

	
}
