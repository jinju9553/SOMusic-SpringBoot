package com.example.SOMusic.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
	
	GroupPurchase gp1;
	
	@BeforeEach
	void setup() {
		
		gp1 = new GroupPurchase();
		gp1.setTitle("Rover");
		gp1.setSellerId("kai");
		
		GroupPurchase gp2 = new GroupPurchase();
		gp2.setTitle("Hi-five");
		gp2.setSellerId("TeStar");

		gp1 = gpRepository.save(gp1);
		gp2 = gpRepository.save(gp2);

		WishGroupPurchase wish1 = new WishGroupPurchase("hi", gp1.getGpId());
		wish1.setGp(gp1);
		
		WishGroupPurchase wish2 = new WishGroupPurchase("hi", gp2.getGpId());
		wish2.setGp(gp2);
		
		wishRepository.save(wish1);
		wishRepository.save(wish2);
	
	}

	@Test
	@DisplayName("위시 공구 생성 확인")
	void create() {
		
		GroupPurchase gp = new GroupPurchase();
		gp.setSellerId("basket");
		gp.setTitle("mitsu");
		gp = gpRepository.save(gp);
		
		WishGroupPurchase wish = new WishGroupPurchase("hi", gp.getGpId());
		wish.setGp(gp);
		wish = wishRepository.save(wish);
		
		assertEquals(wish.getGp().getTitle(), gp.getTitle());
		
	}
	
	@Test
	@DisplayName("위시 공구 가져오기")
	void wishGP() {
		
		WishGroupPurchase result = wishRepository.findByUserIdAndGpId("hi", gp1.getGpId());
		
		assertEquals(result.getGp().getTitle(), "Rover");
		
	}
	
	@Test
	@DisplayName("위시 공구 삭제")
	void delete() {
		
		wishRepository.deleteByUserIdAndGpId("hi", gp1.getGpId());
		
		List<WishGroupPurchase> result = wishRepository.findByUserId("hi");
		
		assertEquals(result.size(), 1);
		
	}
	
	@Test
	@DisplayName("위시 공구 리스트")
	void wishList() {
		
		List<WishGroupPurchase> result = wishRepository.findByUserId("hi");
		
		assertEquals(result.size(), 2);
		
	}

}
