package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.SOMusic.service.ProductService;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping(value="/product", method = RequestMethod.GET)
public class ProductViewController {

	private ProductService prSvc;
	public void setProductService(ProductService prSvc) {
		this.prSvc = prSvc;
	}	
	
	@RequestMapping(value="/view", method = RequestMethod.GET) 
	public String showProductView() {
		return "product/view/productView";
	}
}	

