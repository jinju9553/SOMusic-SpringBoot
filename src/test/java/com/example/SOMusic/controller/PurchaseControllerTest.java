package com.example.SOMusic.controller;

import com.example.SOMusic.domain.*;
import com.example.SOMusic.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PurchaseServiceImpl purchaseService;

    @MockBean
    ProductServiceImpl productService;

    @MockBean
    AccountServiceImpl accountService;

    @MockBean
    PurchaseValidator validator;

    private static final String USER_ID = "tripleS";
    private static final String URI_TEMPLATE = "/purchase";
    private static final String PURCHASE_FORM = "purchase/purchaseForm";
    private static final String PURCHASE_DONE = "thyme/purchase/purchaseDone";
    Purchase purchase = TestPurchase.createTestPurchase();

    Account account = new Account();

    Login userSession;

    MockHttpSession session;

    @BeforeEach()
    public void setUp() {
        account.setUserId(USER_ID);

        userSession = new Login(account);

        session = new MockHttpSession();
        session.setAttribute("userSession", userSession);
    }

    @Test
    void showForm() throws Exception {
        Product product = purchase.getProduct();
        int productId = product.getProductId();

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/" + productId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name(PURCHASE_FORM));
    }

    @Test
    void formBacking() throws Exception {
        Product product = purchase.getProduct();
        int productId = product.getProductId();

        Mockito.when(productService.findProductByProductId(productId)).thenReturn(product);
        Mockito.when(accountService.getAccount(USER_ID)).thenReturn(account);

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/" + productId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("purchaseReq"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    void register() throws Exception {
        Product product = purchase.getProduct();
        int productId = product.getProductId();

        Mockito.when(productService.findProductByProductId(productId)).thenReturn(product);
        Mockito.when(accountService.getAccount(USER_ID)).thenReturn(account);
        Mockito.when(purchaseService.calculateTotal(product)).thenReturn(purchase.getTotalAmount());

        Mockito.doNothing().when(purchaseService).registerPurchase(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.post(URI_TEMPLATE + "/" + productId)
                        .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("purchaseReq"))
                .andExpect(view().name(PURCHASE_DONE));
    }
}