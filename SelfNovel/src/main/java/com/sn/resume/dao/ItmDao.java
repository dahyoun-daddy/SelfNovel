package com.sn.resume.dao;

import java.util.List;

import com.sn.common.DTO;
import com.sn.common.WorkDiv;

public interface ItmDao extends WorkDiv {
	public int do_save_edit(DTO dto);
	public List<?> do_search_child(DTO dto);
	public int do_deleteAllRoot(DTO dto);
}
