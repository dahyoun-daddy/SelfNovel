package com.sn.expert.controller;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sn.common.StringUtil;
import com.sn.expert.domain.ExpertVO;
import com.sn.expert.service.ExpertSvc;
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
	
	@RequestMapping(value="expert/do_search.do")
	public ModelAndView do_search(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ExpertVO VO = new ExpertVO();	 
		Hashtable<String, String> 
		searchParam = new Hashtable<String, String>();//검색조건
		String p_pageSize = StringUtil.nvl(req.getParameter("page_size"),"16");
		String p_pageNo  = StringUtil.nvl(req.getParameter("page_num"),"1");
		String p_searchDiv = StringUtil.nvl(req.getParameter("searchDiv"),"");
		String p_searchCategory = StringUtil.nvl(req.getParameter("searchCategory"),"");
		String p_searchWord = StringUtil.nvl(req.getParameter("searchWord"),"");
		
		if(!p_searchWord.equals("") && p_searchWord != null) {
			p_searchWord = "AND instr(exp_title,'" + p_searchWord +"',1,1) > 0";
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
		searchParam.put("searchWord", p_searchWord);
		searchParam.put("searchCategory", p_searchCategory);
		
		VO.setParam(searchParam);
		
		List<ExpertVO> list = (List<ExpertVO>)expertSvc.do_search(VO);
		List<ExpertVO> rank_list = (List<ExpertVO>) expertSvc.do_searchRank();
   	    
		int totalNo   = 0;
   	    if(list !=null && list.size()>0)totalNo = list.get(0).getTotalNo();
   	    
		ModelAndView modelAndView =new ModelAndView();
		
		modelAndView.addObject("list",list );
		modelAndView.addObject("rank_list",rank_list );
		modelAndView.addObject("totalNo",totalNo);
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
	public void do_save(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String path = req.getSession().getServletContext().getRealPath("/resources/exp_profiles");
		
		File file = new File(path);
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		MultipartRequest mr = new MultipartRequest(req, path, 1024 * 1024 * 5, "utf-8",
				new DefaultFileRenamePolicy());
		
		String fileName = mr.getOriginalFileName("exp_profile");
		String newFileName = UUID.randomUUID().toString().replaceAll("-","") + "_" +mr.getOriginalFileName("exp_profile");
		// 원본이 업로드된 절대경로와 파일명를 구한다.
		String fullFileName = path + "/" + fileName;
	    File f1 = new File(fullFileName);
	    if(f1.exists()) {     // 업로드된 파일명이 존재하면 Rename한다.
	         File newFile = new File(path + "/" + newFileName);
	         f1.renameTo(newFile);   // rename...
	    }
		
		ExpertVO VO = new ExpertVO();
		VO.setU_id(mr.getParameter("u_id"));
		VO.setU_name(mr.getParameter("u_name"));
		VO.setU_password(mr.getParameter("u_password"));		
		VO.setU_level(Integer.valueOf(mr.getParameter("u_level")));
		VO.setExp_id(mr.getParameter("u_id"));
		VO.setExp_price(Integer.valueOf(mr.getParameter("exp_price")));
		VO.setExp_title(mr.getParameter("exp_title"));
		VO.setExp_ctg(Integer.valueOf(mr.getParameter("exp_ctg")));
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
	public void do_update(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String path = req.getSession().getServletContext().getRealPath("/resources/exp_profiles");
		
		File file = new File(path);
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		MultipartRequest mr = new MultipartRequest(req, path, 1024 * 1024 * 5, "utf-8",
				new DefaultFileRenamePolicy());
		
		String fileName = mr.getOriginalFileName("exp_profile");
		String newFileName = null;
		Hashtable<String, String> param = new Hashtable<String, String>();
		param.put("functionSep", mr.getParameter("functionSep"));
		
		if(fileName != null) {
			param.replace("functionSep", "profile");
			newFileName = UUID.randomUUID().toString().replaceAll("-","") + "_" +mr.getOriginalFileName("exp_profile");
			// 원본이 업로드된 절대경로와 파일명를 구한다.
			String fullFileName = path + "/" + fileName;
		    File f1 = new File(fullFileName);
		    if(f1.exists()) {     // 업로드된 파일명이 존재하면 Rename한다.
		         File newFile = new File(path + "/" + newFileName);
		         f1.renameTo(newFile);   // rename...
		    }
		}
		
		ExpertVO VO = new ExpertVO();
		VO.setParam(param);
		VO.setU_id(mr.getParameter("u_id"));
		VO.setExp_id(mr.getParameter("u_id"));
		VO.setU_name(mr.getParameter("u_name"));
		VO.setU_password(mr.getParameter("u_password"));
		VO.setExp_price(Integer.valueOf(mr.getParameter("exp_price")));
		VO.setExp_title(mr.getParameter("exp_title"));
		VO.setExp_ctg(Integer.valueOf(mr.getParameter("exp_ctg")));
		VO.setExp_profile(newFileName);

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