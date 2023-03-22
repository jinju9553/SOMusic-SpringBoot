package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.TestAccount;
import com.example.SOMusic.service.AccountFormValidator;
import com.example.SOMusic.service.AccountServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignupController.class)
public class SignupControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AccountServiceImpl accountService;

    @MockBean
    AccountFormValidator validator;

    private static final String URI_TEMPLATE = "/user/register";
    private static final String REGISTER_FORM = "thyme/user/account/registerForm";
    private static final String REGISTER_DONE = "thyme/user/account/signupDone";

    @Test
    @DisplayName("GET_formBacking_테스트")
    void formBacking() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("accountForm"));
    }

    @Test
    @DisplayName("GET_showForm_테스트")
    void showForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URI_TEMPLATE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name(REGISTER_FORM));
    }

    @Test
    @DisplayName("POST_register_테스트")
    void register() throws Exception {
        Account account = TestAccount.createTestAccount();
        AccountForm accountForm = new AccountForm(account);
        String id = account.getUserId();

        Mockito.when(accountService.getAccount(id)).thenReturn(account);

        mvc.perform(MockMvcRequestBuilders.post(URI_TEMPLATE)
                        .param("accountForm", accountForm.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name(REGISTER_DONE))
                .andExpect(model().attributeExists("accountForm"));
    }

    @Test
    @DisplayName("POST_isDuplicated_테스트")
    void check() throws Exception {
        String id = "amy1234";
        Mockito.when(accountService.isDuplicated(id)).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.post(URI_TEMPLATE + "/checkId?id=" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
