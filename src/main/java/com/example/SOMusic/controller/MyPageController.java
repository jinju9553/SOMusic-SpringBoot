package com.example.SOMusic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.PurchaseService;

@Controller
@RequestMapping("/user/my")
public class MyPageController {
	private static final String MY_PURCHASE_LIST = "thyme/user/my/purchase/MyPurchaseList";
	
	@Autowired
	private PurchaseService purchaseService;
	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	@RequestMapping(value="/purchase/list/{userId}", method = RequestMethod.GET)
	public String registerList(@PathVariable("userId") String userId, Model model) throws Exception {
		
		//userId는 세션에서 받아오면 더 좋음
		List<Purchase> pList = purchaseService.findPurchaseList(userId);
		model.addAttribute("pList", pList);
		
		System.out.println("구매한 상품 리스트 : " + pList.toString());
		
		return MY_PURCHASE_LIST;
	}
}
