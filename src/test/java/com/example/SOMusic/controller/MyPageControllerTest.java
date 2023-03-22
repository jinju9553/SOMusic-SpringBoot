package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.TestAccount;
import com.example.SOMusic.service.AccountFormValidator;
import com.example.SOMusic.service.AccountServiceImpl;
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
import org.springframework.validation.BindingResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MyPageController.class)
class MyPageControllerTest {

    private static final String URI_TEMPLATE = "/user/my";
    private static final String MY_PAGE = "thyme/user/my/myPage";
    private static final String RECOVER_ID_PAGE = "thyme/user/account/recoverAccount";
    private static final String RESET_PW_PAGE = "thyme/user/account/resetPassword";

    @Autowired
    MockMvc mvc;

    @MockBean
    AccountServiceImpl accountService;

    @MockBean
    AccountFormValidator validator;

    Account account = TestAccount.createTestAccount();

    Login userSession;

    MockHttpSession session;

    @BeforeEach()
    public void setUp() {
        userSession = new Login(account);

        session = new MockHttpSession();
        session.setAttribute("userSession", userSession);
    }

    @Test
    void showForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE)
                        .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name(MY_PAGE));
    }

    @Test
    void formBacking() throws Exception {
        String userId = account.getUserId();

        Mockito.when(accountService.getAccount(userId)).thenReturn(account);

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE)
                        .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("accountForm"));
    }

    @Test
    void update() throws Exception {
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        AccountForm accountForm = (AccountForm) session.getAttribute("accountForm");

        Mockito.doNothing().when(validator).validate(accountForm, bindingResult);
        Mockito.doNothing().when(accountService).updateAccount(account);

        mvc.perform(MockMvcRequestBuilders.post(URI_TEMPLATE + "/update")
                        .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updatePassword() throws Exception {
        String newPassword = "newPassword";

        Mockito.doNothing().when(accountService).updatePassword(account, newPassword);

        mvc.perform(MockMvcRequestBuilders.post(URI_TEMPLATE + "/update/password")
                        .param("password", newPassword)
                        .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void drop() throws Exception {
        Mockito.doNothing().when(accountService).deleteAccount(account);

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/drop")
                        .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void findId() throws Exception {
        String phone = account.getPhone();
        String email = account.getEmail();

        Mockito.when(accountService.findAccount(phone, email)).thenReturn(account);

        mvc.perform(MockMvcRequestBuilders.post(URI_TEMPLATE + "/find/id")
                        .param("phone", phone)
                        .param("email", email))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name(RECOVER_ID_PAGE));
    }

    @Test
    void findPasswordGET() throws Exception {
        String phone = account.getPhone();
        String userId = account.getUserId();

        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE + "/find/password")
                        .param("userId", userId)
                        .param("phone", phone)
                        .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("searchResult"))
                .andExpect(view().name(RESET_PW_PAGE));
    }

    @Test
    void findPasswordPOST() throws Exception {
        String phone = account.getPhone();
        String userId = account.getUserId();

        Mockito.when(accountService.findPassword(phone, userId)).thenReturn(account);

        mvc.perform(MockMvcRequestBuilders.post(URI_TEMPLATE + "/find/password")
                        .param("userId", userId)
                        .param("phone", phone))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("searchResult"))
                .andExpect(view().name(RESET_PW_PAGE));
    }
}