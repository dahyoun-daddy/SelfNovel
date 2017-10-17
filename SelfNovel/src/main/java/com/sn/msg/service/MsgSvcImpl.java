package com.sn.msg.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sn.common.DTO;
import com.sn.msg.dao.MsgDao;
/**
 * MsgSvcImpl 
 * detail : 메시지 Svc 구현체
 * 최초작성: 2017-09-25
 * 최종수정: 2017-09-25
 * @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
@Service
public class MsgSvcImpl implements MsgSvc {
	/***********************************************/
	//field
	/***********************************************/
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MsgDao msgDao;
	
	/***********************************************/
	//method
	/***********************************************/
	  /**
	   * do_save
	   * detail: 저장
	   * @param dto
	   * @return flag
	   */
	@Override
	public int do_save(DTO dto) {
		log.info("===== MsgSvc/do_save.do =====");
		log.info("req : " + dto.toString());		
		log.info("====================================");		
		return msgDao.do_save(dto);
	}
	  /**
	   * do_search
	   * detail: 다건조회
	   * @param dto
	   * @return list
	   */
	@Override
	public List<?> do_search(DTO dto) {
		return msgDao.do_search(dto);
	}
	  /**
	   * do_delete
	   * detail: 삭제
	   * @param dto
	   * @return flag
	   */
	@Override
	public int do_delete(DTO dto) {
		return msgDao.do_delete(dto);
	}
	  /**
	   * do_read
	   * detail: 읽음 플래그 off. 단건이지만 다건이 필요할 경우 별도로 추가해도 괜찮다고 생각한다.
	   * @param dto
	   * @return flag
	   */
	@Override
	public int do_read(DTO dto) {
		return msgDao.do_read(dto);
	}
	  /**
	   * do_count
	   * detail: 안읽은 메시지 수 조회
	   * @param dto
	   * @return flag
	   */
	@Override
	public int do_count(DTO dto) {
		return msgDao.do_count(dto);
	}	
}
