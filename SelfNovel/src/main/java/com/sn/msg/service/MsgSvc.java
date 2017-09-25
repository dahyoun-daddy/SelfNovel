package com.sn.msg.service;

import java.util.List;

import com.sn.common.DTO;

/**
 * MsgSvc 
 * detail : 메시지 Svc 인터페이스
 * 최초작성: 2017-09-25
 * 최종수정: 2017-09-25
 * @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public interface MsgSvc {
	  /**
	   * do_save
	   * detail: 저장
	   * @param dto
	   * @return flag
	   */
	public int do_save(DTO dto);
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
}
