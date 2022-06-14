package com.example.SOMusic.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.SOMusic.controller.GPRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
	
	private String title;
	
	private String image;
	
	@Column(name="START_DATE")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate startDate;
	
	@Column(name="END_DATE")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate endDate;
	
	private String category;

	private String account;
	
	private String bank;
	
	private int price;
	
	private String description;
	
	@OneToMany(mappedBy="groupPurchase", cascade = CascadeType.ALL)
	private List<Join> joinList;
	
	public GroupPurchase() {}

	public GroupPurchase(int gpId, String sellerId, String title, String image, LocalDate startDate, LocalDate endDate,
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
	
	public void initGP(GPRequest gpReq, String filename) {
		gpId = gpReq.getGpId();
		sellerId = gpReq.getSellerId();
		title = gpReq.getTitle();
		image = filename;
		startDate = gpReq.getStartDate();
		endDate = gpReq.getEndDate();
		category = gpReq.getCategory();
		account = gpReq.getAccount();
		bank = gpReq.getBank();
		price = gpReq.getPrice();
		description = gpReq.getDescription();
	}

	@Override
	public String toString() {
		return "GroupPurchase [gpId=" + gpId + ", sellerId=" + sellerId + ", title=" + title + ", image=" + image
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", category=" + category + ", account="
				+ account + ", bank=" + bank + ", price=" + price + ", description=" + description + "]";
	}

}
