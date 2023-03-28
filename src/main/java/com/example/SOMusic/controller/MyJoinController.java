package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Join;
import com.example.SOMusic.domain.ShippingCost;
import com.example.SOMusic.service.JoinService;
import com.example.SOMusic.service.JoinValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("userSession")
@RequestMapping("/join")
public class MyJoinController {
    private static final String JOIN_INFO = "join/myJoinInfo";

    @Autowired
    private JoinService joinService;

    public void setJoinService(JoinService joinService) {
        this.joinService = joinService;
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

    @Autowired
    private JoinValidator validator;

    public void setValidator(JoinValidator validator) {
        this.validator = validator;
    }

    @GetMapping("/info/{joinId}")
    public String showForm() {
        return JOIN_INFO;
    }

    @ModelAttribute("joinInfoReq")
    public Join formBackingInfo(HttpServletRequest request,
                                @PathVariable("joinId") int joinId) {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            Join join = joinService.findJoinByJoinId(joinId);

            return join;
        } else {
            return new Join();
        }
    }

    @PostMapping("/info/{joinId}")
    public String update(
            @ModelAttribute("joinInfoReq") Join join,
            BindingResult result, Model model) throws Exception {

        validator.validate(join, result);

        if (result.hasErrors()) {
            model.addAttribute("joinInfoReq", join);
            return JOIN_INFO;
        }

        joinService.modifyJoin(join);

        return "redirect:/" + "join/info/{joinId}";
    }

    @GetMapping("/delete/{joinId}")
    public String delete(@PathVariable("joinId") int joinId) throws Exception {

        Join join = joinService.findJoinByJoinId(joinId);
        join.setGroupPurchase(null);

        joinService.deleteJoin(joinId);

        return "redirect:/" + "user/my/join/list";
    }
}
