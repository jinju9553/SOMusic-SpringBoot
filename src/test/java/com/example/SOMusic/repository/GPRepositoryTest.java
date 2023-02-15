package com.example.SOMusic.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.SOMusic.domain.GroupPurchase;

@DataJpaTest
class GPRepositoryTest {
	
	@Autowired
	GPRepository gpRepository;
	
	@Test
	@DisplayName("공구 생성 확인")
	void create() {
		
		GroupPurchase gp = new GroupPurchase();
		gp.setTitle("aaa");
		
		GroupPurchase result = gpRepository.save(gp);
	
		assertEquals(result.getTitle(), gp.getTitle());
		
	}
	
	@Test
	@DisplayName("공구 리스트")
	void GPList() {
		
		GroupPurchase gp1 = new GroupPurchase();
		gp1.setTitle("aaa");
		
		GroupPurchase gp2 = new GroupPurchase();
		gp2.setTitle("bbb");
		
		gpRepository.save(gp1);
		gpRepository.save(gp2);
		
		List<GroupPurchase> result = gpRepository.findAll();
		
		assertEquals(result.size(), 2);
		
	}
	
	@Test
	@DisplayName("공구 아이디로 검색")
	void GPId() {
		
		GroupPurchase gp1 = new GroupPurchase();
		gp1.setTitle("aaa");
		
		GroupPurchase gp2 = new GroupPurchase();
		gp2.setTitle("bbb");
		
		GroupPurchase gpResult = gpRepository.save(gp1);
		gpRepository.save(gp2);
		
		GroupPurchase result = gpRepository.findByGpId(gpResult.getGpId());
		
		assertEquals(result.getTitle(), gp1.getTitle());
		
	}
	
	@Test
	@DisplayName("판매자 아이디로 공구 검색")
	void SellerId() {
		
		GroupPurchase gp1 = new GroupPurchase();
		gp1.setTitle("aaa");
		gp1.setSellerId("hi");
		
		GroupPurchase gp2 = new GroupPurchase();
		gp2.setTitle("bbb");
		gp2.setSellerId("hi");
		
		GroupPurchase gp3 = new GroupPurchase();
		gp2.setTitle("ccc");
		gp3.setSellerId("bye");
		
		gpRepository.save(gp1);
		gpRepository.save(gp2);
		gpRepository.save(gp3);
		
		List<GroupPurchase> result = gpRepository.findBySellerId("hi");
		
		assertEquals(result.size(), 2);

	}
	
	@Test
	@DisplayName("공구 검색")
	void search() {
		
		GroupPurchase gp1 = new GroupPurchase();
		gp1.setTitle("aaa");
		gp1.setSellerId("hi");
		
		GroupPurchase gp2 = new GroupPurchase();
		gp2.setTitle("bbb");
		gp2.setSellerId("hi");
		
		GroupPurchase gp3 = new GroupPurchase();
		gp2.setTitle("aabb");
		gp3.setSellerId("bye");
		
		gpRepository.save(gp1);
		gpRepository.save(gp2);
		gpRepository.save(gp3);
		
		String keyword = "aa";
		List<GroupPurchase> result = gpRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword, keyword);
		
		assertEquals(result.size(), 2);
		
	}
	
	@Test
	@DisplayName("공구 삭제")
	void GPIdDelete() {
		
		GroupPurchase gp1 = new GroupPurchase();
		gp1.setTitle("aaa");
		gp1.setSellerId("hi");
		
		GroupPurchase gp2 = new GroupPurchase();
		gp2.setTitle("bbb");
		gp2.setSellerId("hi");
		
		GroupPurchase gpResult = gpRepository.save(gp1);
		gpRepository.save(gp2);
		
		gpRepository.deleteByGpId(gpResult.getGpId());
		
		List<GroupPurchase> result = gpRepository.findAll();
		
		assertEquals(result.size(), 1);
		
	}
	
}
