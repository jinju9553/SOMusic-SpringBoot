package com.example.SOMusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.PurchaseService;

@Controller //또는 @RestController
@RequestMapping("/purchase") //무슨 경로로 할 것인지? (/{productId}/purchase)
public class PurchaseController {
	
	private static final String PURCHASE_FORM = "purchase/purchaseForm";
	private static final String PURCHASE_INFO = "purchase/myPurchaseInfo";
	
//	@Autowired	// 자동주입이 계속 실패하여 잠시 주석처리 해둡니다.
	private PurchaseService purchaseService;
	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	//1.Validator 작성 필요
	//@Autowired
	//private OrderValidator orderValidator;

	@ModelAttribute("paymentOption")
	public String[] referenceData() {
		return new String[] { "신용/체크카드", "무통장 입금", "실시간 계좌이체", "네이버 페이", "toss" };
	}

	//2.showForm()
	@GetMapping
	public String showForm() {
		return PURCHASE_FORM;
	}
	
	@GetMapping("/id")
	public String showForm2() {
		return PURCHASE_INFO;
	}
	
	//3.formBacking()
	@ModelAttribute("purchaseReq") // request handler methods 보다 먼저 호출됨
	public PurchaseRequest formBacking(HttpServletRequest request) { //Accessor 메소드
		if (request.getMethod().equalsIgnoreCase("GET")) { //equalsIgnoreCase: 대소문자 구분없이 비교
			PurchaseRequest purchaseReq = new PurchaseRequest();
			
			//만약 배송지 '주문자와 동일' 옵션을 선택했을 경우 ==> ajax 콜 사용
			//1.UserSession에서 UserId를 뽑아낸다.
			//2.Account를 통해 이 유저의 address 및 기본 정보를 읽어와서 세팅한다.
			//purchaseReq.setAddress("address"); //정보를 세팅하여 Form에 초기값으로 나타낸다.	// 메소드가 정의되지 않아 오류가 나는 상태로 잠시 주석처리 했습니다.

			return purchaseReq;
		}
		else return new PurchaseRequest(); //?: get 요청이 아닐 때 실행하는 것인지?
	}
	
	//4.submit() ==> 같은 url의 POST로 매핑
	@PostMapping
	public String register( 
			@ModelAttribute("regReq") PurchaseRequest purchaseReq, //Commmand 객체로 사용
			BindingResult bindingResult, Model model) {
		System.out.println("command 객체: " + purchaseReq);
		
		// validator 생성 및 호출 (입력 값 검증), validator 패키지에 포함됨
		//new OrderValidator().validate(purchaseReq, bindingResult);
		if (bindingResult.hasErrors()) {
			return PURCHASE_FORM;
		}

		//Purchase p = purchaseService.registerPurchase(purchaseReq); //신청자를 등록하고 객체를 리턴

		//JPetStore: mav.addObject("order", orderForm.getOrder());
		//model.addAttribute("purchase", p); //View에 객체 전달하고 간략한 정보 출력
		return "purchase/registered";
	}
	
	//5.PurchaseInfo - 사용자가 구매한 특정 중고상품 하나의 정보
	//6.PurchaseList - 사용자가 구매한 중고상품 목록
	//7.PurchaseSearch - 특정 중고상품 하나를 검색
	//8.PurchaseUpdate - 사용자가 구매한 특정 중고상품 하나의 배송지 수정
	//9.PurchaseView(Confirm) - 사용자가 작성한 구매폼을 판매자가 승인함
	
}
