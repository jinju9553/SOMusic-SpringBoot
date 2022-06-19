package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.PurchaseService;

@Controller
@RequestMapping("/purchase")
public class MyPurchaseController {
	private static final String PURCHASE_INFO = "purchase/myPurchaseInfo";
	
	@Autowired
	private PurchaseService purchaseService;
	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	@GetMapping("/info/{purchaseId}")
	public String showForm() {
		return PURCHASE_INFO;
	}
	
	@ModelAttribute("purchaseInfoReq")
	public Purchase formBackingInfo(HttpServletRequest request,
			@PathVariable("purchaseId") int purchaseId, Model model) {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			Purchase purchaseReq = purchaseService.findPurchaseByPurchaseId(purchaseId);
	
			model.addAttribute("product", purchaseReq.getProduct());
	
			return purchaseReq;
		} 
		else
			return new Purchase();
	}
	
	@PostMapping("/info/{purchaseId}")
	public String update(
			@ModelAttribute("purchaseInfoReq") Purchase purchaseReq,
			BindingResult result) throws Exception {
		//주의: form으로부터 받은 product가 없음 ==> 독자적인 DAO 사용이라 productId에 영향 X
		
		purchaseService.modifyPurchase(purchaseReq);

		return "redirect:/" + "purchase/info/{purchaseId}"; //본래의 경로로 redirection
	}
}
