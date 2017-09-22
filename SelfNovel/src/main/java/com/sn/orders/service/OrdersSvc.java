package com.sn.orders.service;

import java.util.List;

import com.sn.common.DTO;
import com.sn.orders.domain.OrdersVO;

/**
 * OrdersSvc 
 * detail : 의뢰테이블 svc 인터페이스 
 * 최초작성: 2017-09-21
 * 최종수정: 2017-09-22
 * @author @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public interface OrdersSvc {
	  /**
	   * do_search
	   * detail: 다건조회
	   * @param dto
	   * @return list
	   */
	public List<?> do_search(DTO dto);
	  /**
	   * do_delete
	   * detail: 삭제
	   * @param dto
	   * @return flag
	   */
	public int do_delete(DTO dto);
	
	  /**
	   * do_nextState
	   * detail: 의뢰 상태를 다음 단계로 업데이트시킴
	   * @param dto
	   * @return list
	   */
	public int do_nextState(DTO dto);
	/**
	 * do_reject
	 * detail: 거절한다.
	 * 
	 * 최초작성: 2017-09-22
	 * @param dto
	 * @return
	 */
	public int do_reject(DTO dto);
}
