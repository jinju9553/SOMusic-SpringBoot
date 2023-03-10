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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
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
	void info() throws Exception {
		
		GroupPurchase gp = getGP();
		
		Mockito.when(gpSvc.getGP(1)).thenReturn(gp);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/gp/info").param("gpId", "1").session(session).contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.model().attribute("gp", gp));//??????????????
//			.andExpect(MockMvcResultMatchers.jsonPath("$.GroupPurchase.description").value("Is is<br> good!"));
	}
	
	@Test
	@DisplayName("등록한 공구 리스트 불러오기")
	void gpList() throws Exception {
				
		List<GroupPurchase> gpList = getGPList();
		
		Mockito.when(gpSvc.getMyGPList("hi")).thenReturn(gpList);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/gp/list").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("gpList", gpList));

	}
	
	@Test
	@DisplayName("등록한 공구 리스트 불러오기")
	void joinList() throws Exception {
		
		GroupPurchase gp = getGP();
		
		Join join = new Join();
		join.setJoinId(1);
		join.setGroupPurchase(gp);
		
		List<Join> joinList = new ArrayList<>();
		joinList.add(join);
		
		Mockito.when(joinSvc.findAllByUserId("hi")).thenReturn(joinList);
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/join/list").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("joinList", joinList));

	}
	
	@Test
	@DisplayName("등록한 공구 상태 변경")
	void updateJoinStatus() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.get("/user/my/join/status/update")
										.param("gpId", "1").param("joinId", "1")
										.param("status", "0").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
			
		Mockito.verify(joinSvc, Mockito.times(1)).updateStatus(1, 0);
		
	}
	
	@Test
	@DisplayName("등록한 공구 상태 일괄 변경")
	void updateAllJoinStatus() throws Exception {
		
		Mockito.doNothing().when(joinSvc).updateAllStatus(1, 2);
		
		mvc.perform(MockMvcRequestBuilders.post("/user/my/join/status/all/update")
										.param("gpId", "1").param("status", "2")
										.session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/user/my/gp/info?gpId=1"));

			
		Mockito.verify(joinSvc, Mockito.times(1)).updateAllStatus(1, 2);
		
	}
	
	public GroupPurchase getGP() {
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link",
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is\n good");
		
		return gp;
	}
	
	public List<GroupPurchase> getGPList() {
		GroupPurchase gp1 = getGP();
		
		GroupPurchase gp2 = getGP();
		gp2.setGpId(2);
		gp2.setTitle("bbb");
		
		List<GroupPurchase> gpList = new ArrayList<>();
		gpList.add(gp1);
		gpList.add(gp2);
		
		return gpList;
	}
	
}
