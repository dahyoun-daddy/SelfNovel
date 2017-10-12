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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sn.codes.dao.CodesDao;
import com.sn.codes.domain.CodesVO;
import com.sn.common.StringUtil;
import com.sn.user.domain.UserVO;
import com.sn.user.service.UserSvc;

/**
 * ManagerController 
 * detail : 유저관리 페이지 컨트롤러
 * 최초작성: 2017-09-28
 * 최종수정: 2017-09-29
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
	@Autowired
	private CodesDao codesDao;
	
	/***********************************************/
	//servlet
	/***********************************************/
	
	/**
	 * do_search
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/mypage/manager/manager_user_list.do", method = RequestMethod.GET)
	public ModelAndView do_search(HttpServletRequest req) {
		ModelAndView modelAndView = new ModelAndView();
		
		String PAGE_SIZE = StringUtil.nvl(req.getParameter("PAGE_SIZE"), "10");
		String PAGE_NUM = StringUtil.nvl(req.getParameter("PAGE_NUM"), "1");
		String SEARCH_DIV = StringUtil.nvl(req.getParameter("SEARCH_DIV"), "");
		String SEARCH_WORD = StringUtil.nvl(req.getParameter("SEARCH_WORD"), "");
		String ORDER_DIV = StringUtil.nvl(req.getParameter("ORDER_DIV"), "");
		String totalCnt = StringUtil.nvl(req.getParameter("totalCnt"), "0");
		
		UserVO inVO = new UserVO();
		Hashtable<String,String> param = new Hashtable<String, String>();
		param.put("PAGE_SIZE", PAGE_SIZE);
		param.put("PAGE_NUM", PAGE_NUM);
		param.put("SEARCH_DIV", SEARCH_DIV);
		param.put("SEARCH_WORD", SEARCH_WORD);
		param.put("ORDER_DIV", ORDER_DIV);
		param.put("totalCnt", totalCnt);
		inVO.setParam(param);
		log.debug(param.toString());
		
		//userList
		List<?> list = userSvc.do_search(inVO);
		if(list.size()>0) {
			UserVO userVO = (UserVO) list.get(0);
			totalCnt = String.valueOf(userVO.getTotalNo());
			log.debug("totalCnt1: "+totalCnt);
		}
		
		//codeList
		CodesVO inVoCodes = new CodesVO();
		inVoCodes.setMst_cd_id("C003");	
		List<?> codeList = codesDao.do_search(inVoCodes);

		modelAndView.addObject("userList",list);
		modelAndView.addObject("codeList",codeList);
		modelAndView.addObject("PAGE_SIZE",PAGE_SIZE);
		modelAndView.addObject("PAGE_NUM",PAGE_NUM);
		modelAndView.addObject("SEARCH_DIV",SEARCH_DIV);
		modelAndView.addObject("SEARCH_WORD",SEARCH_WORD);
		modelAndView.addObject("ORDER_DIV",ORDER_DIV);
		modelAndView.addObject("totalCnt",totalCnt); //나중에 리스트로부터 토탈카운트 받아서 전달해야함		
		modelAndView.setViewName("/mypage/manager/manager_user_list");
		return modelAndView;
	}
	
	/**
	 * do_delete
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/mypage/manager/do_delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String do_delete(HttpServletRequest req) {
		String data = StringUtil.nvl(req.getParameter("data"), "");				
		Gson gson = new Gson();
		List<UserVO> list = gson.fromJson(data, new TypeToken<List<UserVO>>(){}.getType());	
		
		log.debug("list size-controller: "+list.toString());
		
		return String.valueOf(userSvc.do_deleteTx(list));
	}
	
	/**
	 * do_delete
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/mypage/manager/do_save.do", method = RequestMethod.POST)
	public String do_save(HttpServletRequest req) {
		String u_id = StringUtil.nvl(req.getParameter("u_id"), "");
		String u_password = StringUtil.nvl(req.getParameter("u_password"), "");
		String u_name = StringUtil.nvl(req.getParameter("u_name"), "");
		
		UserVO userVO = new UserVO();
		userVO.setU_id(u_id);
		userVO.setU_password(u_password);
		userVO.setU_name(u_name);
		userVO.setU_level(0);
		
		int flag = userSvc.do_save(userVO);
		log.debug("======================================");
		log.debug("do_save flag: "+flag);
		log.debug("======================================");
		
		return "redirect:manager_user_list.do";
	}
}
