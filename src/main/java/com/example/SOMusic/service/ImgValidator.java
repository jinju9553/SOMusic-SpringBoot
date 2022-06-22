package com.example.SOMusic.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ImgValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return String.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object obj, Errors errors) {
		String img = (String) obj;
		
		if (img.equals("off")) {
			errors.rejectValue("imgCheck", "IMG_REQUIRED", "필수 입력 항목입니다.");
		}
		
	}
}
