package com.example.SOMusic.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.example.SOMusic.domain.GroupPurchase;
import com.example.SOMusic.domain.Login;
import com.example.SOMusic.service.ImgValidator;
import com.example.SOMusic.service.GPService;

@Controller
@RequestMapping("/gp")
@SessionAttributes("userSession")
public class GPController implements ApplicationContextAware {
	
	private static final String GP_REGISTER_FORM = "thyme/gp/register/GPRegisterForm";
	private static final String GP_REGISTER_SEUCCESS = "/gp/register/success";	// redirect : uri
	private static final String GP_REGISTER_SEUCCESS_View = "thyme/gp/register/GPRegisterSuccess";
	
	private static final String GP_UPDATE_FORM = "thyme/gp/update/GPUpdateForm";
	private static final String GP_UPDATE_SEUCCESS = "/user/my/gp/info";	// redirect : uri
	
	private static final String GP_DELETE = "/user/my/gp/list"; // 공구 삭제 후 공구 리스트로 다시 이동 uri
	
	private static final String LOGIN_FROM = "/user/loginForm";		// 로그인 폼으로 uri 이동
	
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
	}
	
	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}
	
	@Autowired // 이미지 유효성 검사
	private ImgValidator validator;
	public void setImgValidator(ImgValidator valitator) {
		this.validator = valitator;
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
	}
	
	// 공구 등록
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String showRegisterForm(HttpServletRequest request) {
		
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		if(userSession == null)		// 로그인 X -> 로그인 폼
			return "redirect:" + LOGIN_FROM;
		
		return GP_REGISTER_FORM;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request,
							@Valid @ModelAttribute("gpReq") GPRequest gpReq, Errors errors,
							@RequestParam("imgCheck") String imgCheck, BindingResult result,
							Model model) throws Exception {
		
		Login userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		
		validator.validate(imgCheck, result); // 이미지가 삽입되었는지 유효성 체크, MultipartFile에 서블릿이 기본값을 주입하기에 null 체크X
		
		System.out.println(gpReq);
		
		// 오류
		if(errors.hasErrors() && result.hasErrors()) {
			return GP_REGISTER_FORM;
		}
		
		// 이미지 업로드
		String filename = uploadFile(gpReq.getTitle(), gpReq.getImage());		// webapp/upoad 밑에 이미지 저장
				
		GroupPurchase gp = new GroupPurchase();
		gp.initGP(gpReq, this.uploadDirLocal + filename);
		gp.setSellerId(userSession.getAccount().getUserId());	// 세션에서 Account.userId 삽입

		gpSvc.insertGP(gp);
		
		return "redirect:" + GP_REGISTER_SEUCCESS;	// redirect로 넘기기
	}
	
	@RequestMapping(value="/register/success", method = RequestMethod.GET)
	public String success(Model model) throws Exception {
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
		// 오류
		if(errors.hasErrors()) {
			String imgPath = gpSvc.getGP(gpReq.getGpId()).getImage();
			model.addAttribute("imgPath", imgPath);
			
			return GP_UPDATE_FORM;
		}
		
		String filePath;
		if (isModify.equals("true")) {
			String filename = uploadFile(gpReq.getTitle(), gpReq.getImage());	// 이미지 수정된 경구
			filePath = this.uploadDirLocal + filename;
		}
		else
			filePath = path;	// 이미지 수정 X 경우
		
		GroupPurchase gp = new GroupPurchase();
		gp.initGP(gpReq, filePath);
		
		// 공구 수정
		gpSvc.updateGP(gp);
		
		return "redirect:" + GP_UPDATE_SEUCCESS + "?gpId=" + gpReq.getGpId();
	}
	
	// 등록한 공구 정보 삭제
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("gpId") int gpId) {
		
		// 삭제
		gpSvc.deleteGP(gpId);
			
		return "redirect:" + GP_DELETE;
	}
	
}
