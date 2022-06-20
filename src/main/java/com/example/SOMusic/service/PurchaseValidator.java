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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "consumerName", "NAME_REQUIRED", "*필수 항목입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "PHONE_REQUIRED", "*필수 항목입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipcode", "ZIPCODE_REQUIRED", "*필수 항목입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "ADDRESS_REQUIRED", "*필수 항목입니다.");
	}
}
