package com.sn.msg.controller;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sn.common.StringUtil;
import com.sn.msg.domain.MsgVO;
import com.sn.msg.service.MsgSvc;
@Controller
public class MsgController {
	@Autowired
	MsgSvc msgSvc;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 2017_10_17
	 * reportForm
	 * detail : 신고 작성 폼으로 이동
	 * @param req
	 * @return modelAndView : 신고작성 폼
	 */	
	@RequestMapping(value="message/reportForm.do")
	public ModelAndView reportView(HttpServletRequest req) {
		log.info("===== MsgController/reportForm.do =====");
		log.info("req : " + req.toString());		
		log.info("=======================================");
		
		String u_id = (String) req.getSession().getAttribute("u_id");//세션에서 현재 ID값 read
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("message/report_form");
		modelAndView.addObject("rsm_id", req.getParameter("rsm_id"));		//자소서 id
		modelAndView.addObject("rsm_title", req.getParameter("rsm_title"));		//자소서 제목
		modelAndView.addObject("u_name", req.getParameter("u_name")); //자소서 작성자
		modelAndView.addObject("msg_sender", u_id);							//신고자
		modelAndView.addObject("msg_notify", req.getParameter("rsm_u_id")); //자소서 작성자
		modelAndView.addObject("msg_sep", "0");								//구분		
		
		return modelAndView;
	}
	
	/**
	 * 2017_10_17
	 * do_save
	 * detail : 메시지 저장
	 * @author MinSeok <dev.edwinner@gmail.com>
	 * @return String 
	 */
	@RequestMapping(value="message/do_save.do")
	@ResponseBody
	public String do_save(HttpServletRequest req) {
		log.info("===== MsgController/do_save.do =====");
		log.info("req : " + req.toString());		
		log.info("====================================");
		
		MsgVO inMsgVO = new MsgVO();
		
		//request에서 msg관련 파라미터들을 불러와서 vo에 set한다.
		inMsgVO.setMsg_sender(req.getParameter("msg_sender"));//보내는 사람
		inMsgVO.setMsg_receiver(req.getParameter("msg_receiver"));//받는 사람		
		inMsgVO.setMsg_content(req.getParameter("msg_content"));//내용
		inMsgVO.setMsg_sep(Integer.parseInt(req.getParameter("msg_sep")));//구분		
		
		//메시지 구분이 0인 경우, 신고메시지 이므로 신고에 관련되 파라미터들을 추가로 set 한다.
		if(Integer.parseInt(req.getParameter("msg_sep")) == 0) {
			inMsgVO.setMsg_notify(req.getParameter("msg_notify").trim());//신고 대상			
			
			String rsm_id = req.getParameter("rsm_id").trim();			
			inMsgVO.setRsm_id(Integer.parseInt(rsm_id));//신고 받는 글 ID			
		}		
		
		//msgSvc를 호출한다.
		int flag = msgSvc.do_save(inMsgVO);
		
		//flag(결과) 리턴
		return flag+"";
	}
	
	/**
	 * 2017_10_17
	 * do_searchReport
	 * detail : 신고 조회
	 * @author MinSeok <dev.edwinner@gmail.com>
	 * @return List<MsgVO>
	 */
	@RequestMapping(value="/mypage/manager/manager_report_list.do")
	public ModelAndView do_searchReport(HttpServletRequest req) {
		log.info("===== MsgController/do_searchReport.do =====");
		log.info("req : " + req.toString());		
		log.info("============================================");
		
		//request로부터 parameter load
		String page_size = StringUtil.nvl(req.getParameter("page_size"),"10");
		String page_num  = StringUtil.nvl(req.getParameter("page_num"),"1");
		String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"),"");
		String searchWord = StringUtil.nvl(req.getParameter("searchWord"),"");
		String total_cnt = "";
		
		Hashtable<String, String> searchParam = new Hashtable<String, String>();
		
		//searchParam에 request로부터 읽은 파라미터 set
		searchParam.put("page_size", page_size);
		searchParam.put("page_num", page_num);
		searchParam.put("searchDiv", searchDiv);
		searchParam.put("searchWord", searchWord);		
		
		//Vo생성 후, searchParam set
		MsgVO inDto = new MsgVO();
		inDto.setParam(searchParam);
		
		//list객체 생성 후, inRsmVo 주입
		List<?> reportList = msgSvc.do_searchReport(inDto);
		if(reportList.size()>0) {
			MsgVO msgVO = (MsgVO) reportList.get(0);
			total_cnt = String.valueOf(msgVO.getTotalNo());			
		}
		
		//ModelAndView 생성
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("reportList", reportList);
		modelAndView.addObject("page_size", page_size);
		modelAndView.addObject("page_num", page_num);
		modelAndView.addObject("searchDiv", searchDiv);
		modelAndView.addObject("searchWord", searchWord);
		modelAndView.addObject("totalCnt", total_cnt);		
		modelAndView.setViewName("/mypage/manager/manager_report_list");		
		
		return modelAndView;
	}

	@RequestMapping(value="/mypage/manager/do_deleteMsg.do", method=RequestMethod.POST)
	@ResponseBody
	public String do_delete(HttpServletRequest req) {
		log.info("===== MsgController/do_deleteAll.do =====");
		log.info("req : " + req.toString());		
		log.info("============================================");
		
		String msgList = StringUtil.nvl(req.getParameter("msgList"),"");
		
		Hashtable<String, String> param = new Hashtable<String, String>();
		param.put("msgList", msgList);
		
		MsgVO inDto = new MsgVO();
		inDto.setParam(param);
		
		int count = msgSvc.do_deleteAll(inDto);
		
		return count + "";
	}
}
