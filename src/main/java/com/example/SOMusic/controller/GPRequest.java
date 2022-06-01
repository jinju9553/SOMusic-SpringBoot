package com.example.SOMusic.controller;

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
import lombok.ToString;

@Getter @Setter @ToString
@Entity @Table(name="GROUPPURCHASE")
@SequenceGenerator(name="SEQ_GP", sequenceName="SEQUENCE_GROUPPURCHASE", allocationSize=1)
public class GPRequest implements Serializable {

	@Id @Column(name="GROUPPURCHASE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GP")
	private int gpId;

	@Column(name="SELLER_ID")
	private String sellerId;

	@Column(name="TITLE")
	@NotBlank(message="필수 입력 항목입니다.")
	private String title;
	
	@Column(name="IMAGE")
	private String image;

	@Column(name="START_DATE")
	@NotNull(message="필수 입력 항목입니다.")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	
	@Column(name="END_DATE")
	@NotNull(message="필수 입력 항목입니다.")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	
	@Column(name="CATEGORY")
	private String category;
	
	@Column(name="ACCOUNT")
	@NotBlank(message="필수 입력 항목입니다.")
	private String account;
	
	@Column(name="BANK")
	@NotBlank(message="필수 입력 항목입니다.")
	private String bank;
	
	@Column(name="PRICE")
	@Min(value = 1, message="필수 입력 항목입니다.")
	private int price;
	
	@Column(name="DESCRIPTION")
	@NotBlank(message="필수 입력 항목입니다.")
	private String description;
	
	public GPRequest() { }

	public GPRequest(int gpId, String sellerId, String title, String image, Date startDate, Date endDate,
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

	
}