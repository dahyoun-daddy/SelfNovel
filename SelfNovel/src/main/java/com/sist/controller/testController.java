package com.sist.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sn.codes.domain.CodesVO;
import com.sn.img.dao.ImgDao;
import com.sn.img.domain.ImgVO;

@Controller
public class testController {
	@Autowired
	ImgDao imgDao;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/img.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		ImgVO dto = new ImgVO();
		imgDao.do_save(dto); 
		
		return "home";
	}
}
