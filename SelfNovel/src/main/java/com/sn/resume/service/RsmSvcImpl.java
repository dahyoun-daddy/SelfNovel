package com.sn.resume.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sn.common.DTO;
import com.sn.resume.dao.RsmDao;
import com.sn.user.dao.UserDao;

/**
 * RsmSvcImpl
 * detail : Rsm 서비스 구현클래스
 * 최초작성: 2017-09-25
 * @author MinSeok <dev.edwinner@gmail.com>
 *
 */

@Service
public class RsmSvcImpl implements RsmSvc {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RsmDao rsmDao;	
	
	/**
	 * do_save
	 * detail : 삽입
	 */
	@Override
	public int do_save(DTO dto) {
		log.debug("===== RsmDaoImpl.do_save =====");
		log.debug("dto : " + dto.toString());
		log.debug("==============================");
		return rsmDao.do_save(dto);
	}

	/**
	 * do_update_count
	 * detail : 조회수 증가
	 */
	@Override
	public int do_update_count(DTO dto) {
		log.debug("===== RsmDaoImpl.do_update_count =====");
		log.debug("dto : " + dto.toString());
		log.debug("======================================");
		return rsmDao.do_update_count(dto);
	}

	/**
	 * do_update_recommend
	 * detail : 추천수 증가
	 */
	@Override
	public int do_update_recommend(DTO dto) {
		log.debug("===== RsmDaoImpl.do_update_recommend =====");
		log.debug("dto : " + dto.toString());
		log.debug("==========================================");
		return rsmDao.do_update_recommend(dto);
	}

	/**
	 * do_update
	 * detail : 수정
	 */
	@Override
	public int do_update(DTO dto) {
		log.debug("===== RsmDaoImpl.do_update =====");
		log.debug("dto : " + dto.toString());
		log.debug("================================");
		return rsmDao.do_update(dto);
	}

	/**
	 * do_delete
	 * detail : 삭제
	 */
	@Override
	public int do_delete(DTO dto) {
		log.debug("===== RsmDaoImpl.do_delete =====");
		log.debug("dto : " + dto.toString());
		log.debug("================================");
		return rsmDao.do_delete(dto);
	}

	/**
	 * do_search
	 * detail : 조회
	 */
	@Override
	public List<?> do_search(DTO dto) {
		log.debug("===== RsmDaoImpl.do_search =====");
		log.debug("dto : " + dto.toString());
		log.debug("================================");
		return rsmDao.do_search(dto);
	}

	/**
	 * do_searchOne
	 * detail : 단건조회
	 */
	@Override
	public DTO do_searchOne(DTO dto) {
		log.debug("===== RsmDaoImpl.do_searchOne =====");
		log.debug("dto : " + dto.toString());
		log.debug("===================================");
		return rsmDao.do_searchOne(dto);
	}

}
