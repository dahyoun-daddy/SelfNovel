package com.sn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sist.controller.HomeController;
import com.sn.expert.domain.ExpertVO;
import com.sn.expert.service.ExpertSvc;

@Controller
public class mainController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private ExpertSvc expertSvc;
	
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
}
