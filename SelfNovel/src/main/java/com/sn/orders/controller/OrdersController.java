package com.sn.orders.controller;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sn.common.StringUtil;
import com.sn.orders.domain.OrdersVO;
import com.sn.orders.service.OrdersSvc;

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
	 * ModelAndView
	 * detail: main list view
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/mypage/orders/list.do")
	public ModelAndView main(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
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
			orderSvc.do_delete(dto);
		}

		return "redirect:list.do";
	}
}
