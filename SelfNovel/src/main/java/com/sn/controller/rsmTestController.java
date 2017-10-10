package com.sn.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sn.common.DTO;
import com.sn.common.ExcelUtil;
import com.sn.common.FileSaveUtil;
import com.sn.common.FileSaveVO;
import com.sn.common.StringUtil;
import com.sn.img.domain.ImgVO;
import com.sn.img.service.ImgSvc;
import com.sn.resume.domain.ItmVO;
import com.sn.resume.domain.RsmVO;
import com.sn.resume.service.ItmSvc;
import com.sn.resume.service.RsmSvc;

/**
 * rsmTestController 
 * detail : dao테스트하고 싶은데 main에서 autowired로 추가하면 자꾸 null에러떠서 걍 만든 컨트롤러
 * 최초작성: 2017-09-21
 * @author @author MinSeok <dev.edwinner@gmail.com>
 *
 */
@Controller
public class rsmTestController {
	private static final Logger log = LoggerFactory.getLogger(rsmTestController.class);
	@Autowired
	RsmSvc rsmSvc;
	@Autowired
	ItmSvc itmSvc;
	@Autowired
	ImgSvc imgSvc;
	
	/**
	 * img Test resume_view
	 */
	@RequestMapping(value="testRsm.do")
	public String resumeForm() {
		log.debug("===================");
		log.debug("=======resumeview======");
		log.debug("===================");
		
		return "viewTest/resumeTest";
	}
	
	/**
	 * img Test resume_view
	 */
	@RequestMapping(value="rsmParamTest.do", method=RequestMethod.POST)
	public String testSave(HttpServletRequest req) {
		log.debug("===================");

		//********************************************************************//
		// resume
		//********************************************************************//
		// 글 본문 저장
		//********************************************************************//
		//1. resume 시퀀스의 nextVal을 가져온다
		String rsm_id=rsmSvc.do_getNextVal();
		log.debug("다음 아이디값: "+rsm_id);
		
		//2. 값 꺼낸다
		String rsm_title="임시제목이다";
		String rsm_content="임시내용이다";
		
		//3. vo 만들어서 데이터 넣음
		RsmVO rsmVo = new RsmVO();
		rsmVo.setRsm_id(rsm_id);
		rsmVo.setImg_id("");
		rsmVo.setRsm_title(rsm_title);
		rsmVo.setRsm_content(rsm_content);
		rsmVo.setU_id("안녕나는유저");
		rsmVo.setRsm_ord_yn("1");
		rsmVo.setRsm_div("");
		
		//4. vo를 서비스에 넘김!
		rsmSvc.do_save(rsmVo);
		
		//********************************************************************//
		// item
		//********************************************************************//
		//1. 먼저 배열로 값 꺼낸다
		String[] titlelist = (String[]) req.getParameterValues("title");
		String[] contentlist = (String[]) req.getParameterValues("content");
		
		//2. itemVO를 넣을 리스트를 만든다.
		List<DTO> itmList = new ArrayList<DTO>();		
		
		//3. for문돌림
		for(int i=0;i<contentlist.length;i++) {
			log.debug(titlelist[i]);
			log.debug(contentlist[i]);
			
			//3-1. vo 만들어서 데이터 넣음
			ItmVO vo = new ItmVO();
			vo.setRsm_id(rsm_id);				//id값 넣어줌
			vo.setItm_title(titlelist[i]);		//제목 넣어줌
			vo.setItm_content(contentlist[i]);	//내용 넣어줌
			vo.setU_id("작성자다");					//작성자 아이디
			vo.setItm_prd_id("");				//상위항목=작성이므로 null
			vo.setItm_seq(i);					//순서
			
			//3-2. vo를 리스트에 넣음!
			itmList.add(vo);
			
			//지금은 다른데 건드리기 뭐하니까 여기서 걍 서비스 호출해서 테스트함!
			itmSvc.do_save(vo);
		}
		
		//4. 리스트를 서비스에 넘김!
		//itmSvc.do_Save(itmList); <<리스트를 받는 메소드 추가가 필요함!
		//********************************************************************//	
		return "redirect:testRsm.do";
	}
	
	/**
	 * img Test resume_view
	 */
	@RequestMapping(value="changeTest.do")
	public String resumeForm2() {
		log.debug("===================");
		log.debug("=======resumeview======");
		log.debug("===================");
		
		return "viewTest/changeTest";
	}
	
	/**
	 * img Test resume_view
	 * @throws IOException 
	 */
	@RequestMapping(value="doExcelDown.do")
	public String doExcelDownLoad() throws IOException {
		log.debug("=========doExcelDownLoad==========");
		ExcelUtil eu = new ExcelUtil();
		eu.writeExcel("c:\\file\\excel\\", "resume", new ArrayList<String>());
		
		return "redirect:changeTest.do";
	}
	
	
	@RequestMapping(value="ppt/pptUpload.do", method=RequestMethod.POST)
	public String pptUpload(MultipartHttpServletRequest mReq) throws IOException,DataAccessException{
		ModelAndView modelAndView = new ModelAndView();
		
		//1. 먼저 파일유틸을 이용해 파일을 저장하고 파일명을 받는다.
		String savePath = "C://file//";
		String resourcesFilepath = mReq.getSession().getServletContext().getRealPath("resources");
		
		FileSaveUtil fileSaveUtil = new FileSaveUtil();
		List<FileSaveVO> fileList = fileSaveUtil.do_saveMulti(mReq, savePath);
		String saveFileName = "";
		
		if(fileList!=null) {
			FileSaveVO fileSaveVO = fileList.get(0);
			saveFileName = StringUtil.nvl(fileSaveVO.getSaveFileName(), "");
		}
		
		//2. 이미지 서비스 쪽에 파일의 저장경로를 넘겨준다. 그럼 저장하고 이미지 아이디를 반환한다.
		int img_id = this.imgSvc.do_savepptTx(saveFileName,savePath+saveFileName,resourcesFilepath);
		
		//3. 반환된 이미지 아이디를 가지고 리스트를 불러온다.
		ImgVO inVO = new ImgVO();
		inVO.setImg_id(img_id);		
		List<?> imgList = imgSvc.do_search(inVO); 
		
		return String.valueOf(img_id);
	}
}
