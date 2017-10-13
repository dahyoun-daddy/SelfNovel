package com.sn.resume.service;

import java.io.IOException;
import java.util.List;

import com.sn.common.DTO;
import com.sn.common.WorkDiv;

public interface ItmSvc extends WorkDiv {
	public int do_save_edit(DTO dto);
	public List<?> do_search_child(DTO dto);
	public int do_deleteAllRoot(DTO dto);
	public int do_updateOne(DTO dto);
	
	/**
	 * do_ExcelDownload
	 * @author LSG
	 * @param dto
	 * @return
	 * @throws IOException 
	 */
	public String do_ExcelDownload(DTO dto,String u_name) throws IOException;
}
