package com.example.SOMusic.controller;

import com.example.SOMusic.domain.Product;
import com.example.SOMusic.domain.Purchase;
import com.example.SOMusic.service.ProductService;
import com.example.SOMusic.service.PurchaseService;
import com.example.SOMusic.service.PurchaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/purchase")
public class MyPurchaseController {
    private static final String PURCHASE_INFO = "purchase/myPurchaseInfo";
    private static final String PURCHASE_CHECK = "purchase/purchaseCheck";

    @Autowired
    private PurchaseService purchaseService;

    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Autowired
    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    private PurchaseValidator validator;

    public void setValidator(PurchaseValidator validator) {
        this.validator = validator;
    }

    @GetMapping("/info/{purchaseId}")
    public String showForm() {
        return PURCHASE_INFO;
    }

    @GetMapping("/check/{purchaseId}")
    public String showForm2() {
        return PURCHASE_CHECK;
    }

    @ModelAttribute("purchaseInfoReq")
    public Purchase formBackingInfo(HttpServletRequest request,
                                    @PathVariable("purchaseId") int purchaseId) {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            Purchase purchaseReq = purchaseService.findPurchaseByPurchaseId(purchaseId);

            return purchaseReq;
        } else {
            return new Purchase();
        }
    }

    @PostMapping("/info/{purchaseId}")
    public String update(
            @RequestParam("productId") int productId,
            @ModelAttribute("purchaseInfoReq") Purchase purchase,
            BindingResult result, Model model) throws Exception {

        Product product = productService.findProductByProductId(productId);
        purchase.setProduct(product);

        validator.validate(purchase, result);

        if (result.hasErrors()) {
            model.addAttribute("purchaseInfoReq", purchase);
            return PURCHASE_INFO;
        }

        int purchaseId = purchase.getPurchaseId();
        Purchase oldPurchase = purchaseService.findPurchaseByPurchaseId(purchaseId);

        purchaseService.modifyPurchaseInfo(oldPurchase, purchase);

        return "redirect:/" + "purchase/info/{purchaseId}";
    }

    @GetMapping("/delete/{purchaseId}")
    public String delete(@PathVariable("purchaseId") int purchaseId) throws Exception {

        Purchase purchase = purchaseService.findPurchaseByPurchaseId(purchaseId);
        purchase.setProduct(null);

        purchaseService.deletePurchase(purchaseId);

        return "redirect:/" + "user/my/purchase/list";
    }

    @PostMapping("/check/{purchaseId}")
    public String confirm(@PathVariable("purchaseId") int purchaseId) throws Exception {

        Purchase purchase = purchaseService.findPurchaseByPurchaseId(purchaseId);
        purchaseService.confirmPurchase(purchase);

        return "redirect:/" + "user/my/sale/list";
    }
}
