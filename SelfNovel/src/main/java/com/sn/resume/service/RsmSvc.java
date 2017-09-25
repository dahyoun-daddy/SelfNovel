package com.sn.resume.service;

import java.util.List;

import com.sn.common.DTO;

/**
 * RsmSvc
 * detail : Rsm서비스 인터페이스
 * 최초작성: 2017-09-25
 * @author MinSeok <dev.edwinner@gmail.com> 
 */
public interface RsmSvc {
	
	/**
	 * do_save
	 * detail : 삽입
	 * @param dto
	 * @return int (1:성공, other:실패)
	 */
	public int do_save(DTO dto);
	
	/**
	 * do_update
	 * detail : 수정
	 * @param dto
	 * @return int (1:성공, other:실패)
	 */
	public int do_update_count(DTO dto);
	
	/**
	 * do_update_count
	 * detail : 조회수 증가
	 * @param dto
	 * @return int (1:성공, other:실패)
	 */
	public int do_update_recommend(DTO dto);
	
	/**
	 * do_update_recommend
	 * detail : 추천수 증가
	 * @param dto
	 * @return int (1:성공, other:실패)
	 */
	public int do_update(DTO dto);
	
	/**
	 * do_delete
	 * detail : 삭제
	 * @param dto
	 * @return int (1:성공, other:실패)
	 */
	public int do_delete(DTO dto);
	
	/**
	 * do_search
	 * detail전체 조회 
	 * @param dto
	 * @return List<RsmVO>
	 */
	public List<?> do_search(DTO dto);
	
	/**
	 * do_searchOne 
	 * detail : 단건 조회
	 * @param dto(id = ?)
	 * @return RsmVO
	 */
	public DTO do_searchOne(DTO dto);	
}
