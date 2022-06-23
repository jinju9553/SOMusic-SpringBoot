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

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="join") 
@Getter @Setter
@SequenceGenerator(name="SEQ_JOIN", sequenceName="SEQUENCE_JOIN", allocationSize=1)
@SuppressWarnings("serial")
public class Join implements Serializable { 
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_JOIN")
	private int joinId;
	
	/*공통 필드*/
	@Column(name="consumer_id") 
	private String consumerId;

	@Column(name="consumer_name") 
	private String consumerName;

	@Column(name="consumer_bank") 
	private String consumerBank;

	@Column(name="total_amount", nullable=false)
	private int totalAmount;

	private String address;
	private String zipcode;
	private String phone;

	@Column(name="shipping_cost")
	private int shippingCost;

	@Column(name="shipping_method", nullable=false)
	private int shippingMethod; //1: 준등기, 2: 택배, 3: 택배(제주산간)
	
	@Column(name="shipping_request")
	private String shippingRequest;

	private int quantity;
	private int status; //0: join 이전 & 1: 승인됨, 입금 대기 & 2: 입금 완료, 배송 대기중 & 3:배송 시작 & 4: 거래 완료

	@Column(name="account_holder")
	private String accountHolder; 

	@Column(name="refund_bank")
	private String refundBank; 

	@Column(name="refund_account")
	private String refundAccount; 

	@Column(name="refund_holder")
	private String refundHolder; 
	
	@Column(name="reg_date", nullable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date regDate;
	
	//12강 p51: ManyToOne에는 mappedBy 속성 불필요
	@ManyToOne(cascade = CascadeType.ALL) //Many가 Join, One이 GroupPurchase
	@JoinColumn(name="grouppurchase_id") //DB 상에서 FK의 이름
	private GroupPurchase groupPurchase; //주최자 정보 포함
	
	public boolean matchesZipcode() {
		return zipcode.matches("^\\d{5}$");
	}
	
	public boolean matchesPhone() {
		return phone.matches("^01([0-9])(\\d{3,4})(\\d{4})$");
	}
}
