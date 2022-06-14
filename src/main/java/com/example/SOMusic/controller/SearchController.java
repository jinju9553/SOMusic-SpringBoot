package com.example.SOMusic.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.ProductService;

@Controller
@RequestMapping("/main")
public class SearchController {
	
	private static final String GP_SEARCH = "thyme/search/GPSearchList";
	private static final String PRODUCT_SEARCH = "thyme/search/ProductSearchList";
	
	@Autowired
	private ProductService prSvc;
	public void setProductService(ProductService prSvc) {
		this.prSvc = prSvc;
	}	
	
	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}
	
	@RequestMapping(value="/product/search", method = RequestMethod.GET)
	public String searchProduct(@RequestParam("keyword") String keyword, Model model) throws Exception {
		
		List<Product> productList = prSvc.getSearchProductList(keyword);
		
//		System.out.println("검색 : " + keyword + " >>> " + productList);
		
		model.addAttribute("productList", productList);
		model.addAttribute("keyword", keyword);
		
		return PRODUCT_SEARCH;
	}
	
	@RequestMapping(value="/gp/search", method = RequestMethod.GET)
	public String searchGP(@RequestParam("keyword") String keyword, Model model) throws Exception {
		
		List<GroupPurchase> gpList = gpSvc.getSearchGPList(keyword);
		
//		System.out.println("검색 : " + keyword + " >>> " + gpList);
		
		model.addAttribute("gpList", gpList);
		model.addAttribute("keyword", keyword);
		
		return GP_SEARCH;
	}
	
	
	
}
