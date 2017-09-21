package com.sn.resume.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sn.common.DTO;
import com.sn.resume.domain.RsmVO;

/**
 * RsmDaoImpl
 * detail : 자소서  테이블 DaoImpl
 * @author MinSeok <dev.edwinner@gmail.com>
 *
 */
@Repository
public class RsmDaoImpl implements RsmDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String namespace ="com.sn.resume.repository.mappers.resume";
	
	/**
	 * 저장기능
	 */
	@Override
	public int do_save(DTO dto) {
		String statement = namespace +".do_save";//resume.xml연결
		RsmVO  inRsmVO   = (RsmVO)dto;           //파라미터 주입
				
		return sqlSession.insert(statement, inRsmVO);
	}
	
	/**
	 * 전체 조회 및 검색기능
	 */
	@Override
	public List<?> do_search(DTO dto) {
		// TODO
		return null;
	}

	/**
	 * 삭제기능
	 */
	@Override
	public int do_delete(DTO dto) {
		String statement = namespace +".do_delete";//resume.xml연결
		RsmVO  inRsmVO   = (RsmVO)dto;             //파라미터 주입
				
		return sqlSession.insert(statement, inRsmVO);
	}

	/**
	 * 수정기능
	 */
	@Override
	public int do_update(DTO dto) {
		String statement = namespace +".do_update";//resume.xml연결
		RsmVO  inRsmVO   = (RsmVO)dto;             //파라미터 주입
				
		return sqlSession.insert(statement, inRsmVO);
	}

	/**
	 * 단건조회 기능
	 */
	@Override
	public DTO do_searchOne(DTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
