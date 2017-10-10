package com.sn.resume.controller;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sn.common.StringUtil;
import com.sn.resume.dao.RsmDao;
import com.sn.resume.domain.ItmVO;
import com.sn.resume.domain.RsmVO;
import com.sn.resume.service.ItmSvc;
import com.sn.resume.service.RsmSvc;
import com.sn.resume.service.RsmSvcImpl;


/**
 * RsmController
 * detail : 자소서 컨트롤러
 * 최종수정일 : 2017_09_27
 * @author MinSeok <dev.edwinner@gmail.com>
 *
 */
@Controller
public class RsmController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	RsmSvc rsmSvc;
	
	@Autowired
	ItmSvc itmSvc;
	
	/**
	 * resumeList
	 * detail : 자소서 게시판 및 검색 기능
	 * 최종수정일 : 2017_09_27
	 * @param req
	 * @return modelAndView(list, totalCnt, searchWord, searchDiv, searchCat)
	 * 		   viewName("resume/resume_list); 
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
		
		//Vo생성 후, searchParam set
		RsmVO inRsmVo = new RsmVO();
		inRsmVo.setParam(searchParam);
		
		//list객체 생성 후, inRsmVo 주입
		List<RsmVO> list = (List<RsmVO>) this.rsmSvc.do_search(inRsmVo);
		
		//리턴받은 list에서 총 글수 값을 가져와 set
		int totalCnt = 0;
		if(list !=null && list.size()>0)totalCnt = list.get(0).getTotalNo();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", list );		
		modelAndView.addObject("totalCnt", totalCnt);
		modelAndView.addObject("searchWord", p_searchWord);
		modelAndView.addObject("searchDiv", p_searchDiv);
		modelAndView.addObject("searchCat", p_searchCat);
		modelAndView.setViewName("resume/resume_list");
		
		return modelAndView;
	}	
	
	/**
	 * resumeModify
	 * detail : 수정폼으로 이동
	 * @param req
	 * @return ModelAndView
	 */
	@RequestMapping(value="resume/modifyView")
	public ModelAndView resumeModify(HttpServletRequest req) {
		log.debug("===== RsmDaocontroller.modifyView =======");
		log.debug("req : " + req.toString());
		log.debug("=========================================");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("resume/resume_mod");
		
		return modelAndView;
	}
	
	/**
	 * resumeView
	 * detail : 상세조회
	 * @param req
	 * @return ModelAndView
	 */
	@RequestMapping(value="resume/do_searchOne.do")
	public ModelAndView resumeView(HttpServletRequest req) {
		log.debug("===== RsmDaocontroller.do_searchOne =====");
		log.debug("req : " + req.toString());		
		log.debug("rsm_id : " + req.getParameter("rsm_id"));
		log.debug("=========================================");
		
		String rsm_id = req.getParameter("rsm_id");
		
		//Vo생성 후, rsm_id set
		RsmVO inRsmVO = new RsmVO();
		inRsmVO.setRsm_id(rsm_id);
		
		//RsmSvc를 통해 해당 자기소개서 호출
		RsmVO resultVO = (RsmVO) this.rsmSvc.do_searchOne(inRsmVO);		
		log.debug("result : " + resultVO.toString());
		
		//itmVO에 rsm_id를 set
		ItmVO itmVO = new ItmVO();
		itmVO.setRsm_id(rsm_id);
		
		//ItmSvc를 통해 해당 자기소개서의 항목들 호출
		List<ItmVO> itmList = (List<ItmVO>) itmSvc.do_search(itmVO);
		//HashMap<String, String> sizeMap = itmSvc.do_search(itmVO);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("resume/resume_view");
		modelAndView.addObject("rsmVO", resultVO);		
		modelAndView.addObject("itmList", itmList);
		
		return modelAndView;		
	}	
	
	/**
	 * do_searchChild
	 * detail : 상세조회
	 * @param req
	 * @return ModelAndView
	 */
	@RequestMapping(value="resume/do_search_child.do")
	@ResponseBody
	public List<ItmVO> do_searchChild(HttpServletRequest req) {
		log.debug("===== RsmDaocontroller.do_search_child =====");
		log.debug("req : " + req.toString());		
		log.debug("rsm_id : " + req.getParameter("rsm_id"));
		log.debug("============================================");
		
		String itm_prd_id = req.getParameter("itm_prd_id");
		
		//itmVO에 rsm_id를 set
		ItmVO itmVO = new ItmVO();
		itmVO.setItm_prd_id(itm_prd_id);
		
		//ItmSvc를 통해 해당 자기소개서의 항목들 호출
		List<ItmVO> itmList = (List<ItmVO>) itmSvc.do_search_child(itmVO);
		
		return itmList;
	}
	
	/**
	 * doSaveItm
	 * detail : 첨삭 저장 메소드
	 * 최초작성   : 2017-09-28	
	 * @param req
	 * @return flag(1: 성공 other: 실패)
	 */
	@RequestMapping(value="resume/do_save_edit.do")
	@ResponseBody
	public int doSaveEdit(HttpServletRequest req) {
		log.debug("===== RsmDaocontroller.do_save_edit =====");
		log.debug("req : " + req.toString());
		log.debug("=========================================");
		
		//Vo 생성 후 request에서 받은 parameter를 세팅한다.
		ItmVO inItmVo = new ItmVO();
		inItmVo.setRsm_id(req.getParameter("rsm_id"));
		inItmVo.setItm_prd_id(req.getParameter("itm_prd_id"));
		inItmVo.setItm_title(req.getParameter("itm_title"));
		inItmVo.setItm_content(req.getParameter("itm_content"));
		inItmVo.setU_id(req.getParameter("u_id"));
		
		//itmSvc를 통해 do_save메소드 호출
		int flag = itmSvc.do_save_edit(inItmVo);
		
		return flag;	
	}
	
	@RequestMapping(value="resume/do_update_item.do")
	@ResponseBody
	public int doUpdateItem(HttpServletRequest req) {
		log.debug("===== RsmDaocontroller.do_update_item =====");
		log.debug("req : " + req.toString());
		log.debug("===========================================");
		
		ItmVO inItmVo = new ItmVO();
		inItmVo.setItm_form_id(req.getParameter("itm_form_id"));		
		inItmVo.setItm_title(req.getParameter("itm_title"));
		inItmVo.setItm_content(req.getParameter("itm_content"));		
		
		//itmSvc를 통해 do_save메소드 호출
		int flag = itmSvc.do_update(inItmVo);
		
		return flag;
	}
}
