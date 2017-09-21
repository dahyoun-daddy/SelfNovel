package com.sn.codes.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sn.codes.domain.CodesVO;
import com.sn.common.DTO;

@Repository
public class CodesDaoImpl implements CodesDao {
	/***********************************************/
	//field
	/***********************************************/
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SqlSessionTemplate sqlSession;
	private final String namespace = "com.sn.codes.repository.mappers.codes";

	@Override
	public int do_save(DTO dto) {
		//아래의 이름으로 codes.xml에서 해당하는 select를 불러온다.
		String statement = namespace +".do_save";
		
		log.debug("in do_save========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
		
		CodesVO inUserVO = (CodesVO)dto;
		return sqlSession.insert(statement, inUserVO);
	}

	@Override
	public List<?> do_search(DTO dto) {
		// TODO Auto-generated method stub
		return null;
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
