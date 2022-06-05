package com.example.SOMusic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="grouppurchase")
@Getter @Setter
@SequenceGenerator(name="SEQ_GP", sequenceName="SEQUENCE_GROUPPURCHASE", allocationSize=1)
@SuppressWarnings("serial")
public class GroupPurchase implements Serializable {
	@Id @Column(name="GROUPPURCHASE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GP")
	private int gpId;
	
	@Column(name="SELLER_ID")
	private String sellerId;
	
	@NotBlank(message="필수 입력 항목입니다.")
	private String title;
	
	private String image;
	
	@Column(name="START_DATE")
	@NotNull(message="필수 입력 항목입니다.")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	
	@Column(name="END_DATE")
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
	private String description;
	
	public GroupPurchase() {}

	public GroupPurchase(int gpId, String sellerId, String title, String image, Date startDate, Date endDate,
			String category, String account, String bank, int price, String description) {
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

	public int getGpId() {
		return gpId;
	}

	public void setGpId(int gpId) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
