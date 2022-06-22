package com.example.SOMusic.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.example.SOMusic.domain.Product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ProductRequest implements Serializable {
	private int productId;
	
	@NotBlank(message="필수 입력 항목입니다.")
	private String productName;
	
	private String sellerId;
	
	@Min(value=1, message="필수 입력 항목입니다.")
	private int price;
	
	@Min(value=1, message="필수 입력 항목입니다.")
	private int shippingCost;

	private MultipartFile image;
	private String imgCheck;
	
	@NotBlank(message="필수 입력 항목입니다.")
	private String description;
	
	private int condition;
	
	private int status;
	
	@NotNull(message="필수 입력 항목입니다.")
	private int account;
	
	@NotBlank(message="필수 입력 항목입니다.")
	private String bank;
	
	@NotBlank(message="필수 입력 항목입니다.")
	private String artistName;
	
	@NotBlank(message="필수 입력 항목입니다.")
	private String location;
	
	public ProductRequest () {}
	public ProductRequest(int productId, String productName, MultipartFile image,
			String sellerId, String buyer,
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
	public ProductRequest(int productId, String productName, String location,
			MultipartFile image, int price, int shippingCost, String description,
			int condition, String artistName) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.image = image;
		this.price = price;
		this.location = location;
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
		bank = p.getBank();
		shippingCost = p.getShippingCost();
		account = p.getAccount();	
		location = p.getLocation();
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
