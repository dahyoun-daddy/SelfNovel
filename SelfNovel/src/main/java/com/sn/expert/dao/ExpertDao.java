package com.sn.expert.dao;

import java.util.List;

import com.sn.common.DTO;
import com.sn.common.WorkDiv;

public interface ExpertDao extends WorkDiv {
	public DTO do_chkId(DTO dto);
	public List<?> do_searchRank();
}	
