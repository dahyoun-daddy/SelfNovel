package com.sn.resume.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sn.resume.service.RsmSvc;

@Controller
public class RsmController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	RsmSvc rsmSvc;	
}
