package com.example.SOMusic.controller;

import com.example.SOMusic.domain.*;
import com.example.SOMusic.service.AccountService;
import com.example.SOMusic.service.GPService;
import com.example.SOMusic.service.JoinService;
import com.example.SOMusic.service.JoinValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/join")
public class JoinController {

    private static final int INITIAL_QUANTITY = 1;
    private static final int INITIAL_SHIPPING_METHOD = 1;
    private static final int INITIAL_STATUS = 1;
    private static final String JOIN_FORM = "join/joinForm";
    private static final String JOIN_DONE = "thyme/join/joinDone";

    @Autowired
    private JoinService joinService;

    public void setJoinService(JoinService joinService) {
        this.joinService = joinService;
    }

    @Autowired
    private GPService gpService;

    public void setGPService(GPService gpService) {
        this.gpService = gpService;
    }

    @Autowired
    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    private JoinValidator validator;

    public void setValidator(JoinValidator validator) {
        this.validator = validator;
    }

    @ModelAttribute("shippingOption")
    public Object[] referenceOption() {
        Object[] data = ShippingCost.getCodeList();

        return data;
    }

    @ModelAttribute("shippingText")
    public Object[] referenceText() {
        Object[] data = ShippingCost.getNameList();

        return data;
    }

    @ModelAttribute("shippingCost")
    public Object[] referenceCost() {
        Object[] data = ShippingCost.getCostList();

        return data;
    }

    @GetMapping("/{gpId}")
    public String showForm(HttpServletRequest request,
                           @PathVariable("gpId") int gpId, Model model) {
        Login userSession =
                (Login) WebUtils.getSessionAttribute(request, "userSession");

        WishGroupPurchase wishGp = null;
        if (isLoggedIn(userSession)) {
            String userId = (userSession.getAccount()).getUserId();
            wishGp = gpService.getWishGP(userId, gpId);
        }

        model.addAttribute("wishGp", wishGp);

        return JOIN_FORM;
    }


    @ModelAttribute("joinReq")
    public Join formBacking(HttpServletRequest request,
                            @PathVariable("gpId") int gpId, Model model) {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            Login userSession =
                    (Login) WebUtils.getSessionAttribute(request, "userSession");

            if (isLoggedIn(userSession)) {
                Account account = accountService.getAccount(userSession.getAccount().getUserId());
                model.addAttribute("account", account);
            }

            GroupPurchase gp = gpService.getGP(gpId);
            Join join = initJoin(gp);

            return join;
        } else {
            return new Join();
        }
    }

    private static boolean isLoggedIn(Login userSession) {
        return (userSession != null);
    }

    private static Join initJoin(GroupPurchase gp) {
        Join join = new Join();

        join.setGroupPurchase(gp);
        if (isInitializing(join)) {
            join.setQuantity(INITIAL_QUANTITY);
            join.setShippingMethod(INITIAL_SHIPPING_METHOD);
        }
        return join;
    }

    private static boolean isInitializing(Join join) {
        return (join.getQuantity() == 0);
    }

    @PostMapping("/{gpId}")
    public String register(
            HttpServletRequest request,
            @PathVariable("gpId") int gpId,
            @ModelAttribute("joinReq") Join joinReq,
            BindingResult result, Model model) throws Exception {

        Login userSession =
                (Login) WebUtils.getSessionAttribute(request, "userSession");

        validator.validate(joinReq, result);

        String userId = (userSession.getAccount()).getUserId();
        if (result.hasErrors()) {
            WishGroupPurchase wishGp = gpService.getWishGP(userId, gpId);
            model.addAttribute("wishGp", wishGp);
            model.addAttribute("joinReq", joinReq);

            return JOIN_FORM;
        }

        GroupPurchase groupPurchase = gpService.getGP(gpId);
        Join join = initJoin(joinReq, userId, groupPurchase);

        joinService.registerJoin(join);

        return JOIN_DONE;
    }

    private Join initJoin(Join joinReq, String userId, GroupPurchase groupPurchase) {
        Join join = joinReq;

        join.setGroupPurchase(groupPurchase);
        join.setConsumerId(userId);
        join.setShippingCost(joinService.initShippingCost(joinReq));
        join.setTotalAmount(joinService.calculateTotal(groupPurchase, joinReq));
        join.setStatus(INITIAL_STATUS);
        join.setRegDate(new Date());

        return join;
    }
}
