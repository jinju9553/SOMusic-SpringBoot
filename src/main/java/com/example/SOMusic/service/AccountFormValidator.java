package com.example.SOMusic.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.SOMusic.controller.AccountForm;
import com.example.SOMusic.domain.Account;

@Component
public class AccountFormValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return Account.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		AccountForm accountForm = (AccountForm)obj; 
		Account account = accountForm.getAccount();

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account.userName", "USER_NAME_REQUIRED", "*닉네임은 필수 항목입니다.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account.email", "EMAIL_REQUIRED", "*이메일은 필수 항목입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account.phone", "PHONE_REQUIRED", "*전화번호는 필수 항목입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account.address", "ADDRESS_REQUIRED", "*주소는 필수 항목입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account.zipcode", "ZIPCODE_REQUIRED", "*우편 번호는 필수 항목입니다.");
		
		if (accountForm.isNewAccount()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account.userId", "USER_ID_REQUIRED", "*ID는 필수 항목입니다.");
			if (account.getPassword() == null || account.getPassword().length() < 1 ||
					!account.getPassword().equals(accountForm.getRepeatedPassword())) {
				errors.reject("PASSWORD_MISMATCH",
					 "*패스워드가 일치하지 않거나 입력되지 않았습니다."); //서버에서만 확인하는 오류 메시지
			}
		}
		else if (account.getPassword() != null && account.getPassword().length() > 0) {
			if (!account.getPassword().equals(accountForm.getRepeatedPassword())) {
				errors.reject("PASSWORD_MISMATCH", "*패스워드가 일치하지 않습니다.");
			}
		}
		
		if(account.getPhone() != null && account.getPhone().length() > 0) {
			if(!account.matchesPhone())
				errors.rejectValue("account.phone", "PHONE_NOT_CORRECT", "*잘못된 형식입니다. ex)01011113333, 0112224444");
		}
		
		if(account.getZipcode() != null && account.getZipcode().length() > 0) {
			if(!account.matchesZipcode())
				errors.rejectValue("account.zipcode", "ZIPCODE_NOT_CORRECT", "*잘못된 형식입니다. ex)12345");
		}
	}
}