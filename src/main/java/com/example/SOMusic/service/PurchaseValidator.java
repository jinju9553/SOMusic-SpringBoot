package com.example.SOMusic.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.SOMusic.domain.Purchase;


@Component
public class PurchaseValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return Purchase.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object obj, Errors errors) {
		Purchase purchase = (Purchase) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "PHONE_REQUIRED", "*필수 항목입니다.");
		if(purchase.getShippingMethod() == 0) { //직거래일 때만 체크
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "ADDRESS_REQUIRED", "*필수 항목입니다.");
		}
		else { //택배일 때만 체크
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "consumerName", "NAME_REQUIRED", "*필수 항목입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipcode", "ZIPCODE_REQUIRED", "*필수 항목입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "ADDRESS_REQUIRED", "*필수 항목입니다.");
		}
		
		if(purchase.getPhone() != null && purchase.getPhone().length() > 0) {
			if(!purchase.matchesPhone())
				errors.rejectValue("phone", "PHONE_NOT_CORRECT", "*잘못된 형식입니다. ex)01011113333, 0112224444");
		}
		
		if(purchase.getZipcode() != null && purchase.getZipcode().length() > 0) {
			if(!purchase.matchesZipcode())
				errors.rejectValue("zipcode", "ZIPCODE_NOT_CORRECT", "*잘못된 형식입니다. ex)12345");
		}
	}
}
