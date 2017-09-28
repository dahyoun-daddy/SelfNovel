package com.sn.user.controller;

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
		modelAndView.addObject("userList",userSvc.do_search(new UserVO()));
		modelAndView.addObject("page_size","10");
		modelAndView.addObject("page_num","1");
		modelAndView.addObject("totalCnt","");
		modelAndView.setViewName("/mypage/manager/manager_user_list");
		return modelAndView;
	}
	
}
