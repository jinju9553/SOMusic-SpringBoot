package com.example.SOMusic.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.SOMusic.domain.Join;


@Component
public class JoinValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return Join.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object obj, Errors errors) {
		Join join = (Join)obj; 

		//상시 수정 가능
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "refundHolder", "REFUND_HOLDER_REQUIRED", "*필수 항목입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "refundAccount", "REFUND_ACCOUNT_REQUIRED", "*필수 항목입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "refundBank", "REFUND_BANK_REQUIRED", "*필수 항목입니다.");
		
		int status = join.getStatus();
		if(status < 4) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "consumerName", "NAME_REQUIRED", "*필수 항목입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "PHONE_REQUIRED", "*필수 항목입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipcode", "ZIPCODE_REQUIRED", "*필수 항목입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "ADDRESS_REQUIRED", "*필수 항목입니다.");
			if(status < 3) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountHolder", "ACCOUNT_HOLDER_REQUIRED", "*필수 항목입니다.");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "consumerBank", "BANK_REQUIRED", "*필수 항목입니다.");
			}
		}
	}
}
