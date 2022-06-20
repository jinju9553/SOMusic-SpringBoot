package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.ProductService;
import com.example.SOMusic.service.PurchaseService;
import com.example.SOMusic.service.PurchaseValidator;

@Controller
@RequestMapping("/purchase")
public class MyPurchaseController {
	private static final String PURCHASE_INFO = "purchase/myPurchaseInfo";
	
	@Autowired
	private PurchaseService purchaseService;
	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	@Autowired 
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@Autowired
	private PurchaseValidator validator;
	public void setValidator(PurchaseValidator validator) {
		this.validator = validator;
	}
	
	@GetMapping("/info/{purchaseId}")
	public String showForm() {
		return PURCHASE_INFO;
	}
	
	@ModelAttribute("purchaseInfoReq")
	public Purchase formBackingInfo(HttpServletRequest request,
			@PathVariable("purchaseId") int purchaseId) {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			Purchase purchaseReq = purchaseService.findPurchaseByPurchaseId(purchaseId);
	
			//model.addAttribute("product", purchaseReq.getProduct());
	
			return purchaseReq;
		} 
		else
			return new Purchase();
	}
	
	@PostMapping("/info/{purchaseId}")
	public String update(
			@RequestParam("productId") int productId,
			@ModelAttribute("purchaseInfoReq") Purchase purchase,
			BindingResult result, Model model) throws Exception {
		//주의: form으로부터 product가 받아지지 않음
		purchase.setProduct(productService.findProductByProductId(productId));

		validator.validate(purchase, result);
		
		if(result.hasErrors()) {
			for ( ObjectError r :  result.getAllErrors()) {
				System.out.println(r);
			}
			model.addAttribute("purchaseInfoReq", purchase);
			return PURCHASE_INFO;
		}
		
		purchaseService.modifyPurchaseInfo(purchase.getPurchaseId(), purchase);

		return "redirect:/" + "purchase/info/{purchaseId}"; //본래의 경로로 redirection
	}
	
	//8.PurchaseView(Confirm) - 사용자가 작성한 구매폼을 판매자가 승인함
	//1.폼 띄우고
	//2.확인 버튼을 누르면 3.DAO로 넘어가서 status만 수정!
}
