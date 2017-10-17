package com.sn.controller;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sist.controller.HomeController;
import com.sn.common.StringUtil;
import com.sn.expert.domain.ExpertVO;
import com.sn.expert.service.ExpertSvc;
import com.sn.resume.domain.RsmVO;
import com.sn.resume.service.RsmSvc;

@Controller
public class mainController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private ExpertSvc expertSvc;
	
	@Autowired
	private RsmSvc rsmSvc;
	
	@RequestMapping(value="main/main.do")
	public ModelAndView main() {
		log.debug("===================");
		log.debug("========main=======");
		log.debug("===================");
		
		List<ExpertVO> rank_list = (List<ExpertVO>) expertSvc.do_searchRank();
		ModelAndView modelAndView =new ModelAndView();
		modelAndView.addObject("rank_list",rank_list );
		modelAndView.setViewName("main/main_view");
		
		return modelAndView;
	}
	
	@RequestMapping(value="main/do_search.do")
	public ModelAndView do_search(HttpServletRequest req) {
		log.debug("===================");
		log.debug("========main=======");
		log.debug("===================");
		
		String SEARCH_WORD = StringUtil.nvl(req.getParameter("SEARCH_WORD_MAIN"), "");
		Hashtable<String, String> expParam = new Hashtable<String, String>();
		expParam.put("pageSize", "10");
		expParam.put("pageNo", "1");
		expParam.put("searchDiv", "u_reg_dt");
		expParam.put("searchCategory", "");
		if(SEARCH_WORD.equals("")) {
			expParam.put("searchWord", "");
		}else {
			expParam.put("searchWord", "AND instr(exp_title,'" + SEARCH_WORD +"',1,1) > 0");
		}
		
		Hashtable<String, String> rsmParam = new Hashtable<String, String>();
		rsmParam.put("pageSize", "10");
		rsmParam.put("pageNo", "1");
		rsmParam.put("searchDiv", "10");
		rsmParam.put("searchCat", "");	
		rsmParam.put("searchWord", SEARCH_WORD);

		
		
		ExpertVO expVO = new ExpertVO();
		RsmVO	 rsmVO = new RsmVO();
		expVO.setParam(expParam);
		rsmVO.setParam(rsmParam);
		
		List<?> expList = expertSvc.do_search(expVO);
		List<?> rsmList = rsmSvc.do_search(rsmVO);
		
		ModelAndView modelAndView =new ModelAndView();
		modelAndView.addObject("searchWord",SEARCH_WORD);
		modelAndView.addObject("expList",expList );
		modelAndView.addObject("rsmList",rsmList );
		modelAndView.setViewName("main/main_list");
		
		return modelAndView;
	}
}
