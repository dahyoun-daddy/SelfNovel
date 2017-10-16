package com.sn.expert.dao;

import java.util.List;

import com.sn.common.DTO;
import com.sn.common.WorkDiv;

public interface ExpertDao extends WorkDiv {
	public DTO do_chkId(DTO dto);
	public List<?> do_searchRank();
	public List<?> do_searchDetail_itm(DTO dto);
	public DTO do_searchDetail_rsm(DTO dto);
	public List<?> do_searchDetail(DTO dto);
	public DTO do_chkNaver(DTO dto);
	public int do_saveOrder(DTO dto);
	public DTO do_nextSeq();
}	
