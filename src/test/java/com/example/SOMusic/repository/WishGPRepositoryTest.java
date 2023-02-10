package com.example.SOMusic.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.WishGroupPurchase;

@DataJpaTest
class WishGPRepositoryTest {
	
	@Autowired
	GPRepository gpRepository;
	
	@Autowired
	WishGPRepository wishRepository;

	@Test
	@DisplayName("위시 공구 생성 확인")
	void create() {
		
		GroupPurchase gp = new GroupPurchase();
		gp.setSellerId("hi");
		gp.setTitle("aaa");
		
		GroupPurchase result = gpRepository.save(gp);
		
		WishGroupPurchase wish = new WishGroupPurchase("hi", result.getGpId());
		wish.setGp(gp);
		
		WishGroupPurchase result2 = wishRepository.save(wish);
		
		assertEquals(result2.getGp().getTitle(), "aaa");
		
	}
	
	@Test
	@DisplayName("위시 공구 가져오기")
	void wishGP() {
		
		GroupPurchase gp1 = new GroupPurchase();
		gp1.setTitle("aaa");
		gp1.setSellerId("hi");
		
		GroupPurchase gp2 = new GroupPurchase();
		gp2.setTitle("bbb");
		gp2.setSellerId("hi");
		
		GroupPurchase gpResult1 = gpRepository.save(gp1);
		GroupPurchase gpResult2 = gpRepository.save(gp2);
		
		WishGroupPurchase wish1 = new WishGroupPurchase("hi", gpResult1.getGpId());
		WishGroupPurchase wish2 = new WishGroupPurchase("hi", gpResult2.getGpId());
		
		wish1.setGp(gp1);
		wish2.setGp(gp2);
		
		wishRepository.save(wish1);
		wishRepository.save(wish2);
		
		WishGroupPurchase result = wishRepository.findByUserIdAndGpId("hi", gpResult1.getGpId());
		
		assertEquals(result.getGp().getTitle(), "aaa");
		
	}
	
	@Test
	@DisplayName("위시 공구 리스트")
	void wishList() {
		
		GroupPurchase gp1 = new GroupPurchase();
		gp1.setTitle("aaa");
		gp1.setSellerId("hi");
		
		GroupPurchase gp2 = new GroupPurchase();
		gp2.setTitle("bbb");
		gp2.setSellerId("hi");
		
		GroupPurchase gpResult1 = gpRepository.save(gp1);
		GroupPurchase gpResult2 = gpRepository.save(gp2);
		
		WishGroupPurchase wish1 = new WishGroupPurchase("hi", gpResult1.getGpId());
		WishGroupPurchase wish2 = new WishGroupPurchase("hi", gpResult2.getGpId());
		
		wish1.setGp(gp1);
		wish2.setGp(gp2);
		
		wishRepository.save(wish1);
		wishRepository.save(wish2);
		
		List<WishGroupPurchase> result = wishRepository.findByUserId("hi");
		
		assertEquals(result.size(), 2);
		
	}
	
	@Test
	@DisplayName("위시 공구 삭제")
	void delete() {
		
		GroupPurchase gp1 = new GroupPurchase();
		gp1.setTitle("aaa");
		gp1.setSellerId("hi");
		
		GroupPurchase gp2 = new GroupPurchase();
		gp2.setTitle("bbb");
		gp2.setSellerId("hi");
		
		GroupPurchase gpResult1 = gpRepository.save(gp1);
		GroupPurchase gpResult2 = gpRepository.save(gp2);
		
		WishGroupPurchase wish1 = new WishGroupPurchase("hi", gpResult1.getGpId());
		WishGroupPurchase wish2 = new WishGroupPurchase("hi", gpResult2.getGpId());
		
		wish1.setGp(gp1);
		wish2.setGp(gp2);
		
		wishRepository.save(wish1);
		wishRepository.save(wish2);
		
		List<WishGroupPurchase> result = wishRepository.findByUserId("hi");
		
		assertEquals(result.size(), 2);
		
		wishRepository.deleteByUserIdAndGpId("hi", gpResult1.getGpId());
		
		List<WishGroupPurchase> result2 = wishRepository.findByUserId("hi");
		
		assertEquals(result2.size(), 1);
		
	}
	
	
	

}
