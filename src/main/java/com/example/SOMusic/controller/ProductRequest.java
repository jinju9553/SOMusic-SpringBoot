package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequest {
	private String productName;
	private String sellerId;
	private int price;
	private String image;
	private String description;
	private int condition;
	private int status;
	private String artistName;
}
