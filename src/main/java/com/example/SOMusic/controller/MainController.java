package com.example.SOMusic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.ProductService;

@Controller
@SessionAttributes("userSession")
public class MainController {
	
	private static final String MAIN = "/main";
	private static final String MAIN_VIEW = "thyme/main";
	private static final String MAIN_GP_VIEW = "thyme/gp/list/GPListView";
	private static final String MAIN_PRODUCT_VIEW = "thyme/Product/list/ProductListView";
	
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
	
	@RequestMapping("/")
	public String goMain() {
		return "redirect:" + MAIN;
	}
	
	@RequestMapping(value="/main", method = RequestMethod.GET)
	public String showMainList(Model model) {
		
		List<Product> productList = prSvc.getMainProductList();
		List<GroupPurchase> gpList = gpSvc.getMainGPList();
		
		model.addAttribute("gpList", gpList);
		model.addAttribute("productList", productList);
		
		return MAIN_VIEW;
	}
	
	@RequestMapping(value="/main/product", method = RequestMethod.GET)
	public String showProductList(Model model) {
		
		List<Product> productList = prSvc.getAllProductList();
		model.addAttribute("productList", productList);
		
		return MAIN_PRODUCT_VIEW;
	}
	
	@RequestMapping(value="/main/gp", method = RequestMethod.GET)
	public String showGPList(Model model) {

		List<GroupPurchase> gpList = gpSvc.getAllGPList();
		model.addAttribute("gpList", gpList);

		return MAIN_GP_VIEW;
	}

}
