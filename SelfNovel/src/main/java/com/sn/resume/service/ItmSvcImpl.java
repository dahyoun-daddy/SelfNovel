package com.sn.resume.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sn.common.DTO;
import com.sn.common.ExcelUtil;
import com.sn.resume.dao.ItmDao;
import com.sn.resume.domain.ItmVO;

/**
 * ItmSvcImpl
 * detail : Itm 서비스 구현클래스
 * 최초작성 : 2017-09-27
 * @author MinSeok <dev.edwinner@gmail.com>
 *
 */

@Service
public class ItmSvcImpl implements ItmSvc {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItmDao itmDao;
	
	//for excel_download
	//@autor LSG
	private String path = "c:\\file\\excel\\";
	
	/**
	 * 일반 저장
	 */
	@Override
	public int do_save(DTO dto) {
		log.debug("===== ItmDaoImpl.do_save =====");
		log.debug("dto : " + dto.toString());
		log.debug("==============================");
		return itmDao.do_save(dto);
	}
	

	/**
	 * 첨삭 저장
	 */
	@Override
	public int do_save_edit(DTO dto) {
		log.debug("===== ItmDaoImpl.do_save_edit =====");
		log.debug("dto : " + dto.toString());
		log.debug("===================================");
		return itmDao.do_save_edit(dto);
	}

	/**
	 * 조회
	 */
	@Override
	public List<?> do_search(DTO dto) {
		log.debug("===== ItmDaoImpl.do_search =====");
		log.debug("dto : " + dto.toString());
		log.debug("================================");
		return itmDao.do_search(dto);
	}
	
	/**
	 * 조회
	 */	
	public List<?> do_search_child(DTO dto) {
		log.debug("===== ItmDaoImpl.do_search_child =====");
		log.debug("dto : " + dto.toString());
		log.debug("======================================");
		return itmDao.do_search_child(dto);
	}

	/**
	 * 삭제
	 */
	@Override
	public int do_delete(DTO dto) {
		log.debug("===== ItmDaoImpl.do_delete =====");
		log.debug("dto : " + dto.toString());
		log.debug("================================");
		return itmDao.do_delete(dto);
	}
	
	

	/**
	 * 수정
	 */
	@Override
	public int do_update(DTO dto) {
		log.debug("===== ItmDaoImpl.do_update =====");
		log.debug("dto : " + dto.toString());
		log.debug("================================");		
		
		return itmDao.do_update(dto);
	}
	
	/**
	 * 단건 수정
	 */
	@Override
	public int do_updateOne(DTO dto) {
		log.debug("===== ItmDaoImpl.do_updateOne =====");
		log.debug("dto : " + dto.toString());
		log.debug("===================================");
		return itmDao.do_updateOne(dto);
	}

	//Do not use
	@Override
	public DTO do_searchOne(DTO dto) {
		//Don't use it
		return null;
	}

	/**
	 * 수정시 root제거
	 */
	@Override
	public int do_deleteAllRoot(DTO dto) {
		log.debug("===== ItmDaoImpl.do_delete_all_root =====");
		log.debug("dto : " + dto.toString());
		log.debug("=========================================");
		
		return itmDao.do_deleteAllRoot(dto);
	}
	
	
	/**
	 * do_ExcelDownload
	 * @author LSG
	 * @param dto
	 * @return
	 * @throws IOException 
	 */
	public String do_ExcelDownload(DTO dto,String u_name) throws IOException {
		String fileName = null;						
		log.debug("======================================");						
		log.debug("do excel_down: dto: "+dto.toString());						
		log.debug("======================================");						
		List<?> list = (List<?>) this.itmDao.do_search(dto);	
								
		ExcelUtil excelUtil = new ExcelUtil();						
		fileName = excelUtil.writeExcel(path, "resume.xls", list,u_name);						
								
		log.debug("======================================");						
		log.debug("fileName: "+fileName);						
		log.debug("======================================");						
								
		return path+fileName;						
	}
}
