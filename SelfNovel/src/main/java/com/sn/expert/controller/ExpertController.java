package com.sn.expert.controller;

import java.io.File;
import java.io.IOException;
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

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
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
			res.getWriter().write(new Gson().toJson(VO));
		}
	}
	
	@RequestMapping(value="expert/do_save.do")
	public void do_save(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String path = "c:/exp_profiles";

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

		int flag1 = userSvc.do_save(VO);
		int flag2 = expertSvc.do_save(VO);
		
		if(flag1 <= 0 || flag2 <= 0) {
			res.getWriter().write("fail");
		} else {
			res.getWriter().write("success");
		}
	}
}
