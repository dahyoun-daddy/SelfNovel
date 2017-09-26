package com.sn.expert.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sn.common.DTO;
import com.sn.expert.domain.ExpertVO;
import com.sn.user.dao.UserDaoImpl;
import com.sn.user.domain.UserVO;

@Repository
public class ExpertDaoImpl implements ExpertDao {
private static Logger log = LoggerFactory.getLogger(ExpertDaoImpl.class);
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String namespace ="com.sn.expert.repository.mappers.expert";
	
	@Override
	public int do_save(DTO dto) {
		log.debug("=================================");
		log.debug(".do_save");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		int flag = sqlSession.update(namespace+".do_save", (ExpertVO) dto);
		
		return flag;
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
		log.debug("=================================");
		log.debug(".do_searchOne");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		return sqlSession.selectOne(namespace+".do_searchOne", (ExpertVO) dto);
	}

}
