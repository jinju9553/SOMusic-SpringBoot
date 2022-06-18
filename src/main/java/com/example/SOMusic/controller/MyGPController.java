package com.example.SOMusic.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.JoinService;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/user/my/gp")
public class MyGPController {

	private static final String MY_GP_INFO = "thyme/user/my/gp/myGPInfo";
	private static final String MY_REGISTER_LIST = "thyme/user/my/gp/MyGPList";
	private static final String MY_JOIN_LIST = "thyme/user/my/join/MyJoinList";

	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc = gpSvc;
	}

	@Autowired
	private JoinService joinService;
	public void setJoinService(JoinService joinService) {
		this.joinService = joinService;
	}
	
	@Autowired
	private AccountService accountService;
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@ModelAttribute("joinStatus")
	public Map<Integer, String> joinStatus() { // 0: join 이전 & 1: 승인됨, 입금 대기 & 2: 입금 완료, 배송 대기중 & 3:배송 시작 & 4: 거래 완료
		Map<Integer, String> status = new HashMap<Integer, String>();
		status.put(1, "입금 대기");
		status.put(2, "입금 완료");
		status.put(3, "배송 시작");
		status.put(4, "거래 완료");
		return status;
	}

	// user 정보를 포함하는 formBacking
	@ModelAttribute("accountForm")
	public AccountForm formBacking(HttpServletRequest request) {
		if (request.getMethod().equalsIgnoreCase("GET")) {

			// 1.UserSession에서 UserId를 가져온다.
			Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");

			// 2.세션에서 얻은 Id로 사용자 정보를 가져온다.
			AccountForm accountForm = new AccountForm(accountService.getAccount(userSession.getAccount().getUserId()));

			return accountForm;
		} else
			return new AccountForm(); // POST일 때 실행됨
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(@RequestParam("gpId") int gpId, Model model) throws Exception {
		System.out.println("GP 정보 : " + gpId);

		// 공구 검색
		GroupPurchase gp = gpSvc.getGP(gpId);
		gp.setDescription(gp.getDescription().replace("\n", "<br>")); // 줄바꿈 --> <br> 태그로
		model.addAttribute("gp", gp);

		return MY_GP_INFO;
	}

	@RequestMapping(value = "/register/list", method = RequestMethod.GET)
	public String registerList(HttpServletRequest request,  Model model) throws Exception {
		
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");

		List<GroupPurchase> gpList = gpSvc.getMyGPList(userSession.getAccount().getUserId());
		model.addAttribute("gpList", gpList);

		return MY_REGISTER_LIST;
	}

	@RequestMapping(value = "/join/list", method = RequestMethod.GET)
	public String joinList(HttpServletRequest request, Model model) throws Exception {
		
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");

		List<Join> joinList = joinService.findAllByUserId(userSession.getAccount().getUserId());
		model.addAttribute("joinList", joinList);

		return MY_JOIN_LIST;
	}

	@RequestMapping(value = "/join/status/update", method = RequestMethod.POST) // 한개의 join에 대한 상태 변경
	public String updateJoinStatus(@RequestParam("joinId") int joinId, @RequestParam("gpId") int gpId,
			@RequestParam("status") int status) throws Exception {

		joinService.updateStatus(joinId, status);

		return "redirect:" + "/user/my/gp/info?gpId=" + gpId;
	}

	@RequestMapping(value = "/join/status/all/update", method = RequestMethod.POST) // 모든 join의 상태 일괄 변경
	public String updateAllJoinStatus(@RequestParam("gpId") int gpId, @RequestParam("status") String status)
			throws Exception {

		System.out.println("join 상태 수정 : " + gpId + ", " + status);

		if (status.equals("none")) // ==상태변경== 클릭시 다시 info로
			return "redirect:" + "/user/my/gp/info?gpId=" + gpId;

		joinService.updateAllStatus(gpId, Integer.parseInt(status));

		return "redirect:" + "/user/my/gp/info?gpId=" + gpId;
	}
}
