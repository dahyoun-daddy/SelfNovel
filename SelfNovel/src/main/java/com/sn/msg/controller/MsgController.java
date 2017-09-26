package com.sn.msg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * MsgController 
 * detail : 메시지 임시 컨트롤러
 * 최초작성: 2017-09-25
 * 최종수정: 2017-09-25
 * @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */

import com.sn.msg.service.MsgSvc;
@Controller
public class MsgController {
	@Autowired
	MsgSvc msgSvc;
	
	
}
