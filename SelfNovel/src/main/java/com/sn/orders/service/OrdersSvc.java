package com.sn.orders.service;

import java.util.List;

import com.sn.common.DTO;
import com.sn.orders.domain.OrdersVO;

public interface OrdersSvc {
	  /**
	   * do_search
	   * detail: 다건조회
	   * @param dto
	   * @return list
	   */
	public List<?> do_search(DTO dto);
	  /**
	   * do_search
	   * detail: 다건조회
	   * @param dto
	   * @return list
	   */
	public int do_nextState(DTO dto);
}
