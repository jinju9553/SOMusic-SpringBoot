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
	private static final String GP_REGISTER_SEUCCESS = "/gp/register/success";
	private static final String GP_REGISTER_SEUCCESS_VIEW = "thyme/gp/register/GPRegisterSuccess";
	
	private static final String GP_UPDATE_FORM = "thyme/gp/update/GPUpdateForm";
	private static final String GP_UPDATE_SEUCCESS = "/user/my/gp/info";
	
	private static final String GP_DELETE = "/user/my/gp/list";
	
	private static final String LOGIN_FORM = "/user/loginForm";
	
	private Login userSession;
	
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
	
	@Autowired
	private ImgValidator validator;
	public void setImgValidator(ImgValidator valitator) {
		this.validator = valitator;
	}
	
	@SuppressWarnings("finally")
	@ModelAttribute("gpReq")
	public GPRequest formBacking(HttpServletRequest request) throws Exception {
		
		userSession = (Login) WebUtils.getSessionAttribute(request, "userSession");
		
		String gpId = request.getParameter("gpId");
		GPRequest gpReq = new GPRequest();
		
		try {
			gpReq.initGpReq(gpSvc.getGP(Integer.parseInt(gpId)));
		} finally {
			return gpReq;
		}
	}
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String showRegisterForm(HttpServletRequest request) {
		
		if(isLoggedIn(userSession))
			return "redirect:" + LOGIN_FORM;
		
		return GP_REGISTER_FORM;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request,
							@Valid @ModelAttribute("gpReq") GPRequest gpReq, Errors errors,
							@RequestParam("imgCheck") String imgCheck, BindingResult result,
							Model model) throws Exception {
		
		validator.validate(imgCheck, result);
		
		if(existFormAndImageError(errors, result)) {
			return GP_REGISTER_FORM;
		}
		
		String filePath = getFilePath(gpReq);
				
		GroupPurchase gp = makeGP(gpReq, filePath);
		gp.setSellerId(userSession.getAccount().getUserId());

		gpSvc.insertGP(gp);
		
		return "redirect:" + GP_REGISTER_SEUCCESS;
	}
	
	@RequestMapping(value="/register/success", method = RequestMethod.GET)
	public String success(Model model) throws Exception {
		return GP_REGISTER_SEUCCESS_VIEW;
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

	@RequestMapping(value="/update", method = RequestMethod.GET)
	public String showUpdateForm(@RequestParam("gpId") int gpId, Model model) {
		
		String imgPath = gpSvc.getGP(gpId).getImage();
		model.addAttribute("imgPath", imgPath);
		
		return GP_UPDATE_FORM;
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("gpReq") GPRequest gpReq, Errors errors,
						@RequestParam("imgPath") String filePath, @RequestParam("isModify") String isModify,
						Model model) throws Exception {

		if(existFormError(errors)) {
			int gpId = gpReq.getGpId();
			String imgPath = gpSvc.getGP(gpId).getImage();
			
			model.addAttribute("imgPath", imgPath);
			
			return GP_UPDATE_FORM;
		}
		
		if (isModify.equals("true"))
			filePath = getFilePath(gpReq);
		
		GroupPurchase gp = makeGP(gpReq, filePath);
		
		gpSvc.updateGP(gp);
		
		return "redirect:" + GP_UPDATE_SEUCCESS + "?gpId=" + gpReq.getGpId();
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("gpId") int gpId) {

		gpSvc.deleteGP(gpId);
			
		return "redirect:" + GP_DELETE;
	}
	
	private GroupPurchase makeGP(GPRequest gpReq, String filePath) {
		GroupPurchase gp = new GroupPurchase();
		gp.initGP(gpReq, filePath);
		
		return gp;
	}
	
	private String getFilePath(GPRequest gpReq) {
		String fileName = uploadFile(gpReq.getTitle(), gpReq.getImage());
		String filePath = this.uploadDirLocal + fileName;
		
		return filePath;
	}
	
	private static boolean isLoggedIn(Login userSession) {
        return (userSession != null);
    }
	
	private boolean existFormAndImageError(Errors errors, BindingResult result) {
		return errors.hasErrors() && result.hasErrors();
	}
	
	private boolean existFormError(Errors errors) {
		return errors.hasErrors();
	}
	
}
