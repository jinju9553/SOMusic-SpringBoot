package com.example.SOMusic.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GroupPurchaseTest {

	@Test
	@DisplayName("도메인 생성")
	void createGP() {
	
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link",
											LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
											"music", "3333", "bank", 1000, "Is is");
		
		assertEquals("aaa", gp.getTitle());
	}
	

}
