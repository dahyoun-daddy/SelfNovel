package com.sn.resume.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sn.common.DTO;
import com.sn.resume.dao.ItmDao;

/**
 * ItmSvcImpl
 * detail : Itm 서비스 구현클래스
 * 최초작성 : 2017-09-27
 * @author MinSeok <dev.edwinner@gmail.com>
 *
 */

@Service
public class ItmSvcImpl implements ItmSvc {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItmDao itmDao;
	
	/**
	 * 삽입
	 */
	@Override
	public int do_save(DTO dto) {
		log.debug("===== ItmDaoImpl.do_save =====");
		log.debug("dto : " + dto.toString());
		log.debug("==============================");
		return itmDao.do_save(dto);
	}

	/**
	 * 조회
	 */
	@Override
	public List<?> do_search(DTO dto) {
		log.debug("===== ItmDaoImpl.do_search =====");
		log.debug("dto : " + dto.toString());
		log.debug("================================");
		return itmDao.do_search(dto);
	}

	/**
	 * 삭제
	 */
	@Override
	public int do_delete(DTO dto) {
		log.debug("===== ItmDaoImpl.do_delete =====");
		log.debug("dto : " + dto.toString());
		log.debug("================================");
		return itmDao.do_delete(dto);
	}

	/**
	 * 수정
	 */
	@Override
	public int do_update(DTO dto) {
		log.debug("===== ItmDaoImpl.do_update =====");
		log.debug("dto : " + dto.toString());
		log.debug("================================");
		return itmDao.do_update(dto);
	}

	//Do not use
	@Override
	public DTO do_searchOne(DTO dto) {
		//Don't use it
		return null;
	}

}
