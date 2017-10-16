package com.sn.resume.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.CookieGenerator;

import com.sn.codes.dao.CodesDao;
import com.sn.codes.domain.CodesVO;
import com.sn.common.DTO;
import com.sn.common.FileSaveUtil;
import com.sn.common.FileSaveVO;
import com.sn.common.StringUtil;
import com.sn.img.domain.ImgVO;
import com.sn.img.service.ImgSvc;
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
	
	@Autowired
	CodesDao codesDao;
	
	//for excelDown @autor LSG
	@Resource
	private View downloadView;
	
	//for pptSave @autor LSG
	@Autowired
	ImgSvc imgSvc;
	
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
		
		// code 테이블 조회
		CodesVO dto = new CodesVO();
		dto.setMst_cd_id("C002");	
		List<CodesVO> codeList = (List<CodesVO>)codesDao.do_search(dto);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", list );		
		modelAndView.addObject("totalCnt", totalCnt);
		modelAndView.addObject("searchWord", p_searchWord);
		modelAndView.addObject("searchDiv", p_searchDiv);
		modelAndView.addObject("searchCat", p_searchCat);
		modelAndView.addObject("codeList", codeList);
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
		
		RsmVO inRsmVO = new RsmVO();					//자기소개서 조회용 객체		
		ItmVO inItmVO = new ItmVO();					//하위항목 조회용 객체
		RsmVO result = new RsmVO();						//자기소개서 조회결과 객체 
		List<ItmVO> itmList = new ArrayList<ItmVO>();	//하위항목 조회결과 객체List
		
		// 0. 자기소개서 id get
		String rsm_id = req.getParameter("rsm_id");		
		
		// 1. 자기소개서 객체, 하위항목 객체에 자소서id set
		inRsmVO.setRsm_id(rsm_id);
		inItmVO.setRsm_id(rsm_id);
		
		// 2. resume테이블 단건조회
		result = (RsmVO) rsmSvc.do_searchOne(inRsmVO);		
		
		// 3. item테이블 조회
		itmList = (List<ItmVO>) itmSvc.do_search(inItmVO);
		
		// 4. code 테이블 조회
		CodesVO dto = new CodesVO();
		dto.setMst_cd_id("C002");	
		List<CodesVO> codeList = (List<CodesVO>)codesDao.do_search(dto);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("resume/resume_mod");
		modelAndView.addObject("resume", result);
		modelAndView.addObject("itemList", itmList);
		modelAndView.addObject("codeList", codeList);
		
		return modelAndView;
	}
	
	/**
	 * do_update
	 * @param req
	 * @return ModelAndView
	 */
	@RequestMapping(value="resume/do_update.do")
	public ModelAndView do_update(HttpServletRequest req) {
		log.debug("===== RsmDaocontroller.do_update =====");
		log.debug("req : " + req.toString());
		log.debug("======================================");		
		
		//1. 리퀘스트로부터 resume테이블에 갱신할 내용을 get
		String rsm_id      = req.getParameter("rsm_id");		//자소서 id
		String rsm_title   = req.getParameter("rsm_title");		//자소서 제목
		String rsm_content = req.getParameter("rsm_content");	//자소서 내용
		String rsm_div     = req.getParameter("selectBox");		//자소서 구분
		String u_id        = req.getParameter("u_id");			//작성자 ID
		String img_id      = req.getParameter("img_id");		//이미지 ID
		
		//2. inRsmVO 객체 생성 후, request로부터 받아온 parameter들을 set
		RsmVO inRsmVO = new RsmVO();		
		inRsmVO.setRsm_id(rsm_id);
		inRsmVO.setRsm_title(rsm_title);
		inRsmVO.setRsm_content(rsm_content);
		inRsmVO.setRsm_div(rsm_div);
		inRsmVO.setImg_id(img_id);

		//rsmSvc의 do_update 호출
		rsmSvc.do_update(inRsmVO);
		
		//--------------------------------------------------------------
		//3. 리퀘스트로부터 item테이블에 갱신할 내용을 get
		String[] ids = req.getParameterValues("itm_form_id");
		String[] titles = req.getParameterValues("itm_title");
		String[] contents = req.getParameterValues("itm_content");	
		
		ItmVO tempItmVO = new ItmVO();	//Resume의 모든 하위 Item들을 제거하기 위해 사용될 VO
		tempItmVO.setRsm_id(rsm_id);
		
		itmSvc.do_deleteAllRoot(tempItmVO);	//모든 하위 항목을 삭제하여, 아이템이 줄어든 경우 문제 방지
		
		//4. inItmVO 객체 생성 후, 리퀘스트로부터 받아온 parameter들을 set
		//그리고 itmSvc의 do_update 호출
		for(int i = 0; i < titles.length; i++) {
			ItmVO inItmVO = new ItmVO();
			inItmVO.setRsm_id(rsm_id);
			inItmVO.setU_id(u_id);
			inItmVO.setItm_form_id(ids[i]);
			inItmVO.setItm_title(titles[i]);			
			inItmVO.setItm_content(contents[i]);
			inItmVO.setItm_seq(i);			
			itmSvc.do_update(inItmVO);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("rsm_id", rsm_id);
		modelAndView.setView(new RedirectView("do_searchOne.do"));
		
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
		
		//조회수 증가
		rsmSvc.do_update_count(resultVO);
		
		//itmVO에 rsm_id를 set
		ItmVO itmVO = new ItmVO();
		itmVO.setRsm_id(rsm_id);
		
		//ItmSvc를 통해 해당 자기소개서의 항목들 호출
		List<ItmVO> itmList = (List<ItmVO>) itmSvc.do_search(itmVO);
		//HashMap<String, String> sizeMap = itmSvc.do_search(itmVO);
		
		/*
		 //2017-10-12 추가
		 //@autor: LSG
		 //ppt 미리보기를 위한 이미지 아이디 세팅
		 //modelAndView.addObject("imgList", imgList); 추가
		*/
		String img_id = resultVO.getImg_id();
		List<?> imgList = null;
		if(img_id!=null) {
			ImgVO imgVO = new ImgVO();
			imgVO.setImg_id(Integer.valueOf(img_id));
			imgList = imgSvc.do_search(imgVO);
		}
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("resume/resume_view");
		modelAndView.addObject("rsmVO", resultVO);	
		modelAndView.addObject("itmList", itmList);
		modelAndView.addObject("imgList", imgList);
		
		return modelAndView;		
	}
	
	/**
	 * updateRecommend
	 * detail : 추천버튼 클릭시 추천수를 증가시킨다.
	 * @param req
	 * @return
	 */
	@RequestMapping(value="resume/do_updateRecommend")
	@ResponseBody
	public int do_updateRecommend(HttpServletRequest req, HttpServletResponse res) {
		log.debug("===== RsmDaocontroller.do_updateRecommend =====");
		log.debug("req : " + req.toString());
		log.debug("===============================================");
		
		// 0. 쿠키명 지정
		String cookieName = req.getParameter("rsm_id");
		
		// 1. 저장된 쿠키 불러오기 
		Cookie cookies[] = req.getCookies(); 
		Cookie cookie_tmp = null;  
		
		// 2. 쿠키가 존재 한다면 탐색
		if (cookies != null && cookies.length > 0) {  
			 for (Cookie cookie : cookies) {
				 if (cookieName.equals(cookie.getName())) {
					 return 404;
				 }//close if
			 }//close for
		}//close if
		
		// last. 쿠키가 존재하지 않는다면 쿠키 생성
		if(cookie_tmp == null) {
			CookieGenerator cookieGer = new CookieGenerator();
			cookieGer.setCookieName(req.getParameter("rsm_id"));
			//cookieGer.setCookiePath("/web_project"); 
			cookieGer.addCookie(res, "recommended"); 
		}		
		
		String rsm_id = req.getParameter("rsm_id");
		RsmVO inRsmVO = new RsmVO();
		inRsmVO.setRsm_id(rsm_id);
		
		int flag = rsmSvc.do_update_recommend(inRsmVO);
		
		return flag;
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
	 * do_delete
	 * @param req
	 * @return flag
	 */
	@RequestMapping(value="resume/do_delete.do")
	@ResponseBody
	public int do_delete(HttpServletRequest req) {
		log.debug("===== RsmDaocontroller.do_delete =====");
		log.debug("req : " + req.toString());		
		log.debug("rsm_id : " + req.getParameter("rsm_id"));
		log.debug("======================================");
		
		String rsm_id = req.getParameter("rsm_id");
		
		RsmVO inRsmVO = new RsmVO();
		inRsmVO.setRsm_id(rsm_id);
		
		int flag = rsmSvc.do_delete(inRsmVO);
		
		return flag;
	}
	
	/**
	 * do_save_edit
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
	
	/**
	 * doUpdateOne
	 * detail : 하위항목 단건 수정
	 * @param req
	 * @return flag
	 */
	@RequestMapping(value="resume/do_updateOne.do")
	@ResponseBody
	public int doUpdateItem(HttpServletRequest req) {
		log.debug("===== RsmDaocontroller.do_updateOne =====");
		log.debug("req : " + req.toString());
		log.debug("=========================================");
		
		ItmVO inItmVo = new ItmVO();
		inItmVo.setItm_form_id(req.getParameter("itm_form_id"));		
		inItmVo.setItm_title(req.getParameter("itm_title"));
		inItmVo.setItm_content(req.getParameter("itm_content"));		
		
		//itmSvc를 통해 do_save메소드 호출
		int flag = itmSvc.do_updateOne(inItmVo);
		
		return flag;
	}
	
	
	/**										
	 * 2017-10-12										
	 * doExcelDownload	
	 * detail: 이력서를 엑셀로 다운시켜주는 서블릿.
	 * 	
	 * @author LSG								
	 * @return										
	 */										
	@RequestMapping(value="resume/do_excelDown.do", method=RequestMethod.POST)										
	public ModelAndView do_excelDown(HttpServletRequest request) throws IOException{
		ItmVO inVO = new ItmVO();
		//inVO.setParam(StringUtil.createParam(request));
		String excel_rsm_id = StringUtil.nvl(request.getParameter("excel_rsm_id"), "");
		String u_name = StringUtil.nvl(request.getParameter("u_name"), "");
		inVO.setRsm_id(excel_rsm_id);
											
		String fileFullPath = this.itmSvc.do_ExcelDownload(inVO,u_name);									
		ModelAndView modelAndView = new ModelAndView();									
		log.debug("======================================");									
		log.debug("fileFullPath: "+fileFullPath);									
		log.debug("======================================");									
											
		modelAndView.setView(this.downloadView);									
		File downloadFile=new File(fileFullPath);									
											
		log.debug("======================================");									
		log.debug("downloadFile.: "+downloadFile.getAbsolutePath());									
		log.debug("======================================");									
											
		modelAndView.addObject("downloadFile",downloadFile);									
		return modelAndView;									
		//http://localhost:8080/controller/user/do_search_ajax.do									
	}										

	/**										
	 * 2017-10-12										
	 * do_save		
	 * detail: 작성 페이지 로드하는 서블릿.
	 * 
	 * @author LSG								
	 * @return										
	 */		
	@RequestMapping(value="resume/do_save.do", method=RequestMethod.GET)
	public ModelAndView do_save(HttpServletRequest req) {
		//코드테이블 분야 리스트를 받는다.
		CodesVO dto = new CodesVO();
		dto.setMst_cd_id("C002");	
		List<CodesVO> codeList = (List<CodesVO>)codesDao.do_search(dto);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("codeList",codeList);
		modelAndView.setViewName("resume/resume_form");		
		return modelAndView;		
	}

	/**										
	 * 2017-10-12										
	 * pptPopup		
	 * detail: ppt 업로드하는 popup페이지 띄우는 서블릿.
	 * 
	 * @author LSG								
	 * @return										
	 */	
	@RequestMapping(value="resume/do_save.do", method=RequestMethod.POST)
	public String do_saveResume(HttpServletRequest req) {
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
		String rsm_title=(String)req.getParameter("stitle");
		String rsm_content=(String)req.getParameter("scontent");
		String img_id = StringUtil.nvl(req.getParameter("img_id"), "");
		String rsm_ord_yn =(String)req.getParameter("rsm_ord_yn");
		String rsm_div = StringUtil.nvl(req.getParameter("rsm_div"),"");
		//2-1세션값에서 아이디값 꺼내기
		String u_id = (String) req.getSession().getAttribute("u_id");


		//3. vo 만들어서 데이터 넣음
		RsmVO rsmVo = new RsmVO();
		rsmVo.setRsm_id(rsm_id);
		rsmVo.setImg_id(img_id);
		rsmVo.setRsm_title(rsm_title);
		rsmVo.setRsm_content(rsm_content);
		rsmVo.setU_id(u_id);
		rsmVo.setRsm_ord_yn(rsm_ord_yn);
		rsmVo.setRsm_div(rsm_div);
		
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
			vo.setU_id(u_id);					//작성자 아이디
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
		return "redirect:do_searchOne.do?rsm_id="+rsm_id;
	}	
	
	/**										
	 * 2017-10-12										
	 * pptPopup		
	 * detail: ppt 업로드하는 popup페이지 띄우는 서블릿.
	 * 
	 * @author LSG								
	 * @return										
	 */	
	@RequestMapping(value="resume/pptUpload.do")
	public String pptPopup(){
		return "resume/popup/resume_ppt_pop";
	}
	
	@RequestMapping(value="resume/pptUpload.do", method=RequestMethod.POST)
	public ModelAndView pptUpload(MultipartHttpServletRequest mReq) throws IOException,DataAccessException{
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
		log.debug("ing_id: "+img_id);
		
		modelAndView.addObject("img_id",img_id);
		modelAndView.setViewName("resume/popup/resume_ppt_pop");
		return modelAndView;
	}
}
