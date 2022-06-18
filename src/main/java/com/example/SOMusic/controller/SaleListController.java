package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.service.PurchaseService;
import com.example.SOMusic.service.WishProductService;
import com.example.SOMusic.service.ProductService                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ;



@Controller
@SessionAttributes("userSession")
@RequestMapping("/user/my")
public class SaleListController {
	
	//@Autowired
	private ProductService productService;
	public void setroductService(WishProductService wishproductService) {
		this.productService = productService;
	}
	
	@RequestMapping(value="/SaleList", method = RequestMethod.GET)
	public String showSaleList(Model model) throws Exception {
		
		return "product/Sale/saleList"; 
	}
}
