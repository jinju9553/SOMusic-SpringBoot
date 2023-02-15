package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.service.AccountFormValidator;
import com.example.SOMusic.service.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignupController.class)
public class SignupControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AccountServiceImpl accountService;

    @MockBean
    AccountFormValidator validator;

    @Test
    void POST_register_테스트() throws Exception{
        //given
        AccountForm accountForm = createTestAccountForm();
        Account account = accountForm.getAccount();
        String id = account.getUserId();

        Mockito.when(accountService.getAccount(id)).thenReturn(account);

        //when & then
        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .param("accountForm", accountForm.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void POST_isDuplicated_테스트() throws Exception{
        //given
        String id = "amy1234";
        Mockito.when(accountService.isDuplicated(id)).thenReturn(true);

        //when & then
        mvc.perform(MockMvcRequestBuilders.post("/user/register/checkId?id=" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    public AccountForm createTestAccountForm(){
        AccountForm accountForm = new AccountForm();
        Account account = accountForm.getAccount();

        account.setUserId("mark123");
        account.setUserName("mark");
        account.setEmail("mark123@gmail.com");
        account.setPhone("01012345678");

        accountForm.setAccount(account);

        return accountForm;
    }

}
