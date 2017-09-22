package com.sn.orders.controller;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sn.orders.domain.OrdersVO;
import com.sn.orders.service.OrdersSvc;

@Controller
public class OrdersController {
	/***********************************************/
	//field
	/***********************************************/
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	OrdersSvc orderSvc;
	
	@RequestMapping(value="/mypage/orders/list.do")
	public ModelAndView main(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		//임시 dto
		Hashtable<String, String> param = new Hashtable<String, String>();
//		param.put("SEARCH_DIV", "exp");
//		param.put("SEARCH_ID", "exp1"); 
		param.put("SEARCH_DIV", "user");
		param.put("SEARCH_ID", "u1");		
		OrdersVO dto = new OrdersVO();
		dto.setParam(param);
		log.debug("in controller param: "+param.toString());
		
		modelAndView.addObject("ordersList",orderSvc.do_search(dto));
		modelAndView.setViewName("mypage/orders/orders_list");
		return modelAndView;
	}
	
	@RequestMapping(value="/mypage/orders/levelUpTest.do")
	public String levelUpTest(HttpServletRequest request) {
		String rsm_id = request.getParameter("rsm_id");
		String exp_id = request.getParameter("exp_id");
		OrdersVO dto = new OrdersVO();
		dto.setRsm_id(Integer.parseInt(rsm_id));
		dto.setExp_id(exp_id);
		orderSvc.do_nextState(dto);
		
		return "redirect:list.do";
	}
}
