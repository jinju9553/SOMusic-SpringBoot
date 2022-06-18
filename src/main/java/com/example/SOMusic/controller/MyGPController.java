package com.example.SOMusic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.JoinService;

@Controller
@RequestMapping("/user/my/gp")
public class MyGPController {
	
	private static final String MY_GP_INFO = "thyme/user/my/gp/myGPInfo";
	private static final String MY_REGISTER_LIST = "thyme/user/my/gp/MyGPList";
	private static final String MY_JOIN_LIST = "thyme/user/my/join/MyJoinList";

	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}
	
	@Autowired
	private JoinService joinService;
	public void setJoinService(JoinService joinService) {
		this.joinService = joinService;
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
	
	@RequestMapping(value="/info", method = RequestMethod.GET)
	public String info(@RequestParam("gpId") int gpId, Model model) throws Exception {
		System.out.println("GP 정보 : " + gpId);

		// 공구 검색
		GroupPurchase gp = gpSvc.getGP(gpId);
		gp.setDescription(gp.getDescription().replace("\n", "<br>"));	// 줄바꿈 -->  <br> 태그로
		model.addAttribute("gp", gp);
		
		return MY_GP_INFO;
	}
	
	@RequestMapping(value="/register/list", method = RequestMethod.GET)
	public String registerList(@RequestParam("sellerId") String sellerId, Model model) throws Exception {
		
		List<GroupPurchase> gpList = gpSvc.getMyGPList(sellerId);
		model.addAttribute("gpList", gpList);
		
		System.out.println("등록한 공구 리스트 : " + gpList.toString());
		
		return MY_REGISTER_LIST;
	}
	
	@RequestMapping(value="/join/list", method = RequestMethod.GET)
	public String joinList(@RequestParam("userId") String userId, Model model) throws Exception {
		
		List<Join> joinList = joinService.findAllByUserId(userId);
		model.addAttribute("joinList", joinList);
		
		System.out.println("내가 참여한 공구 리스트 : " + joinList.toString());

		return MY_JOIN_LIST;
	}
	
	@RequestMapping(value="/join/status/update", method = RequestMethod.POST)
	public String updateJoinStatus(@RequestParam("joinId") int joinId, @RequestParam("gpId") int gpId, @RequestParam("status") int status) throws Exception {
		
		System.out.println("join 상태 수정 : join > " + joinId + ", gp > " + gpId + ", 상태 > " + status);
		
		
		joinService.updateStatus(joinId, status);
		
		
		
		return "redirect:" + "/user/my/gp/info?gpId=" + gpId;
	}
	
	@RequestMapping(value="/join/status/all/update", method = RequestMethod.POST)
	public String updateAllJoinStatus(@RequestParam("gpId") int gpId, @RequestParam("status") String status) throws Exception {
		
		System.out.println("join 상태 수정 : " + gpId + ", " + status);
		
		if(status.equals("none")) {		// ==상태변경== 클릭시 다시 info로
//			System.out.println("XXX");
			return "redirect:" + "/user/my/gp/info?gpId=" + gpId;
		}
		
//		joinService.updateAllStatus(gpId, Integer.parseInt(status));
		
		return "redirect:" + "/user/my/gp/info?gpId=" + gpId;
	}
}
