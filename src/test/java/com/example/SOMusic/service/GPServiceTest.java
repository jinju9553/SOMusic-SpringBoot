package com.example.SOMusic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
	
		Mockito.doNothing().when(gpSvc).insertGP(any(GroupPurchase.class));
		
		gpSvc.insertGP(new GroupPurchase());
		
		Mockito.verify(gpSvc, Mockito.times(1)).insertGP(any(GroupPurchase.class));
		
	}
	
	@Test
	@DisplayName("공구 수정")
	void update() {
		
		Mockito.doNothing().when(gpSvc).updateGP(any(GroupPurchase.class));
		
		gpSvc.updateGP(new GroupPurchase());
		
		Mockito.verify(gpSvc, Mockito.times(1)).updateGP(any(GroupPurchase.class));
		
	}
	
	@Test
	@DisplayName("공구 삭제")
	void delete() {
		
		Mockito.doNothing().when(gpSvc).deleteGP(any(Integer.class));
		
		gpSvc.deleteGP(0);
		
		Mockito.verify(gpSvc, Mockito.times(1)).deleteGP(any(Integer.class));
		
	}
	
	@Test
	@DisplayName("공구 가져오기")
	void get() {
		
		GroupPurchase gp = getGP();
		int gpId = gp.getGpId();
		
		Mockito.when(gpSvc.getGP(gpId)).thenReturn(gp);
		
		GroupPurchase result = gpSvc.getGP(gpId);
		
		assertEquals(gp, result);
	
	}
	
	@Test
	@DisplayName("등록한 공구 불러오기 - 판매자 아이디로 공구 반환")
	void MyGPList() {
		
		List<GroupPurchase> gpList = getGPList();
		
		Mockito.when(gpRepository.findBySellerId("hi")).thenReturn(gpList);
		
		List<GroupPurchase> result = gpSvc.getMyGPList("hi");
		
		assertEquals(result.size(), 2);
		
	}
	
	@Test
	@DisplayName("메인화면 공구 리스트")
	void MainGPList() {
		
		List<GroupPurchase> gpList = getGPList();
		
		Mockito.when(gpRepository.findFirst4ByOrderByGpId()).thenReturn(gpList);
		
		List<GroupPurchase> result = gpSvc.getMainGPList();
		
		assertEquals(result.size(), 2);
		
	}
	
	@Test
	@DisplayName("모든 공구 리스트")
	void AllGPList() {
		
		List<GroupPurchase> gpList = getGPList();
		
		Mockito.when(gpRepository.findAll()).thenReturn(gpList);
		
		List<GroupPurchase> result = gpSvc.getAllGPList();
		
		assertEquals(result.size(), 2);
		
	}
	
	@Test
	@DisplayName("검색 공구 리스트")
	void SearchGPList() {
		
		List<GroupPurchase> gpList = getGPList();
		String keyword = "aa";
		
		Mockito.when(gpRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword, keyword)).thenReturn(gpList);
		
		List<GroupPurchase> result = gpSvc.getSearchGPList(keyword);
		
		assertEquals(result.size(), 2);
		
	}
	
	@Test
	@DisplayName("위시 공구 생성")
	void insertWishGP() {
		
		WishGroupPurchase wish = getWishGP();
		String userId = wish.getUserId();
		int gpId = wish.getGpId();
	
		Mockito.doNothing().when(gpSvc).insertWishGP(userId, gpId);
		
		gpSvc.insertWishGP(userId, gpId);
		
		Mockito.verify(gpSvc, Mockito.times(1)).insertWishGP(userId, gpId);
		
	}
	
	@Test
	@DisplayName("위시 공구 가져오기")
	void wishGP() {
		
		WishGroupPurchase wish = getWishGP();
		String userId = wish.getUserId();
		int gpId = wish.getGpId();
		
		Mockito.when(wishRepository.findByUserIdAndGpId(userId, gpId)).thenReturn(wish);
		
		WishGroupPurchase result = gpSvc.getWishGP(userId, gpId);
		
		assertEquals(result.getGp(), wish.getGp());
	}
	
	@Test
	@DisplayName("위시 공구 삭제")
	void deleteWishGP() {
		
		WishGroupPurchase wish = getWishGP();
		String userId = wish.getUserId();
		int gpId = wish.getGpId();
	
		Mockito.doNothing().when(gpSvc).deleteWishGP(userId, gpId);
		
		gpSvc.deleteWishGP(userId, gpId);
		
		Mockito.verify(gpSvc, Mockito.times(1)).deleteWishGP(userId, gpId);
		
	}
	
	@Test
	@DisplayName("위시 공구 리스트")
	void WishGPList() {
		
		List<WishGroupPurchase> wishList = getWishGPList();
		
		Mockito.when(wishRepository.findByUserId("hi")).thenReturn(wishList);
		
		List<WishGroupPurchase> result = gpSvc.getWishGPList("hi");
		
		assertEquals(result.size(), 1);
		
	}
	
	public GroupPurchase getGP() {
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link",
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		return gp;
	}
	
	public List<GroupPurchase> getGPList() {
		GroupPurchase gp1 = getGP();
		
		GroupPurchase gp2 = new GroupPurchase(2, "hi", "aabb", "link",
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		List<GroupPurchase> gpList = new ArrayList<>();
		gpList.add(gp1);
		gpList.add(gp2);
		
		return gpList;
	}
	
	public WishGroupPurchase getWishGP() {
		GroupPurchase gp = getGP();
		String userId = "rover";
		
		WishGroupPurchase wish = new WishGroupPurchase(userId, gp.getGpId());
		wish.setGp(gp);
		
		return wish;
	}
	
	public List<WishGroupPurchase> getWishGPList() {
		WishGroupPurchase wish = getWishGP();
		
		List<WishGroupPurchase> wishList = new ArrayList<>();
		wishList.add(wish);
		
		return wishList;
	}
	
	
	
}
