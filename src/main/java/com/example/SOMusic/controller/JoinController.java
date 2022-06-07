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

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.JoinService;

@Controller //또는 @RestController
@RequestMapping("/join")
public class JoinController {
private static final String JOIN_FORM = "join/joinForm";
private static final String JOIN_INFO = "join/myJoinInfo";
	
	@Autowired
	private JoinService joinService;
	public void setJoinService(JoinService joinService) {
		this.joinService = joinService;
	}
	
	@Autowired
	private GPService gpService;
	public void setGPService(GPService gpService) {
		this.gpService = gpService;
	}

	@ModelAttribute("shippingOption")
	public Object[] referenceData() { //String이나 Object만 전송 가능
		return new Object[] { 1, 2, 3 }; //"준등기 (+1,800원)", "택배 (+3,000원)", "택배(제주산간) (+6,000원)"
	}
	
	//1.Validator 작성 필요
	//@Autowired
	//private OrderValidator orderValidator;

	//2.showForm()
	@GetMapping("/{id}")
	public String showForm() {
		return JOIN_FORM;
	}
	
	@GetMapping("/info") //테스트용 uri
	public String showForm2() {
		return JOIN_INFO;
	}
	
	//3.formBacking()
	@ModelAttribute("joinReq") // request handler methods 보다 먼저 호출됨
	public JoinRequest formBacking(HttpServletRequest request) { //Accessor 메소드
		if (request.getMethod().equalsIgnoreCase("GET")) { //equalsIgnoreCase: 대소문자 구분없이 비교
			JoinRequest joinReq = new JoinRequest();
			//GroupPurchase gp = gpService.findById
			
			//만약 배송지 '주문자와 동일' 옵션을 선택했을 경우 ==> ajax 콜 사용
			//1.UserSession에서 UserId를 뽑아낸다.
			//2.Account를 통해 이 유저의 address 및 기본 정보를 읽어와서 세팅한다.
			//purchaseReq.setAddress("address"); //정보를 세팅하여 Form에 초기값으로 나타낸다.

			return joinReq;
		}
		else return new JoinRequest(); 
	}
	
	//4.submit() ==> 같은 url의 POST로 매핑
	@PostMapping("/{productId}")
	public String register( 
			@ModelAttribute("joinReq") JoinRequest joinReq, //Commmand 객체로 사용
			BindingResult bindingResult, Model model) {
		System.out.println("command 객체: " + joinReq);
		
		Join join = new Join();
		
		// validator 생성 및 호출 (입력 값 검증), validator 패키지에 포함됨
		//new OrderValidator().validate(purchaseReq, bindingResult);
		if (bindingResult.hasErrors()) {
			return JOIN_FORM; 
		}

		join.initJoin(joinReq);
		joinService.registerJoin(join);

		//JPetStore - OrderController : mav.addObject("order", orderForm.getOrder());
		//model.addAttribute("join", p); //View에 객체 전달하고 간략한 정보 출력
		return "join/successed";
	}
	
	@GetMapping("/info/{joinId}")
	public String formBacking2(HttpServletRequest request,
									@PathVariable("joinId") int joinId, Model model) {
			JoinRequest joinReq = new JoinRequest();
			Join j = joinService.findJoinByJoinId(joinId); //DB에서 특정 join을 읽어온다.

			//2.Join에서 뽑아온 값을 joinReq에 세팅한다.
			joinReq.initJoinReq(j);

			//3.model에 세팅한다.
			model.addAttribute("joinReq", joinReq);

			return JOIN_INFO;
	}
	
	@PostMapping("/info/{joinId}")
	public String update(
			@ModelAttribute("joinReq") JoinRequest joinReq,
			BindingResult result) throws Exception {
		Join join = new Join();

		//1.form으로부터 받은 joinReq에 담겨있는 값을 Join에 세팅한다.
		join.initJoin(joinReq);
		
		//2.DAO를 통해 값을 수정한다.
		joinService.modifyJoin(join);
		joinReq.initJoinReq(join);

		return "redirect:/" + "join/info/{joinId}"; //본래의 경로로 redirection
	}
	
	//6.PurchaseList - 사용자가 참여한 공동구매 목록
	//7.PurchaseSearch - 키워드를 포함하는 공동구매 들을 검색
}
