package com.sn.img.service;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sn.common.DTO;

public interface ImgSvc {

	public List<DTO> do_saveMulti(MultipartHttpServletRequest mReq) throws IOException, DataAccessException;
}
