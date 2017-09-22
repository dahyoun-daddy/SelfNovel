package com.sn.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sn.common.DTO;
import com.sn.log.dao.LogDao;
import com.sn.log.domain.LogVO;
@Service
public class LogSvcNotTxImpl implements LogSvc {
	@Autowired
	LogDao logdao;

	@Override
	public int debug(DTO dto) {
		LogVO logDto = (LogVO)dto;
		logDto.setLog_lv("DEBUG");
		return logdao.do_save(dto);
	}

	@Override
	public int error(DTO dto) {
		LogVO logDto = (LogVO)dto;
		logDto.setLog_lv("ERROR");
		return logdao.do_save(dto);
	}

}
