package com.example.SOMusic.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.service.ProductService;

@Controller
@RequestMapping(value="/product")
public class ProductRegisterController {
	
	private static final String Product_REGISTER_FORM = "thyme/product/register/productRegisterForm";
	private static final String Product_REGISTER_SEUCCESS = "/product/register/success";	// redirect
	private static final String Product_REGISTER_SEUCCESS_View = "thyme/product/register/ProductRegisterSuccess";
	
	@Autowired
	private ProductService prSvc;
	public void setProductService(ProductService prSvc) {
		this.prSvc = prSvc;
	}
	
	
  @ModelAttribute("PrReq")
  public ProductRequest formBacking(HttpServletRequest request) throws Exception { 
		 String PrId = request.getParameter("PrId"); 
		 ProductRequest prReq = new ProductRequest(); 
		  
		  //PrId가 없으면 register 
		  if (PrId == null) 
			  return prReq; 
		  //있으면 update
		  else {
			  prReq.initProductReq(prSvc.findProductByProductId(Integer.parseInt(PrId))); 
			  return prReq; 
			  }
		  }
	
  
  //register
  @GetMapping(value="/register") 
  public String showProductRegForm() {
	  	System.out.println("폼 불러옴");
		 return Product_REGISTER_FORM; 
		  }
	 
		
  @PostMapping(value="/register")
  public String register(@ModelAttribute("prReq") ProductRequest prReq, 
			Errors errors, Model model) throws Exception {
		
	  System.out.println("ㅇㅇㅇㅇㅇ");
	  
		  //errors 
	  	if(errors.hasErrors()) { 
	  		return Product_REGISTER_FORM; 
	  		}
	  
		  //이미지 업로드 추후 추가
		  
		  Product pr = new Product(); 
		  //sellerId 추가 
		  pr.setSellerId("panda");
		  pr.initPr(prReq);
		  
		  
		  
		  
		  //DB에 추가
		  prSvc.addProduct(pr);
		 
		
		return "redirect:" + Product_REGISTER_SEUCCESS; //redirect
	}
	
	@RequestMapping(value="/register/success", method = RequestMethod.GET)
	public String success(Model model) throws Exception {
		System.out.println("등록 완료");
		
		return Product_REGISTER_SEUCCESS_View;
	}	 
	
	
	
	
	}
	


