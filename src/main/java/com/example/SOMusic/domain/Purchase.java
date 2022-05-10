package com.example.SOMusic.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class Purchase extends Order{
	private int purchaseId;
	private int productId;
	private int status;
}
