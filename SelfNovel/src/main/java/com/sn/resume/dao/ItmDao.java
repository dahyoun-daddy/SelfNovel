package com.sn.resume.dao;

import java.util.List;

import com.sn.common.DTO;
import com.sn.common.WorkDiv;

public interface ItmDao extends WorkDiv {
	public int do_save_edit(DTO dto);
	public List<?> do_search_child(DTO dto);
	public int do_deleteAllRoot(DTO dto);
	public int do_updateOne(DTO dto);
	/**
	 * do_search_order
	 * detail: order에서 사용되는 조회
	 * 최초작성: 2017-10-16
	 * 
	 * @author LSG
	 * @param dto
	 * @return
	 */
	public List<?> do_search_order(DTO dto);
}
