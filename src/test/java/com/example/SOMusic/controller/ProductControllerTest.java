package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.service.ImgValidator;
import com.example.SOMusic.service.ProductServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	
	@Autowired
	MockMvc mvc;

	@MockBean
	ProductServiceImpl prsvc;
	
	@MockBean
	ImgValidator img;
	
	MockHttpServletRequest request;

	@BeforeEach()
    public void setUp() throws Exception {
       
       Account account = new Account();
      account.setUserName("hi-he");
      account.setUserId("hi");

       
        request = new MockHttpServletRequest();
    }
	
	@Test
	@DisplayName("register") 
	public void register() throws Exception{
		
		Product pr = new Product();
		pr.setProductName("scarlet");
		pr.setSellerId("bomin");
		
		
		mvc.perform(MockMvcRequestBuilders
				.post("/product/register"))
				.andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	@DisplayName("delete")
	public void delete() throws Exception{
		
		Product pr = pr();

		String id = String.valueOf(pr.getProductId());
		int id2 = pr.getProductId();
		
		Mockito.when(prsvc.findProductByProductId(id2)).thenReturn(pr);

		mvc.perform(MockMvcRequestBuilders
				.get("/product/delete")
				.param("productId", id))
				.andDo(MockMvcResultHandlers.print())
				 .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				 .andExpect(MockMvcResultMatchers.redirectedUrl("/user/my/sale/list"));
			        

	
	}
	
	public Product pr() {
		Product pr = new Product("scarlet", 123, "s", "s", 123, 123,
				123, 123, "s", "bomin", "s", "s");
		
		return pr;
	}	
	
	public List<Product> prList() {
		Product pr1 = new Product("scarlet", 123, "s", "s", 123, 123,
				123, 123, "s", "hi", "s", "s");
		Product pr2 = new Product("violet", 123, "s", "s", 123, 123,
				123, 123, "s", "hi", "s", "s");
		
		List<Product> prList = new ArrayList<>();
		prList.add(pr1);
		prList.add(pr2);
		
		return prList;
	}

}
