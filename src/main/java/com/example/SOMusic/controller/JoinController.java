package com.example.SOMusic.controller;

import java.util.Date;

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
	
	@ModelAttribute("shippingCost")
	public Object[] referenceCost() { 
		return new Object[] { 1800, 3000, 6000 };
	}
	
	//1.Validator 작성 필요
	//@Autowired
	//private OrderValidator orderValidator;

	//2.showForm()
	@GetMapping("/info") //테스트용 uri
	public String showForm2() {
		return JOIN_INFO;
	}
	
	@GetMapping("/{gpId}")
	@ModelAttribute("joinReq") // request handler methods 보다 먼저 호출됨
	public ModelAndView formBacking(HttpServletRequest request,
			@PathVariable("gpId") int gpId, Model model) { 
		JoinRequest joinReq = new JoinRequest();

		GroupPurchase gp = gpService.getGP(gpId);

		// 만약 배송지 '주문자와 동일' 옵션을 선택했을 경우 ==> ajax 콜 사용
		// 1.UserSession에서 UserId를 뽑아낸다.
		// 2.Account를 통해 이 유저의 address 및 기본 정보를 읽어와서 세팅한다.
		// purchaseReq.setAddress("address"); //정보를 세팅하여 Form에 초기값으로 나타낸다.

		joinReq.setGroupPurchase(gp); // 정보를 세팅하여 Form에 초기값으로 나타낸다.
		if(joinReq.getQuantity() == 0) //최초로 폼을 불러왔다면
			joinReq.setQuantity(1); //개수는 1개부터 시작
		model.addAttribute("joinReq", joinReq);

		return new ModelAndView(JOIN_FORM);
	}
	
	@PostMapping("/{gpId}")
	public String register( 
			@ModelAttribute("joinReq") JoinRequest joinReq, //Commmand 객체로 사용
			@PathVariable("gpId") int gpId,
			BindingResult bindingResult, Model model) {
		Join join = new Join();
		
		// validator 생성 및 호출 (입력 값 검증), validator 패키지에 포함됨
		//new OrderValidator().validate(purchaseReq, bindingResult);
		if (bindingResult.hasErrors()) {
			return JOIN_FORM; 
		}

		join.initJoin(joinReq);
		join.setGroupPurchase(gpService.getGP(gpId));
		join.setShippingCost(joinService.initShippingCost(join));
		join.setTotalAmount(joinService.calculateTotal(join.getGroupPurchase(), join));
		join.setStatus(1); //참여하자마자는 1
		join.setRegDate(new Date()); //DB에서 SYSDATE가 자동으로 찍히지 않음
		
		joinService.registerJoin(join);

		//JPetStore - OrderController : mav.addObject("order", orderForm.getOrder());
		//model.addAttribute("join", p); //View에 객체 전달하고 간략한 정보 출력
		return "redirect:/" + "join/{gpId}";
	}

	@GetMapping("/info/{joinId}")
	public String formBackingInfo(HttpServletRequest request,
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
	
	//6.JoinList - 사용자가 참여한 공동구매 목록
	//7.JoinSearch - 키워드를 포함하는 공동구매 들을 검색
}
