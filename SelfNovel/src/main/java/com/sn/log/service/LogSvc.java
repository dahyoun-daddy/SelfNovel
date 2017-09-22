package com.sn.log.service;

import com.sn.common.DTO;

public interface LogSvc {

	
	public int debug(DTO dto);
	
	public int error(DTO dto);
}
