package com.example.SOMusic.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.service.GPService;

@Controller
@RequestMapping("/gp")
public class GPController implements ApplicationContextAware {
	
	private static final String GP_REGISTER_FORM = "thyme/gp/register/GPRegisterForm";
	private static final String GP_REGISTER_SEUCCESS = "/gp/register/success";	// redirect : uri
	private static final String GP_REGISTER_SEUCCESS_View = "thyme/gp/register/GPRegisterSuccess";
	
	private static final String GP_UPDATE_FORM = "thyme/gp/update/GPUpdateForm";
	private static final String GP_UPDATE_SEUCCESS = "/user/my/gp/info";	// redirect : uri
	
	// 이미지 업로드를 위해
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
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}
	
	@ModelAttribute("gpReq")
	public GPRequest formBacking(HttpServletRequest request) throws Exception {
		String gpId = request.getParameter("gpId");
		GPRequest gpReq = new GPRequest();
		if (gpId == null)
			return 	gpReq;		// 등록
		else {
			gpReq.initGpReq(gpSvc.getGP(Integer.parseInt(gpId)));
			return gpReq;		// 수정
		}
//		return new GPRequest();
	}
	
	// 공구 등록
//	@RequestMapping(value="/register", method = RequestMethod.GET)
	@GetMapping("/register")
	public String showRegisterForm() {
		return GP_REGISTER_FORM;
	}
	
//	@RequestMapping(value="/register", method = RequestMethod.POST)
	@PostMapping("/regiser")
	public String register( @Valid @ModelAttribute("gpReq") GPRequest gpReq, Errors errors,
							Model model) throws Exception {
		
		System.out.println("GP 등록 : " + gpReq);

		// 오류
		if(errors.hasErrors()) {
			return GP_REGISTER_FORM;
		}
		
		// 이미지 업로드
		String filename = uploadFile(gpReq.getTitle(), gpReq.getImage());		// webapp/upoad 밑에 이미지 저장
				
		GroupPurchase gp = new GroupPurchase();
		gp.initGP(gpReq, this.uploadDirLocal + filename);		

		gp.setSellerId("jinju");	// 임의로 설정, sellerId 삽입
		gpSvc.insertGP(gp);
		
		return "redirect:" + GP_REGISTER_SEUCCESS;	// redirect로 넘기기
	}
	
	@RequestMapping(value="/register/success", method = RequestMethod.GET)
	public String success(Model model) throws Exception {
		System.out.println("GP 등록 완료");
		
		return GP_REGISTER_SEUCCESS_View;
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

	
	// 등록한 공구 정보 수정
	
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public String showUpdateForm(@RequestParam("gpId") int gpId, Model model) {
		
		String imgPath = gpSvc.getGP(gpId).getImage();
		model.addAttribute("imgPath", imgPath);
		
		return GP_UPDATE_FORM;
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("gpReq") GPRequest gpReq, Errors errors,
						@RequestParam("imgPath") String path, @RequestParam("isModify") String isModify,
						Model model) throws Exception {
		System.out.println("GP 수정 : " + gpReq);
		System.out.println("GP 이미지 경로 : " + path + "\t변경 : " + isModify);
		
		// 오류
		if(errors.hasErrors()) {
			String imgPath = gpSvc.getGP(gpReq.getGpId()).getImage();
			model.addAttribute("imgPath", imgPath);
			
			return GP_UPDATE_FORM;
		}
		
		String filePath;
		
		if (isModify.equals("true")) {
			String filename = uploadFile(gpReq.getTitle(), gpReq.getImage());
			filePath = this.uploadDirLocal + filename;
		}
		else
			filePath = path;
		
		GroupPurchase gp = new GroupPurchase();
		gp.initGP(gpReq, filePath);
		
		// 공구 수정
		gp.setSellerId("jinju");		// 임의 설정
		gpSvc.updateGP(gp);
		
		return "redirect:" + GP_UPDATE_SEUCCESS + "?gpId=" + gpReq.getGpId();
	}
	
	// 등록한 공구 정보 삭제
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("gpId") int gpId) {
		
		System.out.println("삭제 : " + gpId);
		
		// 삭제
		gpSvc.deleteGP(gpId);
			
		return "redirect:" + "/user/my/gp/register/list?sellerId=jinju";
	}
	
}
