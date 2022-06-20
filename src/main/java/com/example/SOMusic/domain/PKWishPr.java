package com.example.SOMusic.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

public class PKWishPr implements Serializable {
	
	private String userId;
	private int productId;
	
	public PKWishPr() {}
	
	public PKWishPr(String userId, int productId) {
		this.userId = userId;
		this.productId = productId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PKWishPr other = (PKWishPr) obj;
		return productId == other.productId && Objects.equals(userId, other.userId);
	}

}
