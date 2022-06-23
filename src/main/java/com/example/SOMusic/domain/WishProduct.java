package com.example.SOMusic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.SOMusic.controller.WishRequest;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="WISHPRODUCT")
@Getter @Setter
@IdClass(PKWishPr.class)
public class WishProduct implements Serializable {
	
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
	
	@MapsId()
	@OneToOne
	@JoinColumn(name="PRODUCT_ID", nullable=true)
	private Product pr;

	
}
