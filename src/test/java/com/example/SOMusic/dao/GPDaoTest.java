package com.example.SOMusic.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.SOMusic.domain.GroupPurchase;

@DataJpaTest
class GPDaoTest {
	
	@MockBean
	GPDao dao;

	@Test
	@DisplayName("공구 생성 확인")
	void create() {

		LocalDate start = LocalDate.of(2023, 1, 1);
		LocalDate end = LocalDate.of(2023, 1, 31);
		
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link", start, end, "music", "3333", "bank", 1000, "Is is");
		
		dao.insertGP(gp);
		
		assertEquals("aaa", gp.getTitle());		
	}
	
	@Test
	@DisplayName("공구 수정 확인")
	void udate() {

		LocalDate start = LocalDate.of(2023, 1, 1);
		LocalDate end = LocalDate.of(2023, 1, 31);
		
		GroupPurchase gp = new GroupPurchase(1, "hi", "aaa", "link", start, end, "music", "3333", "bank", 1000, "Is is");
		dao.insertGP(gp);
		
		gp.setTitle("bbb");
		dao.updateGP(gp);
		
		assertEquals("bbb", gp.getTitle());		
	}

}
