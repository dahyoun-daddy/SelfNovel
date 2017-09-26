package com.sn.img.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sn.codes.domain.CodesVO;
import com.sn.common.DTO;
import com.sn.img.domain.ImgVO;
import com.sn.orders.domain.OrdersVO;

@Repository
public class ImgDaoImpl implements ImgDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String namespace = "com.sn.img.repository.mappers.imgs";
	
	/**
	 * do_save
	 * detail: 저장
	 * @param dto
	 * @return flag
	 */
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

	/**
	 * do_search
	 * detail: 다건조회
	 * @param dto
	 * @return List
	 */
	@Override
	public List<?> do_search(DTO dto) {
		String statement = namespace +".do_search";
		
		log.debug("in do_search============");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("========================");
		
		ImgVO imgVO = (ImgVO)dto;
		
		return sqlSession.selectList(statement, imgVO);
	}

	/**
	 * do_delete
	 * detail: 삭제
	 * @param dto
	 * @return flag
	 */
	@Override
	public int do_delete(DTO dto) {
		String statement = namespace +".do_delete";
		
		log.debug("in do_delete========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
		
		ImgVO imgVO = (ImgVO)dto;
		
		return sqlSession.delete(statement, imgVO);
	}

	/**
	 * do_update
	 * detail: 수정
	 * @param dto
	 * @return flag
	 */
	@Override
	public int do_update(DTO dto) {
		String statement = namespace +".do_update";
		
		log.debug("in do_delete========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
		
		ImgVO imgVO = (ImgVO)dto;
		
		return sqlSession.update(statement, imgVO);
	}

	/**
	 * do_searchOne
	 * detail: 단건조회
	 * @param dto
	 * @return dto
	 */
	@Override
	public DTO do_searchOne(DTO dto) {
		String statement = namespace +".do_searchOne";
		
		log.debug("in do_searchOne========================");
		log.debug("statement: "+statement);
		log.debug("dto: "+dto.toString());
		log.debug("=======================================");
		
		ImgVO imgVO = (ImgVO)dto;
		
		return sqlSession.selectOne(statement, imgVO);
	}

}
