package com.example.SOMusic.controller;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/user/my")
public class MyGPController {

	private static final String MY_GP_INFO = "thyme/user/my/gp/myGPInfo";
	private static final String MY_REGISTER_LIST = "thyme/user/my/gp/MyGPList";
	private static final String MY_JOIN_LIST = "thyme/user/my/join/MyJoinList";
	private static final String MY_GP_INFO_URI = "/user/my/gp/info?gpId=";
	
	Login userSession;
	String userId;

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
	public Map<Integer, String> joinStatus() {
		Map<Integer, String> status = new HashMap<Integer, String>();
		status.put(1, "입금 대기");
		status.put(2, "입금 완료");
		status.put(3, "배송 시작");
		status.put(4, "거래 완료");
		return status;
	}

	@ModelAttribute("accountForm")
	public AccountForm formBacking(HttpServletRequest request) {
		
		userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		userId = userSession.getAccount().getUserId();
		
		if (request.getMethod().equalsIgnoreCase("GET")) {
			AccountForm accountForm = new AccountForm(accountService.getAccount(userId));
			return accountForm;
		} else
			return new AccountForm();
	}

  @RequestMapping(value = "/gp/info", method = RequestMethod.GET)
  public String info(@RequestParam("gpId") int gpId, Model model) throws Exception {

      GroupPurchase gp = getReplacedDescGP(gpId);
      model.addAttribute("gp", gp);

      return MY_GP_INFO;
  }

  @RequestMapping(value = "/gp/list", method = RequestMethod.GET)
  public String registerList(HttpServletRequest request, Model model) throws Exception {

      List<GroupPurchase> gpList = gpSvc.getMyGPList(userId);
      model.addAttribute("gpList", gpList);

      return MY_REGISTER_LIST;
  }

  @RequestMapping(value = "/join/list", method = RequestMethod.GET)
  public String joinList(HttpServletRequest request, Model model) throws Exception {

      List<Join> joinList = joinService.findAllByUserId(userId);
      model.addAttribute("joinList", joinList);

      return MY_JOIN_LIST;
  }

	@RequestMapping(value = "/join/status/update", method = RequestMethod.POST)
	public String updateJoinStatus(@RequestParam("joinId") int joinId, @RequestParam("gpId") int gpId,
								@RequestParam("status") int status) throws Exception {
        Join join = joinService.findJoinByJoinId(joinId);
        joinService.updateStatus(join, status);

		return "redirect:" + MY_GP_INFO_URI + gpId;
	}

	@RequestMapping(value = "/join/status/all/update", method = RequestMethod.POST)
	public String updateAllJoinStatus(@RequestParam("gpId") int gpId, @RequestParam("status") String status) throws Exception {

		if (isUpdateJoinStatus(status))
			joinService.updateAllStatus(gpId, Integer.parseInt(status));
		
		return "redirect:" + MY_GP_INFO_URI + gpId;
	}
	
	private GroupPurchase getReplacedDescGP(int gpId) {
		GroupPurchase gp = gpSvc.getGP(gpId);
		
		String beforeDesc = gp.getDescription();
		String afterDesc = beforeDesc.replace("\n", "<br>");
		
		gp.setDescription(afterDesc);
		
		return gp;
	}
	
	private boolean isUpdateJoinStatus(String status) {
		return !status.equals("none");
	}

}
