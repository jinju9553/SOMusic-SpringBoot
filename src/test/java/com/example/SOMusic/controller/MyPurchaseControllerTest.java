package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.domain.TestPurchase;
import com.example.SOMusic.service.ProductService;
import com.example.SOMusic.service.ProductServiceImpl;
import com.example.SOMusic.service.PurchaseServiceImpl;
import com.example.SOMusic.service.PurchaseValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MyPurchaseController.class)
class MyPurchaseControllerTest {

    private static final String URI_TEMPLATE = "/purchase";
    private static final String PURCHASE_INFO = "purchase/myPurchaseInfo";
    private static final String PURCHASE_CHECK = "purchase/purchaseCheck";

    @Autowired
    MockMvc mvc;

    @MockBean
    PurchaseServiceImpl purchaseService;

    @MockBean
    ProductServiceImpl productService;

    @MockBean
    PurchaseValidator validator;

    Purchase purchase = TestPurchase.createTestPurchase();

    @Test
    void showForm() throws Exception {
        int purchaseId = purchase.getPurchaseId();

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/info/" + purchaseId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name(PURCHASE_INFO));
    }

    @Test
    void showForm2() throws Exception {
        int purchaseId = purchase.getPurchaseId();

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/check/" + purchaseId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name(PURCHASE_CHECK));
    }

    @Test
    void formBackingInfo() throws Exception {
        int purchaseId = purchase.getPurchaseId();

        Mockito.when(purchaseService.findPurchaseByPurchaseId(purchaseId)).thenReturn(purchase);

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/info/" + purchaseId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("purchaseInfoReq"));
    }

    @Test
    void update() throws Exception {
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        int purchaseId = purchase.getPurchaseId();
        Product product = purchase.getProduct();
        int productId = product.getProductId();

        Mockito.when(productService.findProductByProductId(productId)).thenReturn(product);

        Mockito.doNothing().when(validator).validate(purchase, bindingResult);
        Mockito.doNothing().when(purchaseService).modifyPurchaseInfo(purchaseId, purchase);

        mvc.perform(MockMvcRequestBuilders.post(URI_TEMPLATE + "/info/" + purchaseId)
                        .param("productId", String.valueOf(productId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void delete() throws Exception {
        int purchaseId = purchase.getPurchaseId();

        Mockito.when(purchaseService.findPurchaseByPurchaseId(purchaseId)).thenReturn(purchase);
        Mockito.doNothing().when(purchaseService).deletePurchase(purchaseId);

        purchaseService.deletePurchase(purchaseId);
        Mockito.verify(purchaseService, Mockito.atLeastOnce()).deletePurchase(purchaseId);

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/delete/" + purchaseId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void confirm() throws Exception {
        int purchaseId = purchase.getPurchaseId();

        Mockito.doNothing().when(purchaseService).confirmPurchase(purchaseId);

        mvc.perform(MockMvcRequestBuilders.post(URI_TEMPLATE + "/check/" + purchaseId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }
}