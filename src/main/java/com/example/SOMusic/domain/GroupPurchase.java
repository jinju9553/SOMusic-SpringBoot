package com.example.SOMusic.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
	
	@NotBlank(message="필수 입력 항목입니다.")
	private String title;
	
	private String image;
	
	@Column(name="START_DATE")
	@NotNull(message="필수 입력 항목입니다.")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate startDate;
	
	@Column(name="END_DATE")
	@NotNull(message="필수 입력 항목입니다.")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate endDate;
	
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
	
	@OneToMany
	@JoinColumn(name="GROUPPURCHASE_ID")
	private List<Join> joinList;

	@Override
	public String toString() {
		return "GroupPurchase [gpId=" + gpId + ", sellerId=" + sellerId + ", title=" + title + ", image=" + image
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", category=" + category + ", account="
				+ account + ", bank=" + bank + ", price=" + price + ", description=" + description + ", joinList="
				+ joinList.toString() + "]";
	}

}
