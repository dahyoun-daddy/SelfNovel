package com.sn.resume.dao;

import com.sn.common.DTO;
import com.sn.common.WorkDiv;

public interface RsmDao extends WorkDiv {
	/**
	 * do_update_recommend
	 * detail : 추천수 증가
	 * @param dto
	 * @return
	 */
	public int do_update_recommend(DTO dto);
	/**
	 * do_update_count
	 * detail : 조회수 증가
	 * @param dto
	 * @return
	 */
	public int do_update_count(DTO dto);
	/**
	 * do_getNextVal
	 * detail : 조회수 증가
	 * 
	 * date: 2017-10-10
	 * @author pinkbean
	 * 
	 * @param dto
	 * @return
	 */
	public String do_getNextVal();
	
}
