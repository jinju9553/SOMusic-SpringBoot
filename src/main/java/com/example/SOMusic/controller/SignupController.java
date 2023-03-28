package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.service.AccountFormValidator;
import com.example.SOMusic.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/user/register")
public class SignupController {

    private static final String REGISTER_FORM = "thyme/user/account/registerForm";
    private static final String REGISTER_DONE = "thyme/user/account/signupDone";

    @Autowired
    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    private AccountFormValidator validator;

    public void setValidator(AccountFormValidator validator) {
        this.validator = validator;
    }

    @ModelAttribute("accountForm")
    public AccountForm formBacking(HttpServletRequest request) throws Exception {

        Login userSession =
                (Login) WebUtils.getSessionAttribute(request, "userSession");

        String uri = request.getRequestURI();
        if (isLoggedIn(userSession) && !(uri.contains("/checkId"))) {
            String userId = (userSession.getAccount()).getUserId();
            Account account = accountService.getAccount(userId);

            return new AccountForm(account);
        } else {
            return new AccountForm();
        }
    }

    private static boolean isLoggedIn(Login userSession) {
        return (userSession != null);
    }

    @GetMapping
    public String showForm() {
        return REGISTER_FORM;
    }

    @PostMapping
    public String register(
            HttpSession session,
            @ModelAttribute("accountForm") AccountForm accountForm,
            BindingResult result) throws Exception {
        validator.validate(accountForm, result);

        if (result.hasErrors()) {
            return REGISTER_FORM;
        }

        Account account = accountForm.getAccount();
        try {
            if (accountForm.isNewAccount()) {
                accountService.insertAccount(account);
            }
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("account.username", "USER_ID_ALREADY_EXISTS",
                    "*중복된 아이디입니다.");
            return REGISTER_FORM;
        }

        Account sessionAccount = accountService.getAccount(account.getUserId());
        Login userSession = new Login(sessionAccount);

        session.setAttribute("userSession", userSession);
        return REGISTER_DONE;
    }

    @ResponseBody
    @PostMapping("/checkId")
    public boolean check(@RequestParam("id") String userId) {
        return accountService.isDuplicated(userId);
    }
}
