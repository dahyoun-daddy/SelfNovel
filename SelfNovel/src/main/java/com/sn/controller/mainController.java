package com.sn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.controller.HomeController;

@Controller
public class mainController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value="main/main.do")
	public String main() {
		log.debug("===================");
		log.debug("========main=======");
		log.debug("===================");
		
		return "main/main_view";
	}
}
