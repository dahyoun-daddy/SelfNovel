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
		String statement = namespace +".do_save";//resume.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;           //파라미터 주입				
		return sqlSession.insert(statement, inItmVO);		
	}
	
	/**
	 * do_save_edit
	 * detail : 첨삭 저장
	 */
	@Override
	public int do_save_edit(DTO dto) {
		String statement = namespace +".do_save_edit";//resume.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;           //파라미터 주입				
		return sqlSession.insert(statement, inItmVO);		
	}	

	/**
	 * do_search
	 * detail : 검색  
	 */
	@Override
	public List<?> do_search(DTO dto) {
		String statement = namespace +".do_search";//resume.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;             //파라미터 주입
		return sqlSession.selectList(statement, inItmVO);
	}
	
	/**
	 * do_search_child
	 * detail : 하위항목 검색
	 */	
	public List<?> do_search_child(DTO dto) {
		String statement = namespace +".do_search_child";//resume.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;             //파라미터 주입
		return sqlSession.selectList(statement, inItmVO);
	}

	/**
	 * do_delete
	 * detail : 삭제
	 */
	@Override
	public int do_delete(DTO dto) {
		String statement = namespace +".do_delete";//resume.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;           //파라미터 주입
		return sqlSession.update(statement, inItmVO);
	}

	/**
	 * do_update
	 * detail : 수정
	 */
	@Override
	public int do_update(DTO dto) {
		String statement = namespace +".do_delete";//resume.xml연결
		ItmVO  inItmVO   = (ItmVO)dto;           //파라미터 주입
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

}
