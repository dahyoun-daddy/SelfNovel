package com.sn.user.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sn.common.DTO;
import com.sn.user.dao.UserDao;
import com.sn.user.domain.UserVO;

@Service
public class UserSvcImpl implements UserSvc {
	private static Logger log = LoggerFactory.getLogger(UserSvcImpl.class);

	@Autowired
	private UserDao userDao;
	
	@Override
	public int do_save(DTO dto) throws DataAccessException {
		log.debug("2=======================");
		log.debug(dto.toString());
		log.debug("2=======================");
		return userDao.do_save(dto);
	}

	@Override
	public int do_delete(DTO dto) {
		log.debug("2=======================");
		log.debug(dto.toString());
		log.debug("2=======================");	
		return userDao.do_delete(dto);
	}

	@Override
	public int do_update(DTO dto) {
		log.debug("2=======================");
		log.debug(dto.toString());
		log.debug("2=======================");	
		return userDao.do_update(dto);
	}

	@Override
	public DTO do_searchOne(DTO dto) throws IOException {
		log.debug("2=======================");
		log.debug(dto.toString());
		log.debug("2=======================");	
		return userDao.do_searchOne(dto);
	}

	@Override
	public List<?> do_search(DTO dto) {
		log.debug("2=======================");
		log.debug("2=======================");	
		return userDao.do_search(dto);
	}
	
	@Override
	public DTO do_chkId(DTO dto) {
		log.debug("2=======================");
		log.debug(dto.toString());
		log.debug("2=======================");	
		return userDao.do_chkId(dto);
	}
	
	/**
	 * 다중 유저 강제탈퇴
	 * 최초작성: 2017-09-29
	 * 작성자: 이슬기
	 * @param dto
	 * @return
	 */
	public int do_deleteTx(List<?> list) {
		for(int i=0;i<list.size();i++) {
			UserVO dto = (UserVO)list.get(i);
			userDao.do_delete(dto);
		}
		return list.size();
	}
}
