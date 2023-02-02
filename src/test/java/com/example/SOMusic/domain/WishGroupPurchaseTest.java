package com.example.SOMusic.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WishGroupPurchaseTest {

	@Test
	@DisplayName("위시 공구 생성")
	void crate() {
		WishGroupPurchase wish = new WishGroupPurchase("hi", 1);
		
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link",
				LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
				"music", "3333", "bank", 1000, "Is is");
		
		wish.setGp(gp);
		
		assertEquals("aaa", wish.getGp().getTitle());
	}

}
