package com.example.SOMusic.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class Join extends Order{
	private int joinId;
	private int groupPurchaseId;
	private int shippingCost;
	private int quantity;
	private int status;
	private String refundAccount;
	private String refundBank;
}
