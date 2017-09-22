package com.sn.orders.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sn.common.DTO;
import com.sn.orders.dao.OrdersDao;

@Service
public class OrdersSvcImpl implements OrdersSvc {
	@Autowired
	OrdersDao ordersDao;
	
	@Override
	public List<?> do_search(DTO dto) {
		return ordersDao.do_search(dto);
	}

	@Override
	public int do_nextState(DTO dto) {
		return ordersDao.do_nextState(dto);
	}

}
