package com.sn.resume.service;

import java.util.List;

import com.sn.common.DTO;
import com.sn.common.WorkDiv;

public interface ItmSvc extends WorkDiv {
	public int do_save_edit(DTO dto);
	public List<?> do_search_child(DTO dto);
}
