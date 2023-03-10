package com.example.SOMusic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.SOMusic.dao.GPDao;
import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.WishGroupPurchase;
import com.example.SOMusic.repository.GPRepository;
import com.example.SOMusic.repository.WishGPRepository;

@ExtendWith(SpringExtension.class)
class GPServiceTest {
	
	@Mock
	GPDao dao;
	
	@Mock
	GPService gpSvc;
	
	@Mock
	GPRepository gpRepository;
	
	@Mock
	WishGPRepository wishRepository;
	
	@BeforeEach
	void setUp() {
		
	}
	
	@Test
	@DisplayName("공구 생성")
	void create() {
		
		GroupPurchase gp = new GroupPurchase();
		gp.setTitle("aaa");
		
		gpSvc.insertGP(gp);
		
		assertEquals("aaa", gp.getTitle());
		
		Mockito.verify(gpSvc, Mockito.times(1)).insertGP(gp);
		
	}
	
	@Test
	@DisplayName("공구 수정")
	void update() {
		
		GroupPurchase gp = new GroupPurchase();
		gp.setTitle("aaa");
		
		gpSvc.updateGP(gp);
		
		assertEquals("aaa", gp.getTitle());
		
		Mockito.verify(gpSvc, Mockito.times(1)).updateGP(gp);
		
	}
	
	@Test
	@DisplayName("공구 삭제")
	void delete() {
		
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link", 
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		gpSvc.insertGP(gp);
		gpSvc.deleteGP(gp.getGpId());
		
		List<GroupPurchase> result = gpSvc.getAllGPList();
		assertEquals(result.size(), 0);
	}
	
	@Test
	@DisplayName("공구 가져오기")
	void get() {
		
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link", 
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		gpSvc.insertGP(gp);
		
		GroupPurchase result = gpSvc.getGP(1);
		
		assertEquals(gp, result);
	
	}
	
	@Test
	@DisplayName("등록한 공구 불러오기 - 판매자 아이디로 공구 반환")
	void MyGPList() {
		
		GroupPurchase gp1 = new GroupPurchase(1, "hi", "aaa", "link", 
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		GroupPurchase gp2 = new GroupPurchase(2, "hi", "bbb", "link", 
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		GroupPurchase gp3 = new GroupPurchase(3, "bye", "ccc", "link", 
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		List<GroupPurchase> gpList = new ArrayList<>();
		gpList.add(gp1);
		gpList.add(gp2);
		
		Mockito.when(gpRepository.findBySellerId("hi")).thenReturn(gpList);
		
		
		List<GroupPurchase> result = gpSvc.getMyGPList("hi");
		assertEquals(result.size(), 2);
		
	}
	
	@Test
	@DisplayName("공구 리스트")
	void GPList() {
		
		GroupPurchase gp1 = new GroupPurchase(1, "hi", "aaa", "link", 
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		GroupPurchase gp2 = new GroupPurchase(2, "hi", "bbb", "link", 
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		
		List<GroupPurchase> gpList = new ArrayList<>();
		gpList.add(gp1);
		gpList.add(gp2);
		
		Mockito.when(gpRepository.findAll()).thenReturn(gpList);
		
		List<GroupPurchase> result = gpSvc.getAllGPList();
		
		assertEquals(result.size(), 2);
		
	}
	
	@Test
	@DisplayName("위시 공구 리스트")
	void WishGPList() {
		
		GroupPurchase gp1 = new GroupPurchase(1, "hi", "aaa", "link", 
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		GroupPurchase gp2 = new GroupPurchase(2, "hi", "bbb", "link", 
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		GroupPurchase gpResult1 = gpRepository.save(gp1);
		GroupPurchase gpResult2 = gpRepository.save(gp2);
		
		WishGroupPurchase wish1 = new WishGroupPurchase("hi", gpResult1.getGpId());
		WishGroupPurchase wish2 = new WishGroupPurchase("hi", gpResult2.getGpId());
		
		wish1.setGp(gp1);
		wish2.setGp(gp2);
		
		List<WishGroupPurchase> wishList = new ArrayList<>();
		wishList.add(wish1);
		wishList.add(wish2);
		
		Mockito.when(wishRepository.findByUserId("hi")).thenReturn(wishList);
		
		List<WishGroupPurchase> result = gpSvc.getWishGPList("hi");
		
		
		
		assertEquals(result.size(), 2);
		
	}

	
	
	


//	
//	@Test
//	@DisplayName("공구 검색")
//	void search() {
//		
//		
//		String keyword = "aa";
//		List<GroupPurchase> result = gpSvc.getSearchGPList(keyword);
//		
//		assertEquals(result.size(), 2);
//	}
//	
	
	
	
	
	
}
