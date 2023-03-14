package com.example.SOMusic.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.repository.GPRepository;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.ImgValidator;

@WebMvcTest(GPController.class)
class GPControllerTest {

	@Autowired
	MockMvc mvc;
	
	@MockBean
	GPService gpSvc;
	
	@MockBean
	private ImgValidator validator;
	
	@MockBean
	GPRepository gpRepository;
	
	@Mock
	GPRequest gpReq;
	
	Login userSession;
	
	MockHttpSession session;
	
	MockHttpServletRequest request;
    
    private static final String GP_REGISTER_FORM = "thyme/gp/register/GPRegisterForm";
	private static final String GP_REGISTER_SEUCCESS = "/gp/register/success";
	private static final String GP_REGISTER_SEUCCESS_View = "thyme/gp/register/GPRegisterSuccess";
	
	private static final String GP_UPDATE_FORM = "thyme/gp/update/GPUpdateForm";
	private static final String GP_UPDATE_SEUCCESS = "/user/my/gp/info";
	
	private static final String GP_DELETE = "/user/my/gp/list";
	
    @BeforeEach()
    public void setUp() throws Exception {
    	
    	Account account = new Account();
		account.setUserName("hi-he");
		account.setUserId("hi");
		
		userSession = new Login(account);
		
		session = new MockHttpSession();
		session.setAttribute("userSession", userSession);
    	
        request = new MockHttpServletRequest();
    }
	
	@Test
	@DisplayName("공구 등록 폼으로 이동")
	public void register_get_login() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/gp/register").session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(GP_REGISTER_FORM));

	}
	
   @Test
	@DisplayName("공구 등록 (이미지X) - 오류 발생")
	public void register_post_noImage() throws Exception {

		GPRequest gpReq = new GPRequest();
		
		mvc.perform(MockMvcRequestBuilders.post("/gp/register")
										.flashAttr("gpReq", gpReq)
										.param("imgCheck", "off")
										.session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(GP_REGISTER_FORM));
		
	}
	
	@Test
	@DisplayName("공구 등록")
	public void register_post() throws Exception {
		
		GPRequest gpReq = getGPReq();
		
		Mockito.doNothing().when(gpSvc).insertGP(any(GroupPurchase.class));
		
		mvc.perform(MockMvcRequestBuilders.post("/gp/register")
										.flashAttr("gpReq", gpReq)
										.param("imgCheck", "on")
										.session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl(GP_REGISTER_SEUCCESS));
		
		Mockito.verify(gpSvc).insertGP(any(GroupPurchase.class));
	}
	
	@Test
	@DisplayName("공구 등록 성공")
	public void registerSuccess() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.get("/gp/register/success").session(session))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name(GP_REGISTER_SEUCCESS_View));
		
	}
	
    @Test
	@DisplayName("공구 수정 폼으로")
	public void update_get() throws Exception {
		
		GroupPurchase gp = getGP();
		int gpId = gp.getGpId();
		String imgPath = gp.getImage();
		
		request.setParameter("gpId", String.valueOf(gpId));
		
		Mockito.when(gpSvc.getGP(gpId)).thenReturn(gp);
		
		mvc.perform(MockMvcRequestBuilders.get("/gp/update")
										.param("gpId", String.valueOf(gpId))
										.param("imgPath", imgPath)
										.session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name(GP_UPDATE_FORM));
		
	}
    
	@Test
	@DisplayName("공구 수정")
	public void update_post() throws Exception {
		
		GPRequest gpReq = getGPReq();
		int gpId = gpReq.getGpId();
		
		request.setParameter("gpId", String.valueOf(gpId));
		
		Mockito.doNothing().when(gpSvc).updateGP(any(GroupPurchase.class));
		
		mvc.perform(MockMvcRequestBuilders.post("/gp/update")
										.flashAttr("gpReq", gpReq)
										.param("imgPath", "imgPath")
										.param("isModify", "true"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl(GP_UPDATE_SEUCCESS + "?gpId=" + gpId));
		
	}
	
	@Test
	@DisplayName("공구 삭제")
	public void delete() throws Exception {
		
		GroupPurchase gp = getGP();
		int gpId = gp.getGpId();
		
		request.setParameter("gpId", String.valueOf(gpId));
		Mockito.when(gpSvc.getGP(gpId)).thenReturn(gp);
		
		Mockito.doNothing().when(gpSvc).deleteGP(gpId);
		
		mvc.perform(MockMvcRequestBuilders.get("/gp/delete")
										.param("gpId", String.valueOf(gpId))
										.session(session))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl(GP_DELETE));
		
		Mockito.verify(gpSvc, times(1)).deleteGP(gpId);

	}
	
	public GroupPurchase getGP() {
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link",
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		return gp;
	}
	
	public GPRequest getGPReq() {
		byte[] fileContent = "Image Link".getBytes();
        String filename = "test.png";

        MockMultipartFile image = new MockMultipartFile("file", filename, MediaType.TEXT_PLAIN_VALUE, fileContent);

		GPRequest gpReq = new GPRequest(1, "hi", "aaa", image,
									LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
									"music", "3333", "bank", 1000,
									"It is\n good!");
		
		return gpReq;
	}
	
}
