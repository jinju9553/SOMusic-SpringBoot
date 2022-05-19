package com.example.SOMusic.domain;

import java.util.Date;
import java.util.List;

public class GroupPurchase {
	private String gp_id;
	private String seller_id;
	private String title;
	private String image;
	private Date start_date;
	private Date end_date;
	private List<String> category;
	private String account;
	private String bank;
	private int price;
	private String description;
	
	public String getGp_id() {
		return gp_id;
	}

	public void setGp_id(String gp_id) {
		this.gp_id = gp_id;
	}

	public String getSeller_id() {
		return seller_id;
	}
	
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
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
	
	public Date getStart_date() {
		return start_date;
	}
	
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	public Date getEnd_date() {
		return end_date;
	}
	
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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
