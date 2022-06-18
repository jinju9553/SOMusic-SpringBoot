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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	
	@NotEmpty(message = "*이름을 입력해주세요")
	@Column(name="consumer_name") 
	private String consumerName;
	
	//@NotEmpty(message = "*은행명을 입력해주세요")
	@Column(name="consumer_bank") 
	private String consumerBank;
	
	@NotNull
	@Column(name="total_amount", nullable=false)
	private int totalAmount;
	
	@NotEmpty(message = "*주소를 입력해주세요")
	private String address;
	
	@NotEmpty(message = "*우편번호를 입력해주세요")
	private String zipcode;
	
	@NotEmpty(message = "*전화번호를 입력해주세요")
	private String phone;
	
	@NotNull
	@Column(name="shipping_cost")
	private int shippingCost;
	
	@NotNull(message = "*배송방법을 선택해주세요")
	@Column(name="shipping_method", nullable=false)
	private int shippingMethod; //1: 준등기, 2: 택배, 3: 택배(제주산간)
	
	@Column(name="shipping_request")
	private String shippingRequest;

	//@NotNull @Positive //Info에서는 적용되면 안됨 ==> GlobalValidator 말고 따로 정의할 것
	private int quantity;
	private int status; //0: join 이전 & 1: 승인됨, 입금 대기 & 2: 입금 완료, 배송 대기중 & 3:배송 시작 & 4: 거래 완료
	
	@NotEmpty(message = "*입금자명을 입력해주세요")
	@Column(name="account_holder")
	private String accountHolder; 
	
	@NotEmpty(message = "*환불계좌 은행명을 입력해주세요")
	@Column(name="refund_bank")
	private String refundBank; 
	
	@NotEmpty(message = "*환불계좌 번호를 입력해주세요")
	@Column(name="refund_account")
	private String refundAccount; 
	
	@NotEmpty(message = "*환불 예금주명을 입력해주세요")
	@Column(name="refund_holder")
	private String refundHolder; 
	
	@Column(name="reg_date", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
	//mappedBY
	@ManyToOne(cascade = CascadeType.ALL) //Many가 Join, One이 GroupPurchase
	@JoinColumn(name="grouppurchase_id") //DB 상에서 FK의 이름
	private GroupPurchase groupPurchase; //주최자 정보 포함
}
