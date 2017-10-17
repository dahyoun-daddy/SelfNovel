package com.sn.msg.dao;

import java.util.List;

import com.sn.common.DTO;
import com.sn.common.WorkDiv;
/**
 * MsgDao 
 * detail : 메시지 Dao 구현체
 * 최초작성: 2017-09-25
 * 최종수정: 2017-09-25
 * @author @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public interface MsgDao extends WorkDiv {
	  /**
	   * do_read
	   * detail: 읽음 플래그 off
	   * @param dto
	   * @return flag
	   */
	public int do_read(DTO dto);
	  /**
	   * do_count
	   * detail: 안읽은 메시지 수 조회
	   * @param dto
	   * @return flag
	   */
	public int do_count(DTO dto);
	
	/**
	 * do_searchReport
	 * detail : 신고목록을 조회
	 * @param dto
	 * @return List<DTO>
	 */
	public List<?> do_searchReport(DTO dto);
}
