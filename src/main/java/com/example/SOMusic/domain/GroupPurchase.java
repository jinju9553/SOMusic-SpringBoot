package com.example.SOMusic.domain;

import java.util.Date;
import java.util.List;

public class GroupPurchase {
	private String gpId;
	private String sellerId;
	private String title;
	private String image;
	private Date startDate;
	private Date endDate;
	private List<String> category;
	private String account;
	private String bank;
	private int price;
	private String description;
	
	public GroupPurchase() {}

	public GroupPurchase(String gpId, String sellerId, String title, String image, Date startDate, Date endDate,
			List<String> category, String account, String bank, int price, String description) {
		this.gpId = gpId;
		this.sellerId = sellerId;
		this.title = title;
		this.image = image;
		this.startDate = startDate;
		this.endDate = endDate;
		this.category = category;
		this.account = account;
		this.bank = bank;
		this.price = price;
		this.description = description;
	}

	public String getGpId() {
		return gpId;
	}

	public void setGpId(String gpId) {
		this.gpId = gpId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
