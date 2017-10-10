package com.sn.controller;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sn.common.StringUtil;

/**
 * rsmTestController 
 * detail : dao테스트하고 싶은데 main에서 autowired로 추가하면 자꾸 null에러떠서 걍 만든 컨트롤러
 * 최초작성: 2017-09-21
 * @author @author MinSeok <dev.edwinner@gmail.com>
 *
 */
@Controller
public class rsmTestController {
	private static final Logger log = LoggerFactory.getLogger(rsmTestController.class);
	
	/**
	 * img Test resume_view
	 */
	@RequestMapping(value="testRsm.do")
	public String resumeForm() {
		log.debug("===================");
		log.debug("=======resumeview======");
		log.debug("===================");
		
		return "viewTest/resumeTest";
	}
	
	/**
	 * img Test resume_view
	 */
	@RequestMapping(value="rsmParamTest.do", method=RequestMethod.POST)
	public String testSave(HttpServletRequest req) {
		//Hashtable<String,String> ht = StringUtil.createParam(req);
		//ht.toString();
		log.debug("===================");
		String[] titlelist = (String[]) req.getParameterValues("title");
		String[] contentlist = (String[]) req.getParameterValues("content");
		for(int i=0;i<contentlist.length;i++) {
			log.debug(titlelist[i]);
			log.debug(contentlist[i]);
		}
		
		
		return "redirect:testRsm.do";
	}
	
	/**
	 * img Test resume_view
	 */
	@RequestMapping(value="changeTest.do")
	public String resumeForm2() {
		log.debug("===================");
		log.debug("=======resumeview======");
		log.debug("===================");
		
		return "viewTest/changeTest";
	}
}
