package com.sn.orders.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sn.common.DTO;
import com.sn.orders.dao.OrdersDao;

/**
 * OrdersSvcImpl 
 * detail : 의뢰테이블 svc 구현체 
 * 최초작성: 2017-09-21
 * 최종수정: 2017-09-22
 * @author @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
@Service
public class OrdersSvcImpl implements OrdersSvc {
	@Autowired
	OrdersDao ordersDao;
	
	
	  /**
	   * do_saveTx
	   * detail: 저장
	   * @param dto
	   * @return list
	   */
	public int do_saveTx(DTO dto){
		return ordersDao.do_save(dto);
	}
	  /**
	   * do_search
	   * detail: 다건조회
	   * @param dto
	   * @return list
	   */
	@Override
	public List<?> do_search(DTO dto) {
		return ordersDao.do_search(dto);
	}
	
	  /**
	   * do_delete
	   * detail: 삭제
	   * @param dto
	   * @return flag
	   */
	@Override
	public int do_deleteTx(DTO dto) {
		return ordersDao.do_delete(dto);
	}

	  /**
	   * do_nextState
	   * detail: 의뢰 상태를 다음 단계로 업데이트시킴
	   * @param dto
	   * @return list
	   */
	@Override
	public int do_nextState(DTO dto) {
		return ordersDao.do_nextState(dto);
	}
	
	/**
	 * do_reject
	 * detail: 거절한다.
	 * 
	 * 최초작성: 2017-09-22
	 * @param dto
	 * @return
	 */
	public int do_reject(DTO dto) {
		return ordersDao.do_reject(dto);
	}
}
