package com.sn.user.controller;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sn.common.StringUtil;
import com.sn.user.domain.UserVO;
import com.sn.user.service.UserSvc;

/**
 * ManagerController 
 * detail : 유저관리 페이지 컨트롤러
 * 최초작성: 2017-09-28
 * 최종수정: 2017-09-28
 * @author @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
@Controller
public class ManagerController {
	/***********************************************/
	//field
	/***********************************************/
	private static Logger log = LoggerFactory.getLogger(ManagerController.class);
	
	@Autowired
	private UserSvc userSvc;
	
	/***********************************************/
	//servlet
	/***********************************************/
	
	@RequestMapping(value = "/mypage/manager/manager_user_list.do", method = RequestMethod.GET)
	public ModelAndView do_search(HttpServletRequest req) {
		ModelAndView modelAndView = new ModelAndView();
		
		String PAGE_SIZE = StringUtil.nvl(req.getParameter("PAGE_SIZE"), "10");
		String PAGE_NUM = StringUtil.nvl(req.getParameter("PAGE_NUM"), "1");
		String SEARCH_DIV = StringUtil.nvl(req.getParameter("SEARCH_DIV"), "");
		String SEARCH_WORD = StringUtil.nvl(req.getParameter("SEARCH_WORD"), "");
		String totalCnt = StringUtil.nvl(req.getParameter("totalCnt"), "0");
		
		UserVO inVO = new UserVO();
		Hashtable<String,String> param = new Hashtable<String, String>();
		param.put("PAGE_SIZE", PAGE_SIZE);
		param.put("PAGE_NUM", PAGE_NUM);
		param.put("SEARCH_DIV", SEARCH_DIV);
		param.put("SEARCH_WORD", SEARCH_WORD);
		param.put("totalCnt", totalCnt);
		inVO.setParam(param);
		
		List<?> list = userSvc.do_search(inVO);
		if(list.size()>0) {
			UserVO userVO = (UserVO) list.get(0);
			totalCnt = String.valueOf(userVO.getTotalNo());
			log.debug("totalCnt1: "+totalCnt);
		}
		log.debug("totalCnt2: "+totalCnt);
		modelAndView.addObject("userList",list);
		modelAndView.addObject("PAGE_SIZE",PAGE_SIZE);
		modelAndView.addObject("PAGE_NUM",PAGE_NUM);
		modelAndView.addObject("SEARCH_DIV",SEARCH_DIV);
		modelAndView.addObject("SEARCH_WORD",SEARCH_WORD);
		modelAndView.addObject("totalCnt",totalCnt); //나중에 리스트로부터 토탈카운트 받아서 전달해야함		
		modelAndView.setViewName("/mypage/manager/manager_user_list");
		return modelAndView;
	}
	
}
