package com.example.SOMusic.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.example.SOMusic.domain.GroupPurchase;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class GPRequest implements Serializable {

	private int gpId;

	private String sellerId;

	@NotBlank(message="필수 입력 항목입니다.")
	private String title;
	
//	@Column(name="IMAGE")
//	private String image;
	
	@NotNull(message="필수 입력 항목입니다.")
	private MultipartFile image;

	@NotNull(message="필수 입력 항목입니다.")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate startDate;
	
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
	
	public GPRequest() { }

	public GPRequest(int gpId, String sellerId, String title, MultipartFile image,
			LocalDate startDate, LocalDate endDate, String category,
			String account, String bank, int price, String description) {
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
	
	public void initGpReq(GroupPurchase gp) {
		gpId = gp.getGpId();
		sellerId = gp.getSellerId();
		title = gp.getTitle();
		image = getFile(gp.getImage());
		startDate = gp.getStartDate();
		endDate = gp.getEndDate();
		category = gp.getCategory();
		account = gp.getAccount();
		bank = gp.getBank();
		price = gp.getPrice();
		description = gp.getDescription();
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
//        MultipartFile multipartFile = new MockMultipartFile("test.xlsx", new FileInputStream(new File("/home/admin/test.xlsx")));
	}
	
}