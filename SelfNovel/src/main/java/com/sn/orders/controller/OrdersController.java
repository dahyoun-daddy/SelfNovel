package com.sn.orders.controller;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.sn.common.StringUtil;
import com.sn.expert.domain.ExpertVO;
import com.sn.expert.service.ExpertSvc;
import com.sn.orders.domain.OrdersVO;
import com.sn.orders.service.OrdersSvc;
import com.sn.resume.domain.ItmVO;
import com.sn.resume.domain.UnityItmVO;

/**
 * OrdersController 
 * detail : 마이페이지-의뢰 페이지 컨트롤러 
 * 최초작성: 2017-09-22
 * 최종수정: 2017-09-22  
 * @author @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
@Controller
public class OrdersController { 
	/***********************************************/
	//field
	/***********************************************/
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	OrdersSvc orderSvc;
	
	/***********************************************/
	//servlet
	/***********************************************/
	/**
	 * not use
	 * 
	 * ModelAndView
	 * detail: main list view
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/mypage/orders/list.do")
	public ModelAndView main(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		//Transaction test
//		OrdersVO dto2 = new OrdersVO(1, "sty2002", "1", "1", "1","1");
//		orderSvc.do_saveTx(dto2);
		
		//임시 dto
		Hashtable<String, String> param = new Hashtable<String, String>();
//		param.put("SEARCH_DIV", "exp");
//		param.put("SEARCH_ID", "exp1"); 
		param.put("SEARCH_DIV", "user");
		param.put("SEARCH_ID", "sty2003");		
		OrdersVO dto = new OrdersVO();
		dto.setParam(param);
		log.debug("in controller param: "+param.toString());
		
		modelAndView.addObject("ordersList",orderSvc.do_search(dto));
		modelAndView.setViewName("mypage/orders/orders_list");
		return modelAndView;
	}
	
	/**
	 * workDivOrders
	 * detail: 게시글의 state처리 및 삭제
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/mypage/orders/workdiv.do")
	public String workDivOrders(HttpServletRequest request) {
		String RSM_ID = request.getParameter("RSM_ID");
		String EXP_ID = request.getParameter("EXP_ID");
		String WORK_DIV = StringUtil.nvl(request.getParameter("WORK_DIV"), "");
		
		OrdersVO dto = new OrdersVO();		
		dto.setRsm_id(Integer.parseInt(RSM_ID));
		dto.setExp_id(EXP_ID);
		
		if(WORK_DIV.equals("do_nextState")) {
			orderSvc.do_nextState(dto);
		}else if(WORK_DIV.equals("do_reject")) {
			orderSvc.do_reject(dto);
		}else if(WORK_DIV.equals("do_delete")) {
			orderSvc.do_deleteTx(dto);
		}

		return "redirect:pagelist.do";
	}
	
	@RequestMapping(value = "/mypage/orders/pagelist.do", method = RequestMethod.GET)
	public ModelAndView do_search(HttpServletRequest req) {
		//*******************시나리오*************************
		//1. 먼저 session으로부터 유저의 등급, 아이디를 얻는다.
		//2. 유저의 등급, 아이디, 페이지 사이즈, 페이지 번호를 inVO에 넣는다.
		//3. 가져온 데이터 리스트를 modelAndView에 넣는다.
		//4. 유저의 등급을 modelAndView에 넣는다.
		//5. 페이징을 위한 기타 변수들을 넣는다. 끝.		
		
		OrdersVO ordersVO = new OrdersVO();
		Hashtable<String, String> searchParam = new Hashtable<String, String>();// 검색조건
		List<OrdersVO> ordersList;
		String u_id 	  = "";	//유저 아이디
		int	   u_level	  = 0;	//유저 등급
		String p_pageSize = "";	//페이지 사이즈
		String p_pageNo	  = "";	//페이지 번호
		int TOTALCNT 	  = 0;	//전체 게시글수
		
		//1. 먼저 session으로부터 유저의 등급, 아이디를 얻는다.
		u_id 	= (String) req.getSession().getAttribute("u_id");
		u_level = (Integer) req.getSession().getAttribute("u_level");
		
		//2. 아이디, 유저의 등급, 페이지 사이즈, 페이지 번호를 inVO에 넣는다.	
		p_pageSize = StringUtil.nvl(req.getParameter("PAGE_SIZE"), "10");
		p_pageNo = StringUtil.nvl(req.getParameter("PAGE_NUM"), "1");
		searchParam.put("PAGE_SIZE", p_pageSize);
		searchParam.put("PAGE_NUM", p_pageNo);
		searchParam.put("SEARCH_ID", u_id);	 
		searchParam.put("SEARCH_DIV", String.valueOf(u_level));	
		ordersVO.setParam(searchParam);

		log.debug(searchParam.toString());
		
		//3. 가져온 데이터 리스트를 modelAndView에 넣는다.		
		ordersList = (List<OrdersVO>) orderSvc.do_search(ordersVO);
		
		//3-1. totalCnt 구함
		if (ordersList != null && ordersList.size() > 0)
			TOTALCNT = ordersList.get(0).getTotalNo();
		
		//4. 유저의 등급을 modelAndView에 넣는다.
		//5. 페이징을 위한 기타 변수들을 넣는다. 끝.			
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("u_level", u_level);
		modelAndView.addObject("ordersList", ordersList);
		modelAndView.addObject("PAGE_SIZE", p_pageSize);
		modelAndView.addObject("PAGE_NUM", p_pageNo);
		modelAndView.addObject("TOTALCNT", TOTALCNT);
		modelAndView.setViewName("/mypage/orders/orders_list");

		return modelAndView;
	}
	
	@RequestMapping(value="/mypage/orders/do_save.do")
	public void do_save(HttpServletRequest req, HttpServletResponse res) throws IOException {
		UnityItmVO unityItmVO = new UnityItmVO();
		int flag=1;
		String[] itm_title_arr = req.getParameter("itm_titles").split("\\\\");
		String[] itm_content_arr = req.getParameter("itm_contents").split("\\\\");
		String[] itm_form_id_arr = req.getParameter("itm_form_ids").split("\\\\");
		
		unityItmVO.setRsm_id(req.getParameter("rsm_id"));
		unityItmVO.setU_id(req.getParameter("u_id"));
		List<UnityItmVO> list = (List<UnityItmVO>) orderSvc.do_searchRev(unityItmVO);
		List<UnityItmVO> getPrd = (List<UnityItmVO>) orderSvc.do_searchOriginal(unityItmVO);
		
		if(list != null && list.size() > 0 ) {
			for(int i=0; i<list.size(); i++) {
				if(itm_title_arr[i] == null || itm_content_arr[i] == null) {
					res.getWriter().write("insufficiency");
					return;
				}
				unityItmVO.setItm_title(itm_title_arr[i]);
				unityItmVO.setItm_content(itm_content_arr[i]);
				unityItmVO.setItm_form_id(Integer.parseInt(itm_form_id_arr[i]));
				unityItmVO.setRsm_id(req.getParameter("rsm_id"));
				flag *= orderSvc.do_updateItem(unityItmVO);
				
			}
		} else {	// 처음 작성인 경우
			for(int i=0; i<getPrd.size(); i++) {
				unityItmVO.setItm_title(itm_title_arr[i]);
				unityItmVO.setItm_content(itm_content_arr[i]);
				unityItmVO.setItm_form_id(Integer.parseInt(itm_form_id_arr[i]));
				unityItmVO.setRsm_id(req.getParameter("rsm_id"));
				unityItmVO.setItm_prd_id((String.valueOf(getPrd.get(i).getItm_form_id())));
				flag *= orderSvc.do_saveFirstTime(unityItmVO);
			}
		}
		
		if(flag > 0) {
			res.getWriter().write("success");
		} else {
			res.getWriter().write("fail");
		}
	}
	
	@RequestMapping(value="/mypage/orders/do_detailOriginal", method = RequestMethod.GET)
	public ModelAndView do_detailOriginal(HttpServletRequest req, HttpServletResponse res) throws IOException {
		UnityItmVO unityItmVO = new UnityItmVO();
		unityItmVO.setRsm_id(req.getParameter("rsm_id"));
		
		List<UnityItmVO> itmList = (List<UnityItmVO>) orderSvc.do_searchOriginal(unityItmVO);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list",itmList);
		modelAndView.setViewName("mypage/orders/order_detail");
		return modelAndView;
	}
	
	@RequestMapping(value="/mypage/orders/do_detailRevision")
	public ModelAndView do_detailRevision(HttpServletRequest req) {
		UnityItmVO unityItmVO = new UnityItmVO();
		ModelAndView modelAndView = new ModelAndView();
		unityItmVO.setRsm_id(req.getParameter("rsm_id"));
		
		List<UnityItmVO> itmList = (List<UnityItmVO>) orderSvc.do_searchRev(unityItmVO);
		if((itmList != null && itmList.size() > 0) && itmList.get(0).getItm_use_yn() == 1) {	// 최초 작성 시..
			modelAndView.addObject("isRev","true");
			modelAndView.addObject("list",itmList);
		} else if((itmList != null && itmList.size() > 0) && itmList.get(0).getItm_use_yn() == 2) {	// 중간 저장한 이후라면..
			modelAndView.addObject("isRev","false");
			modelAndView.addObject("list",itmList);
		} else {
			modelAndView.addObject("isRev","true");
			modelAndView.addObject("list",(List<UnityItmVO>) orderSvc.do_searchOriginal(unityItmVO));
		}
		modelAndView.setViewName("mypage/orders/order_detail");
		return modelAndView;
	}
	
	@RequestMapping(value="/mypage/orders/do_updateUseYN")
	public void do_updateUseYN(HttpServletRequest req, HttpServletResponse res) throws IOException {
		UnityItmVO unityItmVO = new UnityItmVO();
		unityItmVO.setRsm_id(req.getParameter("rsm_id"));
		
		List<UnityItmVO> itmList = (List<UnityItmVO>) orderSvc.do_searchRev(unityItmVO);
		if(itmList.size() == 0 || itmList == null) {
			res.getWriter().write("fail");
		} else {
			for(int i=0; i<itmList.size(); i++) {
				unityItmVO.setItm_form_id(itmList.get(i).getItm_form_id());
				unityItmVO.setItm_use_yn(2);
				orderSvc.do_updateUseYN(unityItmVO);
			}
		}
	}
	
	@RequestMapping(value="/mypage/orders/do_deleteAll.do")
	public void do_deleteAll(HttpServletRequest req, HttpServletResponse res) throws IOException {
		OrdersVO ordersVO = new OrdersVO();
		String chkList[] = req.getParameter("chkList").split("\\\\");
		int flag = 1;
		
		for(int i=0; i<chkList.length; i++) {
			ordersVO.setRsm_id(Integer.parseInt(chkList[i]));
			OrdersVO temp = (OrdersVO) orderSvc.do_searchOne(ordersVO);
			if(!temp.getOrd_state().equals("10")) {
				if(!temp.getOrd_state().equals("50")) {
					flag = -1;
					break;
				}
			}
			flag *= orderSvc.do_deleteTx(ordersVO);
		}
		
		if(flag >0) {
			res.getWriter().write("success");
		} else {
			res.getWriter().write("fail");
		}
	}
}
