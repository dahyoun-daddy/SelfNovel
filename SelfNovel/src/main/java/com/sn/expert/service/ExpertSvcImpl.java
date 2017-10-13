package com.sn.expert.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sn.common.DTO;
import com.sn.expert.dao.ExpertDao;
import com.sn.user.dao.UserDao;
import com.sn.user.service.UserSvcImpl;

@Service
public class ExpertSvcImpl implements ExpertSvc{
	private static Logger log = LoggerFactory.getLogger(ExpertSvcImpl.class);

	@Autowired
	private ExpertDao expertDao;

	@Override
	public int do_save(DTO dto) throws DataAccessException {
		log.debug("2=======================");
		log.debug(dto.toString());
		log.debug("2=======================");
		return expertDao.do_save(dto);
	}

	@Override
	public int do_delete(DTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int do_update(DTO dto) {
		log.debug("2=======================");
		log.debug(dto.toString());
		log.debug("2=======================");	
		return expertDao.do_update(dto);
	}

	@Override
	public DTO do_searchOne(DTO dto) throws IOException {
		log.debug("2=======================");
		log.debug(dto.toString());
		log.debug("2=======================");	
		return expertDao.do_searchOne(dto);
	}

	@Override
	public List<?> do_search(DTO dto) {
		log.debug("2=======================");
		log.debug(dto.toString());
		log.debug("2=======================");	
		return expertDao.do_search(dto);
	}
	
	@Override
	public DTO do_chkId(DTO dto) {
		log.debug("2=======================");
		log.debug(dto.toString());
		log.debug("2=======================");	
		return expertDao.do_chkId(dto);
	}
	
	public List<?> do_searchRank() {
		log.debug("2=======================");
		log.debug("2=======================");	
		return expertDao.do_searchRank();
	}

	@Override
	public List<?> do_searchDetail_itm(DTO dto) {
		log.debug("2=======================");
		log.debug("2=======================");	
		log.debug("asdf: " + dto.toString());
		return expertDao.do_searchDetail_itm(dto);
	}

	@Override
	public DTO do_searchDetail_rsm(DTO dto) {
		log.debug("2=======================");
		log.debug("2=======================");	
		return expertDao.do_searchDetail_rsm(dto);
	}

	@Override
	public List<?> do_searchDetail(DTO dto) {
		log.debug("2=======================");
		log.debug("2=======================");	
		return expertDao.do_searchDetail(dto);
	}

	@Override
	public DTO do_chkNaver(DTO dto) {
		log.debug("2=======================");
		log.debug(dto.toString());
		log.debug("2=======================");	
		return expertDao.do_chkNaver(dto);
	}
}
