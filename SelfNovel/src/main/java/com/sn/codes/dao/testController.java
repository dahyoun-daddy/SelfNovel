package com.sn.codes.dao;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sn.codes.domain.CodesVO;

@Controller
public class testController {
	//log class
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	CodesDao dao;
	
	@RequestMapping(value="/tc.do" , method = RequestMethod.GET)
	public String main(HttpServletRequest request) {
		CodesVO dto = new CodesVO("01","02","03","04",1,"05",1);
		log.debug("0================================================");
		log.debug("0================================================");
		dao.do_save(dto);
				
		return "home";
	}
}
