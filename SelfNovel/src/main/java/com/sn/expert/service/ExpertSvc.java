package com.sn.expert.service;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.sn.common.DTO;

public interface ExpertSvc {
	int do_save(DTO dto) throws DataAccessException;
	int do_delete(DTO dto);
	int do_update(DTO dto);
	DTO do_searchOne(DTO dto) throws IOException;
	List<?> do_search(DTO dto);
	DTO do_chkId(DTO dto);
	List<?> do_searchRank();
	List<?> do_searchDetail_itm(DTO dto);
	DTO do_searchDetail_rsm(DTO dto);
	List<?> do_searchDetail(DTO dto);
	DTO do_chkNaver(DTO dto);
	int do_saveOrder(DTO dto);
	DTO do_nextSeq();
	int do_updateTrade(DTO dto);
}
