package com.example.SOMusic.controller;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class GPRequest {
	
	private String gpId;

	private String sellerId;

	@NotBlank(message="필수 입력 항목입니다.")
	private String title;
	
	private String image;

	@NotNull(message="필수 입력 항목입니다.")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	
	@NotNull(message="필수 입력 항목입니다.")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	
	private String category;
	
	@NotBlank(message="필수 입력 항목입니다.")
	private String account;
	
	@NotBlank(message="필수 입력 항목입니다.")
	private String bank;
	
	@Min(value = 1, message="필수 입력 항목입니다.")
	private int price;
	
	@NotBlank(message="필수 입력 항목입니다.")
	private String discription;
	
	public GPRequest() { }

	public GPRequest(String gpId, String sellerId, String title, String image, Date startDate, Date endDate,
			String category, String account, String bank, int price, String discription) {
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
		this.discription = discription;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
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

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	@Override
	public String toString() {
		return "GpRequest [gpId=" + gpId + ", sellerId=" + sellerId + ", title=" + title + ", image=" + image
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", category=" + category + ", account="
				+ account + ", bank=" + bank + ", price=" + price + ", discription=" + discription + "]";
	}
	
}

//@Entity @Table(name="GROUPPURCHASE")
//@NoArgsConstructor
//@Getter @Setter @toString
//public class GPRequest {
//	
//	@Id @Column(name="GROUPPURCHASE_ID")
//	@GeneratedValue(startegy=GenerationType.SEQUENCE
//					, generator="SEQUENCE_GROUPPURCHASE")
//	private String gp_id;
//	
//	@Column(name="SELLER_ID")
//	private String seller_id;
//	
//	@Column(name="TITLE")
//	private String title;
//	
//	@Column(name="IMAGE")
//	private String image;
//	
//	@Column(name="START_DATE")
//	private Date start_date;
//	
//	@Column(name="END_DATE")
//	private Date end_date;
//	
//	@Column(name="CATEGORY")
//	private String category;
//	
//	@Column(name="ACCOUNT")
//	private String account;
//	
//	@Column(name="BANK")
//	private String bank;
//	
//	@Column(name="PRICE")
//	private int price;
//	
//	@Column(name="DISCRIPTION")
//	private String discription;
//}
