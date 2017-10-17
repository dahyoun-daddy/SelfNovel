package com.sn.msg.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
		log.info("보내는 사람 set 성공");
		
		inMsgVO.setMsg_receiver(req.getParameter("msg_receiver"));//받는 사람
		log.info("받는 사람 set 성공");
		
		inMsgVO.setMsg_content(req.getParameter("msg_content"));//내용
		log.info("내용 set 성공");
		
		inMsgVO.setMsg_sep(Integer.parseInt(req.getParameter("msg_sep")));//구분
		log.info("구분 set 성공");
		
		//메시지 구분이 0인 경우, 신고메시지 이므로 신고에 관련되 파라미터들을 추가로 set 한다.
		if(Integer.parseInt(req.getParameter("msg_sep")) == 0) {
			inMsgVO.setMsg_notify(req.getParameter("msg_notify"));//신고 대상
			log.info("신고대상 set 성공");
			
			String rsm_id = req.getParameter("rsm_id").trim();			
			inMsgVO.setRsm_id(Integer.parseInt(rsm_id));//신고 받는 글 ID
			log.info("신고대상글 set 성공");
		}		
		
		//msgSvc를 호출한다.
		int flag = msgSvc.do_save(inMsgVO);
		
		//flag(결과) 리턴
		return flag+"";
	}
}
