package com.example.SOMusic.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.domain.Product;
import com.example.SOMusic.service.ProductService;

@Controller
@SessionAttributes("userSession")
@RequestMapping(value="/product")
public class ProductController implements ApplicationContextAware{
	
	private static final String Product_REGISTER_FORM = "thyme/product/register/productRegisterForm";
	private static final String Product_REGISTER_SEUCCESS = "/product/register/success";	// redirect
	private static final String Product_REGISTER_SEUCCESS_View = "thyme/product/register/ProductRegisterSuccess";

	private static final String Product_UPDATE_FORM = "thyme/Product/update/ProductUpdateForm";
	private static final String PR_UPDATE_SEUCCESS = "/product/info";
	
	
	
	// 이미지 업로드
	@Value("/upload/")
	private String uploadDirLocal;
		
	private WebApplicationContext context;	
	private String uploadDir;
	
	
	@Override
	public void setApplicationContext(ApplicationContext appContext)
		throws BeansException {			
		this.context = (WebApplicationContext) appContext;
		this.uploadDir = context.getServletContext().getRealPath(this.uploadDirLocal);
		System.out.println("this.uploadDir : " + this.uploadDir);
	}
	
	@Autowired
	private ProductService prSvc;
	public void setProductService(ProductService prSvc) { 
		this.prSvc = prSvc;
	}
	
	
  @ModelAttribute("PrReq")
  public ProductRequest formBacking(HttpServletRequest request) throws Exception { 
	     String PrId = request.getParameter("productId"); 
	     System.out.println("폼백킹에서 출력합니다 : " + PrId);
		 ProductRequest prReq = new ProductRequest();
		 
		 
		 System.out.println("prReq의 ProductId : " + prReq.getProductId());
		 
		  //PrId가 없으면 register 
		  if (PrId == null) 
			  return prReq; 
		  //있으면 update
		  else {
			  prReq.initProductReq(prSvc.findProductByProductId(Integer.parseInt(PrId))); 
			  prReq.setProductId(Integer.parseInt(PrId));
			  return prReq; 
			  }
		  }

  //register
  @GetMapping(value="/register") 
  public String showProductRegForm() {
	  	System.out.println("폼 불러옴");
		 return Product_REGISTER_FORM; 
		  }
	 
		
  @PostMapping(value="/register")
  public String register(@ModelAttribute("prReq") ProductRequest prReq, 
			Errors errors, HttpServletRequest request, Model model) throws Exception {
	  Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
	  System.out.println("상품 등록중입니다.");
	  
		  //errors 
	  	if(errors.hasErrors()) { 
	  		System.out.println("오류발생~");
	  		return Product_REGISTER_FORM; 
	  		}
	  	

	 // 이미지 업로드
	 String filename = uploadFile(prReq.getProductName(), prReq.getImage());		// webapp/upoad 밑에 이미지 저장		
	 		
		  Product pr = new Product(); 
		  pr.initPr(prReq, this.uploadDirLocal + filename);
		 
		  //sellerId 추가   
		  pr.setSellerId(userSession.getAccount().getUserId());
		  //pr.setBank(Product_REGISTER_FORM);
		  //DB에 추가
		  prSvc.addProduct(pr);

		return "redirect:" + Product_REGISTER_SEUCCESS; //redirect
	}
	
	@RequestMapping(value="/register/success", method = RequestMethod.GET)
	public String success(Model model) throws Exception {
		System.out.println("등록 완료");

		return Product_REGISTER_SEUCCESS_View;
	}	
	
	//update
	@GetMapping(value="/update")
	public String showUpdateForm(@RequestParam("productId") int productId, Model model) {
		System.out.println("수정폼 불러옴");
		System.out.println("수정폼에서 출력합니다 : " + productId);
		
		
		String imgPath = prSvc.getPr(productId).getImage();
		model.addAttribute("imgPath", imgPath);
		model.addAttribute("ProductId", productId);
		return Product_UPDATE_FORM;
	}
	
	@PostMapping(value="/update")
	public String update(@ModelAttribute("prReq") ProductRequest prReq,  HttpServletRequest request,
						@RequestParam("imgPath") String path, @RequestParam("isModify") String isModify,
						Model model) throws Exception {
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		System.out.println("ProductId : " + prReq.getProductId());
		String filePath;
		
		if (isModify.equals("true")) {
			String filename = uploadFile(prReq.getProductName(), prReq.getImage());
			filePath = this.uploadDirLocal + filename;
		}
		else
			filePath = path;
		
		 Product pr = new Product(); 
		 
		pr.initPr(prReq, filePath);
		pr.setSellerId(userSession.getAccount().getUserId());
		
		//update 
		prSvc.updateProduct(pr);
		
		return "redirect:" + PR_UPDATE_SEUCCESS + "?productId=" + prReq.getProductId();
	}

	private String uploadFile(String studentNumber, MultipartFile report) {
		String filename = UUID.randomUUID().toString() 
						+ "_" + report.getOriginalFilename();
		System.out.println(studentNumber + "가 업로드 한 파일: "	+ filename);
		File file = new File(this.uploadDir + filename);
		try {
			report.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return filename;
	}	
	
	//delete
	@GetMapping(value="/delete")
	public String delete(@RequestParam("productId") int productId) {
		System.out.println(productId + " 삭제됨");
		
		//delete
		prSvc.deleteProduct(productId);
		
		return "redirect:" + "/user/my/sale/list?sellerId=panda";
	}
	
	
	}
	


