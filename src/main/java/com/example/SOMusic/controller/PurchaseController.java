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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.ProductService;
import com.example.SOMusic.service.PurchaseService;
import com.example.SOMusic.service.PurchaseValidator;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/purchase")
public class PurchaseController {
	
	private static final String PURCHASE_FORM = "purchase/purchaseForm";
	private static final String PURCHASE_DONE = "thyme/purchase/purchaseDone";
	
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
	
	@Autowired
	private PurchaseValidator validator;
	public void setValidator(PurchaseValidator validator) {
		this.validator = validator;
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
			purchaseReq.setShippingMethod(0);
			
			return purchaseReq;
		} 
		else
			return new Purchase();
	}
	
	@PostMapping("/{productId}")
	public String register( 
			HttpServletRequest request,
			@PathVariable("productId") int productId,
			@ModelAttribute("purchaseReq") Purchase purchaseReq,
			BindingResult result, Model model) throws Exception {
		
		purchaseReq.setProduct(productService.findProductByProductId(productId));
		Login userSession = 
				(Login) WebUtils.getSessionAttribute(request, "userSession");
		
		validator.validate(purchaseReq, result);
		
		if (result.hasErrors()) {
			for ( ObjectError r :  result.getAllErrors()) {
				System.out.println(r);
			}
			model.addAttribute("purchaseReq", purchaseReq);
			return PURCHASE_FORM;
		}

		Account account = accountService.getAccount(userSession.getAccount().getUserId());
		
		purchaseReq.setTotalAmount(purchaseService.calculateTotal(purchaseReq.getProduct()));
		purchaseReq.setConsumerId(account.getUserId());
		purchaseService.registerPurchase(purchaseReq); 

		return PURCHASE_DONE;
	}
}
