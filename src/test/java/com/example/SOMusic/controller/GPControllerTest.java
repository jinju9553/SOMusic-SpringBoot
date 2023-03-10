package com.example.SOMusic.controller;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.example.SOMusic.domain.GroupPurchase;
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
	
	@Test
	@DisplayName("공구 등록 폼으로")
	public void register_get() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/gp/register"))
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	@DisplayName("공구 등록")
	public void register_post() throws Exception {
		
		GroupPurchase gp = getGP();
		
		Mockito.doNothing().when(gpSvc).insertGP(gp);
		
		mvc.perform(MockMvcRequestBuilders.post("/gp/register"))
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	@DisplayName("공구 수정")  // <<<<<<<<<<<<<<<<< 오류 발생 - 이미지 파일이 없어서 오류
	public void update() throws Exception {
		
		GroupPurchase gp = getGP();
		String imgPath = gp.getImage();
		
		gpReq = new GPRequest();
		gpReq.initGpReq(gp);
		
		Mockito.doNothing().when(gpSvc).updateGP(gp);
		
		mvc.perform(MockMvcRequestBuilders.post("/gp/update")
										.requestAttr("gpReq", gpReq)
										.param("imgPath", imgPath)
										.param("isModify", "true"))
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	@DisplayName("공구 삭제")
	public void delete() throws Exception {
		
		GroupPurchase gp = getGP();
		String gpId = String.valueOf(gp.getGpId());
		
		Mockito.doNothing().when(gpSvc).deleteGP(gp.getGpId());
		
		mvc.perform(MockMvcRequestBuilders.get("/gp/delete")
										.param("gpId", gpId))
//			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
		
		Mockito.verify(gpSvc, times(1)).deleteGP(gp.getGpId());

	}
	
	public GroupPurchase getGP() {
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link",
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		return gp;
	}
	
}
