package com.sn.resume.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sn.common.DTO;
import com.sn.resume.domain.ItmVO;

/**
 * ItmDaoImpl
 * detail : 항목 테이블 DaoImpl
 * @author MinSeok <dev.edwinner@gmail.com>
 *
 */
@Repository
public class ItmDaoImpl implements ItmDao {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String namespace ="com.sn.item.repository.mappers.item";

	/**
	 * do_save
	 * detail : 일반 저장
	 */
	@Override
	public int do_save(DTO dto) {
		String statement = namespace +".do_save";//item.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;           //파라미터 주입				
		return sqlSession.insert(statement, inItmVO);		
	}
	
	/**
	 * do_save_edit
	 * detail : 첨삭 저장
	 */
	@Override
	public int do_save_edit(DTO dto) {
		String statement = namespace +".do_save_edit";//item.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;           //파라미터 주입				
		return sqlSession.insert(statement, inItmVO);		
	}	

	/**
	 * do_search
	 * detail : 검색  
	 */
	@Override
	public List<?> do_search(DTO dto) {
		String statement = namespace +".do_search";//item.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;             //파라미터 주입
		return sqlSession.selectList(statement, inItmVO);
	}
	
	/**
	 * do_search_child
	 * detail : 하위항목 검색
	 */	
	public List<?> do_search_child(DTO dto) {
		String statement = namespace +".do_search_child";//item.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;             //파라미터 주입
		return sqlSession.selectList(statement, inItmVO);
	}

	/**
	 * do_delete
	 * detail : 삭제
	 */
	@Override
	public int do_delete(DTO dto) {
		String statement = namespace +".do_delete";//item.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;           //파라미터 주입
		return sqlSession.update(statement, inItmVO);
	}

	/**
	 * do_update
	 * detail : 수정
	 */
	@Override
	public int do_update(DTO dto) {
		String statement = namespace +".do_update";//item.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;           //파라미터 주입	
		
		return sqlSession.update(statement, inItmVO);
	}
	
	/**
	 * do_updateOne
	 * detail : 단건 수정
	 */
	@Override
	public int do_updateOne(DTO dto) {
		String statement = namespace +".do_updateOne";//Item.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;           	  //파라미터 주입
		return sqlSession.update(statement, inItmVO);
	}

	/**
	 * 단건 조회
	 */
	@Override
	public DTO do_searchOne(DTO dto) {
		//DO NOT USE
		return null;
	}

	@Override
	public int do_deleteAllRoot(DTO dto) {
		String statement = namespace + ".do_deleteAllRoot";
		ItmVO inItmVO = (ItmVO) dto;		
		
		return sqlSession.update(statement, inItmVO);
	}
	
	/**
	 * do_search_order
	 * detail: order에서 사용되는 조회
	 * 최초작성: 2017-10-16
	 * 
	 * @author LSG
	 * @param dto
	 * @return
	 */
	@Override
	public List<?> do_search_order(DTO dto) {
		String statement = namespace +".do_search_order";//item.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;             //파라미터 주입
		return sqlSession.selectList(statement, inItmVO);		
	}
}

