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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.ProductService;
import com.example.SOMusic.service.PurchaseService;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	
	private static final String PURCHASE_FORM = "purchase/purchaseForm";
	private static final String PURCHASE_INFO = "purchase/myPurchaseInfo";
	
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
	
	//1.Validator 작성 필요
	//@Autowired
	//private OrderValidator orderValidator;
	
	@ModelAttribute("paymentOption")
	public String[] referenceData() {
		return new String[] { "신용/체크카드", "무통장 입금", "실시간 계좌이체", "네이버 페이", "toss" };
	}

	@GetMapping("/info") //테스트용 uri
	public String showForm2() {
		return PURCHASE_INFO;
	}
	
	@GetMapping("/{productId}")
	@ModelAttribute("purchaseReq") // request handler methods 보다 먼저 호출됨
	public ModelAndView formBacking(HttpServletRequest request ,
			@PathVariable("productId") int productId, Model model) {
		PurchaseRequest purchaseReq = new PurchaseRequest();

		Product p = productService.findProductByProductId(productId);

		// 만약 배송지 '주문자와 동일' 옵션을 선택했을 경우 ==> ajax 콜 사용
		// 1.UserSession에서 UserId를 뽑아낸다.
		// UserSession userSession = (UserSession)
		// request.getSession().getAttribute("userSession");

		// 2.Account를 통해 이 유저의 address 및 기본 정보를 읽어와서 세팅한다.
		// Account account =
		// petStore.getAccount(userSession.getAccount().getUsername());

		purchaseReq.setProduct(p); // 정보를 세팅하여 Form에 초기값으로 나타낸다.
		model.addAttribute("purchaseReq", purchaseReq);

		return new ModelAndView(PURCHASE_FORM);
	}
	
	@PostMapping("/{productId}")
	public String register( 
			@ModelAttribute("purchaseReq") PurchaseRequest purchaseReq,
			@PathVariable("productId") int productId,
			BindingResult bindingResult, Model model) throws ModelAndViewDefiningException {
		System.out.println("command 객체: " + purchaseReq); //왜 product가 null이 되었는지??
		
		Purchase p = new Purchase();
		
		if (bindingResult.hasErrors()) {
			return PURCHASE_FORM;
		}

		p.initPurchase(purchaseReq);
		p.setProduct(productService.findProductByProductId(productId));
		p.setTotalAmount(purchaseService.calculateTotal(p.getProduct()));
		purchaseService.registerPurchase(p); //주의: consumerId 없이 삽입됨 ==> 세션 필요

		model.addAttribute("purchaseReq", p); //View에 객체 전달하고 간략한 정보 출력
		
		return PURCHASE_FORM; //결과 화면 새로 만들기(PURCHASE_RESULT)
	}
	
	@GetMapping("/info/{purchaseId}")
	public String formBackingInfo(HttpServletRequest request,
				@PathVariable("purchaseId") int purchaseId, Model model) {
			PurchaseRequest purchaseReq = new PurchaseRequest();
			Purchase p = purchaseService.findPurchaseByPurchaseId(purchaseId);

			//2.Purchase에서 뽑아온 값을 purchaseReq에 세팅한다.
			purchaseReq.initPurchaseReq(p);

			//3.model에 세팅한다.
			model.addAttribute("purchaseReq", purchaseReq);
			model.addAttribute("product", purchaseReq.getProduct());

			return PURCHASE_INFO;
	}
	
	@PostMapping("/info/{purchaseId}")
	public String update(
			@ModelAttribute("purchaseReq") PurchaseRequest purchaseReq,
			BindingResult result) throws ModelAndViewDefiningException {
		Purchase p = new Purchase();

		p.initPurchase(purchaseReq);
		
		purchaseService.modifyPurchase(p);
		purchaseReq.initPurchaseReq(p);

		return "redirect:/" + "purchase/info/{purchaseId}"; //본래의 경로로 redirection
	}
	
	//6.PurchaseList - 사용자가 구매한 중고상품 목록 (findAllByUserId)
	//7.PurchaseSearch - 키워드를 포함하는 중고상품 들을 검색 (DAO에서 정의)
	//8.PurchaseView(Confirm) - 사용자가 작성한 구매폼을 판매자가 승인함
	
}
