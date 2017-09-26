package com.sn.log.dao;

import java.util.List;
import java.util.UUID;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sn.common.DTO;
import com.sn.log.domain.LogVO;

/**
 * LogDaoImpl 
 * detail : 로그 Dao 구현체
 * 최초작성: 2017-09-22
 * @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
@Repository
public class LogDaoImpl implements LogDao {
	/***********************************************/
	//field
	/***********************************************/
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SqlSessionTemplate sqlSession;
	private final String namespace = "com.sn.logs.repository.mappers.logs";
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

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
		//아래의 이름으로 codes.xml에서 해당하는 select를 불러온다.
		String statement = namespace +".do_save";
		
		log.debug("in do_save========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
		
		LogVO inUserVO = (LogVO)dto;
		
		UUID uId= UUID.randomUUID();		
		inUserVO.setLog_id(uId.toString());
		
		log.debug("inUserVO: "+inUserVO.toString());
		
		return sqlSession.insert(statement, inUserVO);
	}

	  /**
	   * do_search
	   * detail: 다건조회
	   * @param dto
	   * @return list
	   */
	@Override
	public List<?> do_search(DTO dto) {
		//아래의 이름으로 codes.xml에서 해당하는 select를 불러온다.
		String statement = namespace +".do_search";
		
		log.debug("in do_search========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
			
		return sqlSession.selectList(statement,dto.getParam());
	}

	@Override
	public int do_delete(DTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int do_update(DTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DTO do_searchOne(DTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
