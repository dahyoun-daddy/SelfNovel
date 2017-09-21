package com.sn.order.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sn.codes.domain.CodesVO;
import com.sn.common.DTO;
import com.sn.order.domain.OrderVO;

/**
 * OrderDaoImpl 
 * detail : 의뢰테이블 dao 인터페이스 
 * 최초작성: 2017-09-21
 * @author @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public class OrderDaoImpl implements OrderDao {
	/***********************************************/
	//field
	/***********************************************/
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SqlSessionTemplate sqlSession;
	private final String namespace = "com.sn.order.repository.mappers.order";
	
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
		
		OrderVO inUserVO = (OrderVO)dto;
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
		
		OrderVO inUserVO = (OrderVO)dto;
		return sqlSession.selectList(statement, inUserVO);
	}

	  /**
	   * do_delete
	   * detail: 삭제
	   * @param dto
	   * @return flag
	   */
	@Override
	public int do_delete(DTO dto) {
		//아래의 이름으로 codes.xml에서 해당하는 select를 불러온다.
		String statement = namespace +".do_delete";
		
		log.debug("in do_delete========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
		
		OrderVO inUserVO = (OrderVO)dto;
		return sqlSession.delete(statement, inUserVO);
	}

	  /**
	   * do_update
	   * detail: 수정
	   * @param dto
	   * @return flag
	   */
	@Override
	public int do_update(DTO dto) {
		//아래의 이름으로 codes.xml에서 해당하는 select를 불러온다.
		String statement = namespace +".do_update";
		
		log.debug("in do_delete========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
		
		OrderVO inUserVO = (OrderVO)dto;
		return sqlSession.update(statement, inUserVO);
	}

	  /**
	   * do_searchOne
	   * detail: 단건조회
	   * @param dto
	   * @return dto
	   */
	@Override
	public DTO do_searchOne(DTO dto) {
		//아래의 이름으로 codes.xml에서 해당하는 select를 불러온다.
		String statement = namespace +".do_update";
		
		log.debug("in do_delete========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
		
		OrderVO inUserVO = (OrderVO)dto;
		return sqlSession.selectOne(statement, inUserVO);
	}

}
