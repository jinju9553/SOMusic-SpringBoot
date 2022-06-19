package com.example.SOMusic.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.example.SOMusic.domain.Product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequest implements Serializable {
	private int productId;
	private String productName;
	private String sellerId;
	private int price;
	private int shippingCost;
	//private String image;
	private MultipartFile image;	
	private String description;
	private int condition;
	private int status;
	private int account;
	private String bank;
	private String artistName;
	
	public ProductRequest () {}
	public ProductRequest(int productId, String productName, MultipartFile image,
			String sellerId,
			int price, int shippingCost, String description, int condition,
			int status, int account, String bank,
			String artistName) {
		this.productId = productId;
		this.productName = productName;
		this.image = image;
		this.sellerId = sellerId;
		this.price = price;
		this.shippingCost = shippingCost;
		this.description = description;
		this.condition = condition;
		this.account = account;
		this.bank = bank;
		this.condition = condition;
		this.artistName = artistName;
	}
	public ProductRequest(int productId, String productName,
			MultipartFile image, int price, int shippingCost, String description,
			int condition, String artistName) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.image = image;
		this.price = price;
		this.shippingCost = shippingCost;
		this.description = description;
		this.condition = condition;
		this.artistName = artistName;
	}
	
	
	// Public Methods
	public void initProductReq(Product p) {
		productName = p.getProductName();
		sellerId = p.getSellerId();
		price = p.getPrice();
		image = getFile(p.getImage());
		description = p.getDescription();
		condition = p.getCondition();
		artistName = p.getArtistName();
	}
	public MultipartFile getFile(String path) {
		File file = new File(path);
		
		try {
			FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
		    
			InputStream input = new FileInputStream(file);
		    OutputStream os = fileItem.getOutputStream();
		    IOUtils.copy(input, os);
		    
		    MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
		    return multipartFile;
		    // Or faster..
		    // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
		} catch (IOException ex) {
		    // do something.
		}

        return null;
     
	}
}
