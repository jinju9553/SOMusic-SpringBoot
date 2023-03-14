package com.example.SOMusic.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.JoinService;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(MyGPController.class)
class MyGPControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	GPService gpSvc;
	
	@MockBean
	JoinService joinSvc;
	
	@MockBean
	AccountService accountSvc;
	
	Login userSession;
	
	MockHttpSession session;
	
	private static final String MY_GP_INFO = "thyme/user/my/gp/myGPInfo";
	private static final String MY_REGISTER_LIST = "thyme/user/my/gp/MyGPList";
	private static final String MY_JOIN_LIST = "thyme/user/my/join/MyJoinList";
	
	@BeforeEach()
    public void setup() {
		Account account = new Account();
		account.setUserName("hi-he");
		account.setUserId("hi");
		
		userSession = new Login(account);
		
		session = new MockHttpSession();
		session.setAttribute("userSession", userSession);
		
	}
	
	@Test
	@DisplayName("공구 정보")
	public void info() throws Exception {
		
		GroupPurchase gp = getGP();
		String desc = "Is is<br> good";
		
		Mockito.when(gpSvc.getGP(1)).thenReturn(gp);
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/user/my/gp/info").param("gpId", "1").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(MY_GP_INFO))
			.andExpect(MockMvcResultMatchers.model().attribute("gp", gp))
			.andReturn();
		
		GroupPurchase result = (GroupPurchase) mvcResult.getModelAndView().getModel().get("gp");
		assertThat(result.getDescription()).isEqualTo(desc);

	}
	
	@Test
	@DisplayName("등록한 공구 리스트 불러오기")
	public void gpList() throws Exception {
				
		List<GroupPurchase> gpList = getGPList();
		String userId = userSession.getAccount().getUserId();
		
		Mockito.when(gpSvc.getMyGPList(userId)).thenReturn(gpList);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/gp/list").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(MY_REGISTER_LIST))
			.andExpect(MockMvcResultMatchers.model().attribute("gpList", gpList));

	}
	
	@Test
	@DisplayName("참여한 공구 리스트 불러오기")
	public void joinList() throws Exception {
		
		List<Join> joinList = getJoinList();
		String userId = userSession.getAccount().getUserId();
		
		Mockito.when(joinSvc.findAllByUserId(userId)).thenReturn(joinList);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/join/list").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(MY_JOIN_LIST))
			.andExpect(MockMvcResultMatchers.model().attribute("joinList", joinList));

	}
	
	@Test
	@DisplayName("등록한 공구 상태 변경")
	public void updateJoinStatus() throws Exception {
		
		int gpId = getGP().getGpId();
		int joinId = getJoin().getJoinId();
		int status = 2;
		
		Mockito.doNothing().when(joinSvc).updateStatus(joinId, status);
		
		mvc.perform(MockMvcRequestBuilders.post("/user/my/join/status/update")
										.param("gpId", String.valueOf(gpId))
										.param("joinId", String.valueOf(joinId))
										.param("status", String.valueOf(status))
										.session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/user/my/gp/info?gpId=" + gpId));
			
		Mockito.verify(joinSvc, Mockito.times(1)).updateStatus(gpId, status);
		
	}
	
	@Test
	@DisplayName("등록한 공구 상태 일괄 변경")
	public void updateAllJoinStatus() throws Exception {
		
		int gpId = getGP().getGpId();
		int status = 2;
		
		Mockito.doNothing().when(joinSvc).updateAllStatus(1, 2);
		
		mvc.perform(MockMvcRequestBuilders.post("/user/my/join/status/all/update")
										.param("gpId", String.valueOf(gpId))
										.param("status", String.valueOf(status))
										.session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/user/my/gp/info?gpId=1"));

		Mockito.verify(joinSvc, Mockito.times(1)).updateAllStatus(gpId, status);
		
	}
	
	public GroupPurchase getGP() {
		
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link",
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is\n good");
		
		return gp;
	}
	
	public List<GroupPurchase> getGPList() {
		
		GroupPurchase gp = getGP();
		
		List<GroupPurchase> gpList = new ArrayList<>();
		gpList.add(gp);
		
		return gpList;
	}
	
	public Join getJoin() {
		
		GroupPurchase gp = getGP();
		String userId = userSession.getAccount().getUserId();
		
		Join join = new Join();
		join.setJoinId(1);
		join.setConsumerId(userId);
		join.setGroupPurchase(gp);
		
		return join;
	}
	
	public List<Join> getJoinList() {
		
		Join join = getJoin();
		
		List<Join> joinList = new ArrayList<>();
		joinList.add(join);
		
		return joinList;
	}
}
