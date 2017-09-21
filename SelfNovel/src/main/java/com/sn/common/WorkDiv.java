package com.sn.common;

import java.util.List;

/**
 * WorkDiv.java
 * 거래 표준 메소드 정의
 * do_save		: 저장
 * do_search	: 조회
 * do_delete	: 삭제
 * do_update	: 수정
 * do_searchOne : 단건조회
 * 
 * 최초작성: 2017-09-21
 * @author sist
 *
 */
public interface WorkDiv {
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
	   * do_update
	   * detail: 수정
	   * @param dto
	   * @return flag
	   */
	  public int do_update(DTO dto);
	  /**
	   * do_searchOne
	   * detail: 단건조회
	   * @param dto
	   * @return dto
	   */
	  public DTO do_searchOne(DTO dto);
}
