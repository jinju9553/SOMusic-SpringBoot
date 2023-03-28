package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.ProductService;
import com.example.SOMusic.service.PurchaseService;
import com.example.SOMusic.service.PurchaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/purchase")
public class PurchaseController {

	private static final int DEFAULT_SHIPPING_METHOD = 0;
	private static final String PURCHASE_FORM = "purchase/purchaseForm";
	private static final String PURCHASE_DONE = "thyme/purchase/purchaseDone";

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

	@ModelAttribute("purchaseReq")
	public Purchase formBacking(HttpServletRequest request,
								@PathVariable("productId") int productId, Model model) {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			Login userSession =
					(Login) WebUtils.getSessionAttribute(request, "userSession");

			Account account = new Account();
			if (isLoggedIn(userSession)) {
				account = accountService.getAccount(userSession.getAccount().getUserId());
			}

			Purchase purchaseReq = new Purchase();
			Product product = productService.findProductByProductId(productId);

			purchaseReq.setProduct(product);
			purchaseReq.setShippingMethod(DEFAULT_SHIPPING_METHOD);

			model.addAttribute("account", account);

			return purchaseReq;
		} else {
			return new Purchase();
		}
	}

	@PostMapping("/{productId}")
	public String register(
			HttpServletRequest request,
			@PathVariable("productId") int productId,
			@ModelAttribute("purchaseReq") Purchase purchaseReq,
			BindingResult result, Model model) throws Exception {

		Product product = productService.findProductByProductId(productId);
		purchaseReq.setProduct(product);

		Login userSession =
				(Login) WebUtils.getSessionAttribute(request, "userSession");

		validator.validate(purchaseReq, result);

		if (result.hasErrors()) {
			model.addAttribute("purchaseReq", purchaseReq);
			return PURCHASE_FORM;
		}

		if (isLoggedIn(userSession)) {
			Account account = userSession.getAccount();
			purchaseReq.setConsumerId(account.getUserId());
		}

		purchaseReq.setTotalAmount(purchaseService.calculateTotal(purchaseReq.getProduct()));

		purchaseService.registerPurchase(purchaseReq);

		return PURCHASE_DONE;
	}

	private static boolean isLoggedIn(Login userSession) {
		return (userSession != null);
	}
}
