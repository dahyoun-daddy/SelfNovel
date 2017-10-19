package com.sn.orders.dao;

import java.util.List;

import com.sn.common.DTO;
import com.sn.common.WorkDiv;

/**
 * OrdersDao 
 * detail : 의뢰테이블 dao 인터페이스 
 * 최초작성: 2017-09-21
 * @author @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public interface OrdersDao extends WorkDiv {

	/**
	 * do_nextState
	 * detail: 상태를 다음 값으로 업데이트시켜주는 메소드. sql함수를 사용한다. 최종단계에서 시도할 경우 최종단계를 반환한다. 정해진 값 이외를 입력하면 null을 반환한다.
	 * 
	 * 최초작성: 2017-09-22
	 * @param dto
	 * @return
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
	
	/**
	 * 의뢰하기 및 첨삭하기 관련 메소드
	 * @param dto
	 * @return
	 */
	public List<?> do_searchOriginal(DTO dto);
	public List<?> do_searchRev(DTO dto);
	public int do_saveFirstTime(DTO dto);
	public int do_updateItem(DTO dto);
	public int do_updateUseYN(DTO dto);
	public DTO do_searchOne(DTO dto);
}
