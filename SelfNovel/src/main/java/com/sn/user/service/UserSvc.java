package com.sn.user.service;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.sn.common.DTO;

public interface UserSvc {
	int do_save(DTO dto) throws DataAccessException;
	int do_delete(DTO dto);
	int do_update(DTO dto);
	DTO do_searchOne(DTO dto) throws IOException;
	List<?> do_search(DTO dto);
	DTO do_chkId(DTO dto);
	
	/**
	 * 다중 유저 강제탈퇴
	 * 최초작성: 2017-09-29
	 * 작성자: 이슬기
	 * @param dto
	 * @return
	 */
	int do_deleteTx(List<?> list);
}
