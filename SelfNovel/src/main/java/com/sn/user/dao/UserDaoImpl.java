package com.sn.user.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sn.common.DTO;
import com.sn.user.domain.UserVO;

@Repository
public class UserDaoImpl implements UserDao {
	private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String namespace ="com.sn.user.repository.mappers.user";
	
	@Override
	public int do_save(DTO dto) {
		log.debug("=================================");
		log.debug(".do_save");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		int flag = sqlSession.update(namespace+".do_save", (UserVO) dto);
		
		return flag;
	}

	@Override
	public List<?> do_search(DTO dto) {
		
		return null;
	}

	@Override
	public int do_delete(DTO dto) {
		
		return 0;
	}

	@Override
	public int do_update(DTO dto) {
		log.debug("=================================");
		log.debug(".do_update");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		if(dto.getParam().get("functionSep").equals("name")) {
			return sqlSession.selectOne(namespace+".do_updateName", (UserVO) dto);
		} else {
			return sqlSession.selectOne(namespace+".do_updatePwd", (UserVO) dto);
		}
	}
	
	@Override
	public DTO do_searchOne(DTO dto) {
		log.debug("=================================");
		log.debug(".do_searchOne");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		return sqlSession.selectOne(namespace+".do_searchOne", (UserVO) dto);
	}
	
	public DTO do_chkId(DTO dto) {
		log.debug("=================================");
		log.debug(".do_chkId");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		return sqlSession.selectOne(namespace+".do_chkId", (UserVO) dto);
	}
}
