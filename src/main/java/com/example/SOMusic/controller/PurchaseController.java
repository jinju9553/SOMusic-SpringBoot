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
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.ProductService;
import com.example.SOMusic.service.PurchaseService;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	
	private static final String PURCHASE_FORM = "purchase/purchaseForm";
	
	@Autowired //주의: interface는 class가 아니므로 Bean을 생성할 수 없음
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
	private AccountService accountService;
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	//1.Validator 작성 필요
	//@Autowired
	//private OrderValidator orderValidator;
	
	@ModelAttribute("paymentOption")
	public String[] referenceData() {
		return new String[] { "현금 거래", "계좌 이체", "카카오 페이", "toss", "그 외" };
	}

	@GetMapping("/{productId}")
	public String showForm() {
		return PURCHASE_FORM;
	}
		
	@ModelAttribute("purchaseReq") // request handler methods 보다 먼저 호출됨
	public Purchase formBacking(HttpServletRequest request ,
			@PathVariable("productId") int productId, Model model) {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			Purchase purchaseReq = new Purchase();
			Product p = productService.findProductByProductId(productId);
	
			// 1.UserSession에서 UserId를 뽑아낸다.
			Login userSession = 
					(Login) WebUtils.getSessionAttribute(request, "userSession");
	
			// 2.Account를 통해 이 유저의 address 및 기본 정보를 읽어와서 세팅한다.
			Account account = new Account();
			if(userSession != null) {
				account = accountService.getAccount(userSession.getAccount().getUserId());
			}

			model.addAttribute("account", account);
			purchaseReq.setProduct(p); // 정보를 세팅하여 Form에 초기값으로 나타낸다.
			
			return purchaseReq;
		} 
		else
			return new Purchase();
	}
	
	@PostMapping("/{productId}")
	public String register( 
			HttpServletRequest request,
			@ModelAttribute("purchaseReq") Purchase purchaseReq,
			@PathVariable("productId") int productId,
			BindingResult bindingResult, Model model) throws Exception {
		System.out.println("command 객체: " + purchaseReq);
		
		Login userSession = 
				(Login) WebUtils.getSessionAttribute(request, "userSession");
		
		if (bindingResult.hasErrors()) {
			return PURCHASE_FORM;
		}

		Account account = accountService.getAccount(userSession.getAccount().getUserId());
		
		purchaseReq.setProduct(productService.findProductByProductId(productId));
		purchaseReq.setTotalAmount(purchaseService.calculateTotal(purchaseReq.getProduct()));
		purchaseReq.setConsumerId(account.getUserId());
		purchaseService.registerPurchase(purchaseReq); 

		return "redirect:/" + "purchase/{productId}"; //결과 화면 새로 만들기(PURCHASE_RESULT)
	}
	
	//8.PurchaseView(Confirm) - 사용자가 작성한 구매폼을 판매자가 승인함
}
