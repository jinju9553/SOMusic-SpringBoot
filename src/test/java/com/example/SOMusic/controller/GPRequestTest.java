package com.example.SOMusic.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

class GPRequestTest {

	@Test
	public void gpReq() {
		
		byte[] fileContent = "Image Link".getBytes();
        String filename = "test.png";

        MockMultipartFile image = new MockMultipartFile("file", filename, MediaType.TEXT_PLAIN_VALUE, fileContent);

		GPRequest gpReq = new GPRequest(1, "hi", "aaa", image,
									LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31),
									"music", "3333", "bank", 1000,
									"It is\n good!");
		
		assertEquals("aaa", gpReq.getTitle());
		assertEquals(image, gpReq.getImage());
		
	}

}
