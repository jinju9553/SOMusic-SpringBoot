package com.example.SOMusic.domain;

public class WishGroupPurchase {
	private String userId;
	private GroupPurchase gp;
	
	public WishGroupPurchase() {}
	
	public WishGroupPurchase(String userId, GroupPurchase gp) {
		this.userId = userId;
		this.gp = gp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public GroupPurchase getGp() {
		return gp;
	}

	public void setGp(GroupPurchase gp) {
		this.gp = gp;
	}
	
}
