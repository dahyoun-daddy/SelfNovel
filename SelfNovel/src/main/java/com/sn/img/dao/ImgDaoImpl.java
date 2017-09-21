package com.sn.img.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sn.common.DTO;
import com.sn.img.domain.ImgVO;

@Repository
public class ImgDaoImpl implements ImgDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String namespace = "com.sn.codes.repository.mappers.codes";
	
	@Override
	public int do_save(DTO dto) {
		String statement = namespace +".do_save";
		
		log.debug("===========in do_save===========");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("===========// do_save===========");
		
		ImgVO imgVO = (ImgVO)dto;
		return sqlSession.insert(statement, imgVO);
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
