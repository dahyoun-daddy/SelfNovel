package com.sn.resume.dao;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	 * do_save
	 * detail : 삽입
	 */
	@Override
	public int do_save(DTO dto) {
		String statement = namespace +".do_save";//resume.xml연결
		RsmVO  inRsmVO   = (RsmVO)dto;           //파라미터 주입
				
		return sqlSession.insert(statement, inRsmVO);
	}
	
	/**
	 * do_search
	 * detail : 전체 조회 및 검색
	 */
	@Override
	public List<?> do_search(DTO dto) {
		String statement = namespace +".do_search";//resume.xml연결
		RsmVO inRsmVO = (RsmVO)dto;//파라미터 주입
		
		Hashtable<String, Object> searchParam = new Hashtable<String, Object>();

		//검색조건 주입
		Set keySet = inRsmVO.getParam().keySet();
		Iterator iter = keySet.iterator();
		
		while(iter.hasNext()) {
			String key = (String) iter.next();
			searchParam.put(key, inRsmVO.getParam().get(key));			
		}
		
		String[] searchCat = searchParam.get("searchCat").toString().split("&");
		List<String> searchCatList = new ArrayList<String>();
		
		if(searchCat[0].equals("")) {
			
		}else {			
			for(String cat : searchCat) {
				searchCatList.add(cat);
			}
		}
		
		int page_size = 10;
		int page_num = 1;		
		
		if(searchParam.get("pageSize")!=null)//page_size:10,50,100
			page_size = Integer.parseInt(searchParam.get("pageSize").toString());
		
		if(searchParam.get("pageNo") !=null)//page_num:1,2,3,....
			page_num = Integer.parseInt(searchParam.get("pageNo").toString());
		
		searchParam.put("page_size", page_size+"");
		searchParam.put("page_num", page_num+"");
		
		String searchWord  = searchParam.get("searchWord").toString();
		String searchDiv   = searchParam.get("searchDiv").toString();		
		
		searchParam.put("search_div", searchDiv);
		searchParam.put("search_word", searchWord);
		searchParam.put("search_cat", searchCatList);
		searchParam.put("search_word_length", searchWord.length());	
		
		log.debug("search_word : " + searchWord);
		
		return sqlSession.selectList(statement, searchParam);
	}

	/**
	 * do_delete
	 * detail : 삭제
	 */
	@Override
	public int do_delete(DTO dto) {
		String statement = namespace +".do_delete";//resume.xml연결
		RsmVO  inRsmVO   = (RsmVO)dto;             //파라미터 주입
				
		return sqlSession.update(statement, inRsmVO);
	}

	/**
	 * do_update
	 * detail : 수정
	 */
	@Override
	public int do_update(DTO dto) {
		String statement = namespace +".do_update";//resume.xml연결
		RsmVO  inRsmVO   = (RsmVO)dto;             //파라미터 주입
				
		return sqlSession.update(statement, inRsmVO);
	}
	
	/**
	 * do_update_recommend
	 * detail : 추천수 증가
	 * @param dto
	 * @return
	 */
	@Override
	public int do_update_recommend(DTO dto) {
		String statement = namespace +".do_update_recommend";//resume.xml연결
		RsmVO  inRsmVO   = (RsmVO)dto;//파라미터 주입
				
		return sqlSession.update(statement, inRsmVO);
	}
	
	/**
	 * do_update_count
	 * detail : 조회수 증가
	 * @param dto
	 * @return
	 */
	@Override
	public int do_update_count(DTO dto) {
		String statement = namespace +".do_update_count";//resume.xml연결
		RsmVO  inRsmVO   = (RsmVO)dto;//파라미터 주입
				
		return sqlSession.update(statement, inRsmVO);
	}
	

	/**
	 * do_searchOne
	 * detail : 단건조회 기능
	 */
	@Override
	public DTO do_searchOne(DTO dto) {
		String statement = namespace +".do_searchOne";//resume.xml연결		
		RsmVO inRsmVO = (RsmVO)dto;//파라미터 주입
		
		return sqlSession.selectOne(statement, inRsmVO);
	}

	/**
	 * do_getNextVal
	 * detail : 조회수 증가
	 * 
	 * date: 2017-10-10
	 * @author pinkbean
	 * 
	 * @param dto
	 * @return
	 */
	public String do_getNextVal() {
		String statement = namespace +".do_getNextVal";//resume.xml연결		
		return sqlSession.selectOne(statement);		
	}
}
