package com.example.SOMusic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.SOMusic.service.GPService;

@Controller
@RequestMapping("/user/my/wish")
public class WishGPController {
	
	private static final String WISH_GP_LIST = "user/my/gp/MyWishGPList";
	
	@Autowired
	private GPService gpSvc;
	public void setGPService(GPService gpSvc) {
		this.gpSvc= gpSvc;
	}

	@RequestMapping(value="/gp/list", method = RequestMethod.GET)
	public String gpWishList(Model model) throws Exception {

		
		return WISH_GP_LIST;
	}
	
}
