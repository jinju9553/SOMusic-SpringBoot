package com.example.SOMusic.domain;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class PKWishGP implements Serializable {
	
	private String userId;
	private int gpId;
	
	public PKWishGP() {}
	
	public PKWishGP(String userId, int gpId) {
		this.userId = userId;
		this.gpId = gpId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PKWishGP other = (PKWishGP) obj;
		return gpId == other.gpId && Objects.equals(userId, other.userId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(gpId, userId);
	}
}
