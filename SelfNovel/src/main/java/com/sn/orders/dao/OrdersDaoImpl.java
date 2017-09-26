package com.sn.orders.dao;

import java.util.Hashtable;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sn.codes.domain.CodesVO;
import com.sn.common.DTO;
import com.sn.common.StringUtil;
import com.sn.orders.domain.OrdersVO;

/**
 * OrdersDaoImpl 
 * detail : 의뢰테이블 dao 인터페이스 
 * 최초작성: 2017-09-21
 * 최종수정: 2017-09-22
 * @author @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
@Repository
public class OrdersDaoImpl implements OrdersDao {
	/***********************************************/
	//field
	/***********************************************/
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private SqlSessionTemplate sqlSession;
	private final String namespace = "com.sn.orders.repository.mappers.orders";
	
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
		
		OrdersVO inUserVO = (OrdersVO)dto;
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
		
		//필요 parameter: 
		//SEARCH_DIV: exp/user
		//SEARCH_ID	: 입력 아이디
		//PAGE_SIZE	: 기본값 10
		//PAGE_NUM	: 기본값 1		
		OrdersVO inUserVO = (OrdersVO)dto;
		Hashtable<String, String> param = inUserVO.getParam();
		param.put("SEARCH_DIV",StringUtil.nvl(param.get("SEARCH_DIV"), "user"));
		param.put("SEARCH_ID",StringUtil.nvl(param.get("SEARCH_ID"), "noUser"));
		param.put("PAGE_SIZE",StringUtil.nvl(param.get("PAGE_SIZE"), "10"));
		param.put("PAGE_NUM",StringUtil.nvl(param.get("PAGE_NUM"), "1"));
		
		log.debug("in dto param: "+inUserVO.getParam().toString());		
		return sqlSession.selectList(statement, param);
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
		
		OrdersVO inUserVO = (OrdersVO)dto;
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
		
		OrdersVO inUserVO = (OrdersVO)dto;
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
		String statement = namespace +".do_searchOne";
		
		log.debug("in do_delete========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
		
		OrdersVO inUserVO = (OrdersVO)dto;
		return sqlSession.selectOne(statement, inUserVO);
	}

	/**
	 * do_nextState
	 * detail: 상태를 다음 값으로 업데이트시켜주는 메소드. sql함수를 사용한다. 최종단계에서 시도할 경우 최종단계를 반환한다. 정해진 값 이외를 입력하면 null을 반환한다.
	 * 필요값: rsm_id, exp_id
	 * 
	 * 최초작성: 2017-09-22
	 * @param dto
	 * @return flag
	 */
	@Override
	public int do_nextState(DTO dto) {
		//아래의 이름으로 codes.xml에서 해당하는 select를 불러온다.
		String statement = namespace +".do_nextState";
		
		log.debug("in do_nextState========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
		
		OrdersVO inUserVO = (OrdersVO)dto;
		return sqlSession.update(statement, inUserVO);		
	}
	
	/**
	 * do_reject
	 * detail: 거절한다.
	 * 
	 * 최초작성: 2017-09-22
	 * @param dto
	 * @return
	 */
	public int do_reject(DTO dto) {
		//아래의 이름으로 codes.xml에서 해당하는 select를 불러온다.
		String statement = namespace +".do_reject";
		
		log.debug("in do_nextState========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
		
		OrdersVO inUserVO = (OrdersVO)dto;
		return sqlSession.update(statement, inUserVO);		
	}
}
