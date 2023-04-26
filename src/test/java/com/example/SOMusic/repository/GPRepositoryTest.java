package com.example.SOMusic.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.SOMusic.domain.GroupPurchase;

@DataJpaTest
class GPRepositoryTest {
	
	@Autowired
	GPRepository gpRepository;
	
	GroupPurchase gp1;
	
	@BeforeEach
	void setup() {
		
		gp1 = new GroupPurchase();
		gp1.setTitle("aaa");
		gp1.setSellerId("hi");
		
		GroupPurchase gp2 = new GroupPurchase();
		gp2.setTitle("bbb");
		gp2.setSellerId("hi");
		
		GroupPurchase gp3 = new GroupPurchase();
		gp3.setTitle("aabb");
		gp3.setSellerId("bye");
		
		gp1 = gpRepository.save(gp1);
		
		gpRepository.save(gp2);
		gpRepository.save(gp3);
	
	}
	
	@Test
	@DisplayName("공구 생성 확인")
	void create() {
		
		GroupPurchase gp = new GroupPurchase();
		gp.setTitle("ccc");
		
		GroupPurchase result = gpRepository.save(gp);
	
		assertEquals(result.getTitle(), gp.getTitle());
		
	}
	
	@Test
	@DisplayName("공구 리스트")
	void GPList() {

		List<GroupPurchase> result = gpRepository.findAll();
		
		assertEquals(result.size(), 3);
		
	}
	
	@Test
	@DisplayName("공구 아이디로 검색")
	void GPId() {
		
		GroupPurchase result = gpRepository.findByGpId(gp1.getGpId());
		
		assertEquals(result.getTitle(), gp1.getTitle());
		
	}
	
	@Test
	@DisplayName("판매자 아이디로 공구 검색")
	void SellerId() {
		
		List<GroupPurchase> result = gpRepository.findBySellerId("hi");
		
		assertEquals(result.size(), 2);

	}
	
	@Test
	@DisplayName("공구 삭제")
	void GPIdDelete() {
		
		gpRepository.deleteByGpId(gp1.getGpId());
		
		List<GroupPurchase> result = gpRepository.findAll();
		
		assertEquals(result.size(), 2);
		
	}
	
	@Test
	@DisplayName("메인화면의 공구 리스트")
	void getMainGPList() {
		
		List<GroupPurchase> result = gpRepository.findFirst4ByOrderByGpId();
		
		assertEquals(result.size(), 3);
	}
	
	@Test
	@DisplayName("모든 공구 리스트")
	void getAllGPList() {
		
		List<GroupPurchase> result = gpRepository.findAll();
		
		assertEquals(result.size(), 3);
	}
	
	@Test
	@DisplayName("공구 검색")
	void search() {
		
		String keyword = "aa";
		List<GroupPurchase> result = gpRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword, keyword);
		
		assertEquals(result.size(), 2);
		
	}
	
}
