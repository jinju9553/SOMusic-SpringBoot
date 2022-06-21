package com.example.SOMusic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="purchase") 
@Getter @Setter
@SequenceGenerator(name="SEQ_PURCHASE", sequenceName="SEQUENCE_PURCHASE", allocationSize=1)
@SuppressWarnings("serial")
public class Purchase implements Serializable { 

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PURCHASE")
	private int purchaseId; 
	
	/*공통 필드*/
	@Column(name="consumer_id") 
	private String consumerId;
	
	@Column(name="consumer_name")
	private String consumerName;
	
	@Column(name="total_amount", nullable=false)
	private int totalAmount;
	
	private String address;
	private String zipcode;
	private String phone;
	
	private String location;
	@Column(name="shipping_method", nullable=false) //여기 말고 Product에 있어야할 것 같음
	private int shippingMethod; //0: 직거래만 & 1: 택배만 & 2: 둘 다 가능 & 3: 기타(알아서 기재)
	@Column(name="shipping_request")
	private String shippingRequest;
	
	@Column(name="reg_date", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
	@ManyToOne(cascade = CascadeType.ALL) //Many가 Purchase, One이 Product
	@JoinColumn(name="product_id") //DB 상에서 FK의 이름
	private Product product;

	public boolean matchesZipcode() {
		return zipcode.matches("^\\d{5}$");
	}
	
	public boolean matchesPhone() {
		return phone.matches("^01([0-9])(\\d{3,4})(\\d{4})$");
	}
}
