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
	  public int do_save(DTO dto);	
	  public List<?> do_search(DTO dto);	
	  public int do_delete(DTO dto);	
	  public int do_update(DTO dto);
	  public DTO do_searchOne(DTO dto);
}
