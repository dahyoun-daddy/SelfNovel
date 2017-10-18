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
import com.sn.resume.domain.ItmVO;
import com.sn.resume.domain.RsmVO;

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
		
		Hashtable<String, String> searchParam = param.getParam();
		
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

	@Override
	public List<?> do_searchDetail_itm(DTO dto) {
		log.debug("=================================");
		log.debug(".do_searchDetail_itm");
		log.debug("=================================");
		return sqlSession.selectList(namespace+".do_searchDetail_itm",(ExpertVO) dto);
	}

	@Override
	public DTO do_searchDetail_rsm(DTO dto) {
		log.debug("=================================");
		log.debug(".do_searchDetail_rsm");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		return sqlSession.selectOne(namespace+".do_searchDetail_rsm", (ItmVO) dto);
	}

	@Override
	public List<?> do_searchDetail(DTO dto) {
		log.debug("=================================");
		log.debug(".do_searchDetail");
		log.debug("=================================");
		
		ItmVO param=(ItmVO) dto;
		Hashtable<String, String> searchParam = param.getParam();
		
		String u_ids  = searchParam.get("u_ids").toString();
		searchParam.put("u_ids", u_ids);
		searchParam.put("rsm_id", param.getRsm_id().toString());
		
		return sqlSession.selectList(namespace+".do_searchDetail", searchParam);
	}

	@Override
	public DTO do_chkNaver(DTO dto) {
		log.debug("=================================");
		log.debug(".do_chkId");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		return sqlSession.selectOne(namespace+".do_chkNaver", (ExpertVO) dto);
	}

	@Override
	public int do_saveOrder(DTO dto) {
		log.debug("=================================");
		log.debug(".do_saveOrder");
		log.debug("dto.toString(): " + dto.toString());
		log.debug("=================================");
		
		RsmVO param=(RsmVO)dto;		
		Hashtable<String, String> searchParam = param.getParam();
		
		searchParam.put("rsm_div", param.getRsm_div());
		searchParam.put("rsm_title", param.getRsm_title());
		searchParam.put("rsm_content", param.getRsm_content());
		searchParam.put("u_id", param.getU_id());
		searchParam.put("exp_id", param.getParam().get("exp_id"));
		searchParam.put("itm_inserts", param.getParam().get("itm_inserts"));
		searchParam.put("itm_contents", param.getParam().get("itm_contents"));
		
		return sqlSession.update(namespace+".do_saveOrder", searchParam);
	}

	@Override
	public DTO do_nextSeq() {
		log.debug("=================================");
		log.debug(".do_nextSeq");
		log.debug("=================================");
		return sqlSession.selectOne(namespace+".do_nextSeq");
	}

	@Override
	public int do_updateTrade(DTO dto) {
		log.debug("=================================");
		log.debug(".do_updateTrade");
		log.debug("=================================");
		return sqlSession.update(namespace+".do_updateTrade", (ExpertVO) dto);
	}
	
}
