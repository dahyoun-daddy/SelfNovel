package com.sn.resume.controller;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sn.common.StringUtil;
import com.sn.resume.dao.RsmDao;
import com.sn.resume.domain.RsmVO;

@Controller
public class RsmController {
	private Logger log = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	RsmDao RsmDao;	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value="resume/do_search.do")
	public ModelAndView resumeList(HttpServletRequest req) {
		log.debug("===== RsmDaocontroller.do_search =====");
		log.debug("req : " + req.toString());
		log.debug("검색어 : " + req.getParameter("searchWord"));
		log.debug("======================================");			
		
		//request로부터 parameter load
		String p_pageSize = StringUtil.nvl(req.getParameter("page_size"),"10");
		String p_pageNo  = StringUtil.nvl(req.getParameter("page_num"),"1");
		String p_searchDiv = StringUtil.nvl(req.getParameter("searchDiv"),"");
		String p_searchWord = StringUtil.nvl(req.getParameter("searchWord"),"");
		String p_searchCat = StringUtil.nvl(req.getParameter("search_category"),"");
		
		Hashtable<String, String> searchParam = new Hashtable<String, String>();
		
		//searchParam에 request로부터 읽은 파라미터 set
		searchParam.put("pageSize", p_pageSize);
		searchParam.put("pageNo", p_pageNo);
		searchParam.put("searchDiv", p_searchDiv);
		searchParam.put("searchWord", p_searchWord);
		searchParam.put("searchCat", p_searchCat);
		
		log.debug("=========================");
		log.debug("p_searchWord" + p_searchWord);
		
		//Vo생성 후, searchParam set
		RsmVO inRsmVo = new RsmVO();
		inRsmVo.setParam(searchParam);
		
		//list객체 생성 후, inRsmVo 주입
		List<RsmVO> list = (List<RsmVO>) RsmDao.do_search(inRsmVo);
		
		//리턴받은 list에서 총 글수 값을 가져와 set
		int totalCnt = 0;
		if(list !=null && list.size()>0)totalCnt = list.get(0).getTotalNo();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", list );		
		modelAndView.addObject("totalCnt", totalCnt);
		modelAndView.addObject("searchCat", p_searchCat);
		modelAndView.setViewName("resume/resume_list");
		
		return modelAndView;
	}
}
