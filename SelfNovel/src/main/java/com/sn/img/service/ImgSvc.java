package com.sn.img.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sn.common.DTO;

public interface ImgSvc {
	public List<?> do_search(DTO dto);
	
	public int do_savepptTx(String fileName,String filePath,String resourcesFilepath) throws FileNotFoundException, IOException;
}
