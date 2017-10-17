package com.sn.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.sist.controller.HomeController;
import com.sn.common.ExcelUtil;
import com.sn.common.StringUtil;
import com.sn.expert.domain.ExpertVO;
import com.sn.resume.domain.ItmVO;
import com.sn.resume.domain.RsmVO;
import com.sn.resume.service.RsmSvc;

@Controller
public class ExcelDownTestController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Resource
	private View downloadView;
	
	@Autowired
	private RsmSvc rsmSvc;
	
	@RequestMapping(value="excelgogo.do")
	public ModelAndView main() {
		log.debug("===================");
		log.debug("========main=======");
		log.debug("===================");
		
		ModelAndView modelAndView =new ModelAndView();
		modelAndView.setViewName("viewTest/mailTest");
		
		return modelAndView;
	}
	
	/**										
	 * 2017-10-12										
	 * doExcelDownload	
	 * detail: 넘겨준 배열대로 엑셀을 만들어 다운시켜주는 서블릿
	 * 	
	 * @author LSG								
	 * @return										
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */										
	@RequestMapping(value="excelgogo_do_excelDown.do", method=RequestMethod.POST)										
	public ModelAndView do_excelDown_save(HttpServletRequest request) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		String path = "c:\\file\\excel\\";
		
		Hashtable<String, String> rsmParam = new Hashtable<String, String>();
		rsmParam.put("pageSize", "10");
		rsmParam.put("pageNo", "1");
		rsmParam.put("searchDiv", "10");
		rsmParam.put("searchCat", "");	
		rsmParam.put("searchWord", "제목");
		RsmVO	 rsmVO = new RsmVO();
		rsmVO.setParam(rsmParam);
		List<?> rsmList = rsmSvc.do_search(rsmVO);
		List<String> param = new ArrayList<String>();
		param.add("제목");
		String[] header = {"no","totalNo","rsm_title","rsm_content","u_id","rsm_reg_dt","rsm_div"};
		
		ExcelUtil excelUtil = new ExcelUtil();						
		String fileFullPath = excelUtil.writeExcelGeneral(path, "Tejava.xls", "시트이름", header, rsmList, RsmVO.class, param);
		
		
		ModelAndView modelAndView = new ModelAndView();									
		log.debug("======================================");									
		log.debug("fileFullPath: "+fileFullPath);									
		log.debug("======================================");									
											
		modelAndView.setView(this.downloadView);									
		File downloadFile=new File(path+fileFullPath);									
											
		log.debug("======================================");									
		log.debug("downloadFile.: "+downloadFile.getAbsolutePath());									
		log.debug("======================================");									
											
		modelAndView.addObject("downloadFile",downloadFile);									
		return modelAndView;									
		//http://localhost:8080/controller/user/do_search_ajax.do									
	}		
}
