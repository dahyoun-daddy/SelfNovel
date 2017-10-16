package com.sn.expert.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sn.codes.dao.CodesDao;
import com.sn.codes.domain.CodesVO;
import com.sn.common.StringUtil;
import com.sn.expert.domain.ExpertVO;
import com.sn.expert.service.ExpertSvc;
import com.sn.resume.domain.ItmVO;
import com.sn.resume.domain.RsmVO;
import com.sn.user.controller.UserController;
import com.sn.user.domain.UserVO;
import com.sn.user.service.UserSvc;

@Controller
public class ExpertController {
private static Logger log = LoggerFactory.getLogger(ExpertController.class);
	
	@Autowired
	private ExpertSvc expertSvc;
	
	@Autowired
	private UserSvc userSvc;
	
	@Autowired
	private CodesDao codesDao;
	
	@RequestMapping(value="expert/do_order.do")
	public void do_order(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Hashtable<String, String> param = new Hashtable<String, String>();
		RsmVO rsmVO = new RsmVO();
		
		String[] itm_title_arr = req.getParameter("itm_titles").split("\\\\");
		String[] itm_content_arr = req.getParameter("itm_contents").split("\\\\");
		System.out.println("asdf33: " + itm_title_arr.toString());
		String itm_inserts ="";
		String itm_contents = "";
		
		for(int i=0; i<itm_title_arr.length; i++) {
			if(i==0 || itm_title_arr.length == 1) {	// 처음
				if(itm_title_arr.length == 1) {	// 항목이 하나밖에 없으면
					itm_contents += "'"+itm_title_arr[i]+"' itm_title"+(i+1) + ",\n";
					itm_contents += "'"+itm_content_arr[i]+"' itm_content"+(i+1) + "\n";
				} else {
					itm_contents += "'"+itm_title_arr[i]+"' itm_title"+(i+1) + ",\n";
					itm_contents += "'"+itm_content_arr[i]+"' itm_content"+(i+1) + ",\n";
				}
				itm_inserts += "INTO item (rsm_id, itm_form_id, itm_prd_id, itm_title, itm_content, u_id, itm_reg_dt, itm_seq, itm_use_yn)\n";
				itm_inserts += "VALUES (resume_seq.currval, item_seq.nextval, resume_seq.currval, itm_title"+(i+1)+", itm_content"+(i+1)+", u_id, sysdate, resume_seq.currval+"+i+", rsm_use_yn)\n";
			}else if(i == itm_title_arr.length-1) {	// 마지막 경우
				itm_inserts += "INTO item (rsm_id, itm_form_id, itm_prd_id, itm_title, itm_content, u_id, itm_reg_dt, itm_seq, itm_use_yn)\n";
				itm_inserts += "VALUES (resume_seq.currval, item_seq.currval+" + i + ", resume_seq.currval, itm_title"+(i+1)+", itm_content"+(i+1)+", u_id, sysdate, resume_seq.currval+"+i+", rsm_use_yn)\n";
				itm_contents += "'"+itm_title_arr[i]+"' itm_title"+(i+1) + ",\n";
				itm_contents += "'"+itm_content_arr[i]+"' itm_content"+(i+1) + "\n";
				break;
			}else {	// 이외
				itm_inserts += "INTO item (rsm_id, itm_form_id, itm_prd_id, itm_title, itm_content, u_id, itm_reg_dt, itm_seq, itm_use_yn)\n";
				itm_inserts += "VALUES (resume_seq.currval, item_seq.currval+" + i + ", resume_seq.currval, itm_title"+(i+1)+", itm_content"+(i+1)+", u_id, sysdate, resume_seq.currval+"+i+", rsm_use_yn)\n";
				itm_contents += "'"+itm_title_arr[i]+"' itm_title"+(i+1) + ",\n";
				itm_contents += "'"+itm_content_arr[i]+"' itm_content"+(i+1) + ",\n";
			}
		}
		
		param.put("itm_inserts", itm_inserts);
		param.put("itm_contents", itm_contents);
		rsmVO.setParam(param);
		rsmVO.setU_id(req.getParameter("u_id"));
		rsmVO.setRsm_title(req.getParameter("rsm_title"));
		rsmVO.setRsm_content(req.getParameter("rsm_content"));
		
		if(expertSvc.do_saveOrder(rsmVO) > 0) {
			for(int i=0; i<itm_title_arr.length; i++) {
				expertSvc.do_nextSeq();
			}
			res.getWriter().write("success");
		} else {
			res.getWriter().write("fail");
		}
	}
	
	@RequestMapping(value="expert/do_detail_list.do")
	public ModelAndView do_detail_list(HttpServletRequest req) {
		ExpertVO expertVO = new ExpertVO();
		expertVO.setU_id(req.getParameter("exp_id"));
		expertVO = (ExpertVO) expertSvc.do_chkId(expertVO);
		
		List<ItmVO> itmVO = (List<ItmVO>) expertSvc.do_searchDetail_itm(expertVO);
		List<RsmVO> rsmList = new ArrayList<RsmVO>();
		String temp = "";
		
		for(int i=0; i<itmVO.size(); i++) {
			RsmVO rsmVO = new RsmVO(); 
			if(!temp.equals(itmVO.get(i).getRsm_id())) {
				temp = itmVO.get(i).getRsm_id();
				rsmVO = (RsmVO) expertSvc.do_searchDetail_rsm(itmVO.get(i));
				rsmList.add(rsmVO);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("RsmList", rsmList);
		modelAndView.addObject("u_name", expertVO.getU_name());
		modelAndView.addObject("exp_id", expertVO.getU_id());
		modelAndView.addObject("exp_title", expertVO.getExp_title());
		modelAndView.addObject("exp_profile", expertVO.getExp_profile());
		modelAndView.addObject("dtl_cd_nm", expertVO.getDtl_cd_nm());
		modelAndView.addObject("exp_price", expertVO.getExp_price());
		modelAndView.setViewName("expert/expert_detail");
		
		return modelAndView;
	}
	
	@RequestMapping(value="expert/do_detail.do", method = RequestMethod.POST)
	public void do_detail(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ItmVO itmVO = new ItmVO();
		RsmVO rsmVO = new RsmVO();
		
		log.debug("asdf: " + req.getParameter("rsm_id"));
		
		itmVO.setRsm_id(req.getParameter("rsm_id"));
		rsmVO = (RsmVO) expertSvc.do_searchDetail_rsm(itmVO);
		String u_ids = "(u_id='"+ req.getParameter("u_id")
			+ "' OR u_id='" + rsmVO.getU_id() + "')";
		
		Hashtable<String, String> idParam = new Hashtable<String, String>();
		idParam.put("u_ids", u_ids);
		itmVO.setParam(idParam);
		
		List<ItmVO> itmList = (List<ItmVO>) expertSvc.do_searchDetail(itmVO);
		
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(new Gson().toJson(itmList));
	}
	
	@RequestMapping(value="expert/do_search.do")
	public ModelAndView do_search(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("utf-8");
		
		ExpertVO VO = new ExpertVO();	 
		Hashtable<String, String> 
		
		searchParam = new Hashtable<String, String>();//검색조건
		String p_pageSize = StringUtil.nvl(req.getParameter("page_size"),"16");
		String p_pageNo  = StringUtil.nvl(req.getParameter("page_num"),"1");
		String p_searchDiv = StringUtil.nvl(req.getParameter("searchDiv"),"");
		String p_searchCategory = StringUtil.nvl(req.getParameter("searchCategory"),"");
		String p_searchWord = StringUtil.nvl(req.getParameter("searchWord"),"");
		
		if(!p_searchWord.equals("") && p_searchWord != null) {
			searchParam.put("searchWord","AND instr(exp_title,'" + p_searchWord +"',1,1) > 0");
		} else {
			searchParam.put("searchWord", "");
		}
		
		if(p_searchDiv.equals("")) {
			p_searchDiv = "u_reg_dt";
		} else if(p_searchDiv.equals("1")) {
			p_searchDiv = "exp_trade";
		} else if(p_searchDiv.equals("2")) {
			p_searchDiv = "u_reg_dt";
		} else if(p_searchDiv.equals("3")){
			p_searchDiv = "exp_price";
		}
		
		searchParam.put("pageSize", p_pageSize);
		searchParam.put("pageNo", p_pageNo);
		searchParam.put("searchDiv", p_searchDiv);
		searchParam.put("searchCategory", p_searchCategory);
		
		VO.setParam(searchParam);
		
		List<ExpertVO> list = (List<ExpertVO>)expertSvc.do_search(VO);
		List<ExpertVO> rank_list = (List<ExpertVO>) expertSvc.do_searchRank();
   	    
		int totalNo   = 0;
   	    if(list !=null && list.size()>0)totalNo = list.get(0).getTotalNo();
   	    
   	    //2017-10-13 @autor LSG
   	    //for codes
		CodesVO dto = new CodesVO();
		dto.setMst_cd_id("C002");	
		List<CodesVO> codeList = (List<CodesVO>)codesDao.do_search(dto);
   	    //for codes end
   	    
		ModelAndView modelAndView =new ModelAndView();
		
		modelAndView.addObject("list",list );
		modelAndView.addObject("rank_list",rank_list );
		modelAndView.addObject("totalNo",totalNo);
		modelAndView.addObject("searchDiv",req.getParameter("searchDiv"));
		modelAndView.addObject("searchCategoryNum",req.getParameter("searchCategoryNum"));
		modelAndView.addObject("codeList",codeList);
		req.setAttribute("searchWord",p_searchWord);
		modelAndView.setViewName("expert/expert_list");
		
		return modelAndView;
	}
	
	@RequestMapping(value="expert/do_searchOne.do")
	public void do_searchOne(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ExpertVO VO = new ExpertVO();
		VO.setU_id(req.getParameter("u_id"));
		VO.setU_password(req.getParameter("u_password"));
		VO.setU_level(Integer.parseInt(req.getParameter("u_level")));
		VO = (ExpertVO) expertSvc.do_searchOne(VO);
		
		if(VO == null) {
			res.getWriter().write("fail");
		} else {
			res.setCharacterEncoding("UTF-8");
			req.getSession().setAttribute("u_id", VO.getU_id());
			req.getSession().setAttribute("u_name", VO.getU_name());
			req.getSession().setAttribute("u_level", VO.getU_level());
			res.getWriter().write(new Gson().toJson(VO));
		}
	}
	
	@RequestMapping(value="expert/do_preview.do")
	public void do_preview(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ExpertVO VO = new ExpertVO();
		VO.setU_id(req.getParameter("u_id"));
		
		VO = (ExpertVO) expertSvc.do_chkId(VO);
		if( VO == null) {
			res.getWriter().write("fail");
		} else {
			res.setCharacterEncoding("UTF-8");
			res.getWriter().write(new Gson().toJson(VO));
		}
	}
	
	@RequestMapping(value="expert/do_save.do")
	public void do_save(MultipartHttpServletRequest req, HttpServletResponse res) throws IOException {
		String path = req.getSession().getServletContext().getRealPath("/resources/exp_profiles");
		
		System.out.println("파일 저장 경로: " + path);
		
		File file = new File(path);
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		
		String newFileName=null;
		
		if(req.getFile("exp_profile") != null) {
			MultipartFile mFile = req.getFile("exp_profile");
			String fileName = mFile.getOriginalFilename();
			newFileName = UUID.randomUUID().toString().replaceAll("-","") + "_" +fileName;
			System.out.println("파일명: " + newFileName);
			mFile.transferTo(new File(path+"/"+newFileName));//저장
		} else {
			newFileName = req.getParameter("exp_profile");
		}
		
		ExpertVO VO = new ExpertVO();
		VO.setU_id(req.getParameter("u_id"));
		VO.setU_naver(req.getParameter("u_naver"));
		VO.setU_name(req.getParameter("u_name"));
		VO.setU_password(req.getParameter("u_password"));		
		VO.setU_level(Integer.valueOf(req.getParameter("u_level")));
		VO.setExp_id(req.getParameter("u_id"));
		VO.setExp_price(Integer.valueOf(req.getParameter("exp_price")));
		VO.setExp_title(req.getParameter("exp_title"));
		VO.setExp_ctg(Integer.valueOf(req.getParameter("exp_ctg")));
		VO.setExp_profile(newFileName);
		
		int flag =1;
		flag *= userSvc.do_save(VO);
		flag *= expertSvc.do_save(VO);
		
		if(flag <= 0) {
			res.getWriter().write("fail");
		} else {
			res.getWriter().write("success");
		}
	}
	
	@RequestMapping(value="expert/do_update.do")
	public void do_update(MultipartHttpServletRequest req, HttpServletResponse res) throws IOException {
		ExpertVO VO = new ExpertVO();
		String path = req.getSession().getServletContext().getRealPath("/resources/exp_profiles");
		Hashtable<String, String> param = new Hashtable<String, String>();
		param.put("functionSep", req.getParameter("functionSep"));
		
		System.out.println("파일 저장 경로: " + path);
		
		File file = new File(path);
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		if(req.getFile("exp_profile") != null) {
			MultipartFile mFile = req.getFile("exp_profile");
			String fileName = mFile.getOriginalFilename();
			String newFileName=null;
			
			if(fileName != null) {
				newFileName = UUID.randomUUID().toString().replaceAll("-","") + "_" +fileName;
				System.out.println("파일명: " + newFileName);
				mFile.transferTo(new File(path+"/"+newFileName));//저장
				VO.setExp_profile(newFileName);
				param.replace("functionSep", "profile");
			}
		}
		
		VO.setParam(param);
		VO.setU_id(req.getParameter("u_id"));
		VO.setExp_id(req.getParameter("u_id"));
		VO.setU_naver(req.getParameter("u_name"));
		VO.setU_name(req.getParameter("u_name"));
		VO.setU_password(req.getParameter("u_password"));
		VO.setExp_price(Integer.valueOf(req.getParameter("exp_price")));
		VO.setExp_title(req.getParameter("exp_title"));
		VO.setExp_ctg(Integer.valueOf(req.getParameter("exp_ctg")));

		int flag = 1;
		
		if(VO.getParam().get("functionSep").equals("password")) {
			flag = userSvc.do_update(VO);
		} else {
			flag *= userSvc.do_update(VO);
			flag *= expertSvc.do_update(VO);
		}
		
		if(flag <= 0) {
			res.getWriter().write("fail");
		} else {
			req.getSession().setAttribute("u_name", VO.getU_name());
			res.getWriter().write("success");
		}
	}
	
}
