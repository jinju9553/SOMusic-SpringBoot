package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Account;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.service.AccountFormValidator;
import com.example.SOMusic.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes({"userSession", "tempSession"})
@RequestMapping("/user/my")
public class MyPageController {

    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String MY_PAGE = "thyme/user/my/myPage";
    private static final String RECOVER_ID_PAGE = "thyme/user/account/recoverAccount";
    private static final String RESET_PW_PAGE = "thyme/user/account/resetPassword";

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

    @GetMapping
    public String showForm() {
        return MY_PAGE;
    }

    @ModelAttribute("accountForm")
    public AccountForm formBacking(HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            Login userSession =
                    (Login) WebUtils.getSessionAttribute(request, "userSession");

            AccountForm accountForm = new AccountForm(accountService.getAccount(userSession.getAccount().getUserId()));

            return accountForm;
        } else {
            return new AccountForm();
        }
    }

    @PostMapping("/update")
    public String update(
            HttpServletRequest request,
            @ModelAttribute("accountForm") AccountForm accountForm,
            BindingResult result) throws ModelAndViewDefiningException {

        accountForm.setNewAccount(false);
        validator.validate(accountForm, result);

        if (result.hasErrors()) {
            return MY_PAGE;
        }

        Login userSession =
                (Login) WebUtils.getSessionAttribute(request, "userSession");

        Account updatedAccount = accountForm.getAccount();
        String existingPassword = (userSession.getAccount()).getPassword();

        updatedAccount.setPassword(existingPassword);

        accountService.updateAccount(updatedAccount);

        return "redirect:/" + "user/my";
    }

    @PostMapping("/update/password")
    public String updatePassword(
            HttpServletRequest request,
            @RequestParam("password") String password) throws ModelAndViewDefiningException {

        Login userSession =
                (Login) WebUtils.getSessionAttribute(request, "userSession");

        if (!isLoggedIn(userSession)) {
            userSession = (Login) WebUtils.getSessionAttribute(request, "tempSession");
            request.removeAttribute("tempSession");
        }

        Account account = userSession.getAccount();
        accountService.updatePassword(account, password);

        return "redirect:/" + "user/loginForm";
    }

    @GetMapping("/drop")
    public String drop(HttpServletRequest request) throws ModelAndViewDefiningException {

        Login userSession =
                (Login) WebUtils.getSessionAttribute(request, "userSession");

        Account account = userSession.getAccount();
        accountService.deleteAccount(account);

        return "redirect:/" + "user/logout";
    }

    @PostMapping("/find/id")
    public ModelAndView findId(
            @RequestParam("phone") String phone,
            @RequestParam("email") String email, Model model) {

        Account account = accountService.findAccount(phone, email);

        if (accountExists(account)) {
            String id = account.getUserId();
            model.addAttribute("id", id);
        }

        return new ModelAndView(RECOVER_ID_PAGE);
    }

    @RequestMapping("/find/password")
    public ModelAndView findPassword(
            HttpServletRequest request,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "phone", required = false) String phone, Model model) {

        if (isPost(request)) {
            Account account = accountService.findPassword(userId, phone);

            if (accountExists(account)) {
                Login tempSession = new Login(account);
                model.addAttribute("tempSession", tempSession);
                model.addAttribute("searchResult", true);
            } else {
                model.addAttribute("searchResult", false);
            }
        }

        if (isGet(request)) {
            Login userSession =
                    (Login) WebUtils.getSessionAttribute(request, "userSession");

            if (!isLoggedIn(userSession)) {
                return new ModelAndView("redirect:/" + "user/logout");
            }

            model.addAttribute("searchResult", true);
        }

        return new ModelAndView(RESET_PW_PAGE);
    }

    private static boolean isPost(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(POST);
    }

    private static boolean isGet(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(GET);
    }

    private static boolean isLoggedIn(Login userSession) {
        return (userSession != null);
    }

    private static boolean accountExists(Account account) {
        return (account != null);
    }
}