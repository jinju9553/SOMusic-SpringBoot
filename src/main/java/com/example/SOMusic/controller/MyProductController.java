package com.example.SOMusic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SOMusic.service.ProductService;
import com.example.SOMusic.domain.Product;

@Controller
@RequestMapping("/user/my/product")
public class MyProductController {
	
	private static final String MY_PRODUCT_LIST = "thyme/user/my/product/mySaleList";
	private static final String PRODUCT_INFO = "thyme/Product/View/productView";
	
	@Autowired
	private ProductService prSvc;
	public void setPrService(ProductService prSvc) {
		this.prSvc = prSvc;
	}
	
	@GetMapping(value="/info")
	public String info(@RequestParam("ProductId") int ProductId, Model model) {
		Product pr = prSvc.findProductByProductId(ProductId);
		model.addAttribute("pr", pr);
		
		return PRODUCT_INFO;
	}
	
	@GetMapping(value="/sale/List")
	public String saleList(@RequestParam("sellerId") String sellerId, Model model)
	throws Exception {
		System.out.println("등록한 상품 리스트 출력중");
		System.out.println("sellerId : " + sellerId);
		
		List<Product> prList = prSvc.getMyPrList(sellerId);
		model.addAttribute("prList", prList);
		
		System.out.println(prList.toString());
		
		return MY_PRODUCT_LIST; 
		
	}
	
	
}
