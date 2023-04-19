package com.example.SOMusic.controller;

import com.example.SOMusic.domain.*;
import com.example.SOMusic.service.AccountServiceImpl;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.JoinServiceImpl;
import com.example.SOMusic.service.JoinValidator;
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

@WebMvcTest(JoinController.class)
class JoinControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    JoinServiceImpl joinService;

    @MockBean
    GPService gpService;

    @MockBean
    AccountServiceImpl accountService;

    @MockBean
    JoinValidator validator;

    private static final String URI_TEMPLATE = "/join";
    private static final String JOIN_FORM = "join/joinForm";
    private static final String JOIN_DONE = "thyme/join/joinDone";

    private static final String USER_ID = "mark123";

    GroupPurchase groupPurchase = createTestGroupPurchase();
    Join join = TestJoin.createTestJoin();

    Object[] shippingOption = ShippingCost.getCodeList();
    Object[] shippingText = ShippingCost.getNameList();
    Object[] shippingCost = ShippingCost.getCostList();

    Login userSession;

    MockHttpSession session;

    @BeforeEach()
    public void setUp() {
        Account account = new Account();
        account.setUserId(USER_ID);

        userSession = new Login(account);

        session = new MockHttpSession();
        session.setAttribute("userSession", userSession);
    }

    @Test
    void showForm() throws Exception {
        int gpId = groupPurchase.getGpId();

        WishGroupPurchase wishGroupPurchase = new WishGroupPurchase();
        wishGroupPurchase.setGp(groupPurchase);

        Mockito.when(gpService.getWishGP(USER_ID, gpId)).thenReturn(wishGroupPurchase);

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/" + gpId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name(JOIN_FORM))
                .andExpect(model().attribute("shippingOption", shippingOption))
                .andExpect(model().attribute("shippingText", shippingText))
                .andExpect(model().attribute("shippingCost", shippingCost));
    }

    @Test
    void formBacking() throws Exception {
        int gpId = groupPurchase.getGpId();
        Account account = new Account();

        account.setUserId(USER_ID);

        Mockito.when(gpService.getGP(gpId)).thenReturn(groupPurchase);
        Mockito.when(accountService.getAccount(USER_ID)).thenReturn(account);

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/" + gpId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("joinReq"));
    }

    @Test
    void register() throws Exception {
        int gpId = groupPurchase.getGpId();

        WishGroupPurchase wishGroupPurchase = new WishGroupPurchase();
        wishGroupPurchase.setGp(groupPurchase);

        Mockito.when(gpService.getGP(gpId)).thenReturn(groupPurchase);
        Mockito.when(gpService.getWishGP(USER_ID, gpId)).thenReturn(wishGroupPurchase);

        Mockito.when(joinService.initShippingCost(join)).thenReturn(join.getShippingCost());
        Mockito.when(joinService.calculateTotal(join.getGroupPurchase(), join)).thenReturn(join.getTotalAmount());

        mvc.perform(MockMvcRequestBuilders.post(URI_TEMPLATE + "/" + gpId)
                        .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name(JOIN_DONE));
    }

    public static GroupPurchase createTestGroupPurchase() {
        GroupPurchase groupPurchase = new GroupPurchase();

        groupPurchase.setGpId(2);

        return groupPurchase;
    }
}