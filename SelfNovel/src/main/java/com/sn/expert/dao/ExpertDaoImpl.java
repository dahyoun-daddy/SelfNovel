package com.sn.expert.dao;

import java.util.Hashtable;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sn.common.DTO;
import com.sn.expert.domain.ExpertVO;

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
		ExpertVO param=(ExpertVO)dto;
		
		Hashtable<String, String> searchParam = null;//검색조건
		searchParam = param.getParam();
		
		int page_size  = 16;
		int page_num   = 1;
		
		if(searchParam.get("pageSize")!=null)//page_size: 10,50,100 
			page_size = Integer.parseInt(searchParam.get("pageSize").toString());
		
		if(searchParam.get("pageNo") !=null)//page_num:1,2,3,....
			page_num = Integer.parseInt(searchParam.get("pageNo").toString());
		
		searchParam.put("PAGE_SIZE", page_size+"");
		searchParam.put("PAGE_NUM", page_num+"");
		
		
		String searchWord  = searchParam.get("searchWord").toString();
		String searchDiv   = searchParam.get("searchDiv").toString();
		String searchCategory   = searchParam.get("searchCategory").toString();
		
		searchParam.put("SEARCH_DIV", searchDiv);
		searchParam.put("SEARCH_WORD", searchWord);
		searchParam.put("SEARCH_CATEGORY", searchCategory);
		return sqlSession.selectList(namespace+".do_search", searchParam);
	}

	@Override
	public int do_delete(DTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int do_update(DTO dto) {
		log.debug("=================================");
		log.debug(".do_update");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		
		if(dto.getParam().get("functionSep").equals("name")) {
			return sqlSession.update(namespace+".do_updateName", (ExpertVO) dto);
		} else {
			return sqlSession.update(namespace+".do_updateProfile", (ExpertVO) dto);
		}
	}

	@Override
	public DTO do_searchOne(DTO dto) {
		log.debug("=================================");
		log.debug(".do_searchOne");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		return sqlSession.selectOne(namespace+".do_searchOne", (ExpertVO) dto);
	}
	
	public DTO do_chkId(DTO dto) {
		log.debug("=================================");
		log.debug(".do_chkId");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		return sqlSession.selectOne(namespace+".do_chkId", (ExpertVO) dto);
	}

	public List<?> do_searchRank(){
		log.debug("=================================");
		log.debug(".do_searchRank");
		log.debug("=================================");
		return sqlSession.selectList(namespace+".do_searchRank");
	}
	
}
