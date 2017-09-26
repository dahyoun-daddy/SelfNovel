package com.sn.log.domain;

import java.util.UUID;

import com.sn.common.DTO;

/**
 * LogVO 
 * detail : 로그 vo
 * 최초작성: 2017-09-22
 * @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public class LogVO extends DTO {
	/***********************************************/
	//field
	/***********************************************/
	String log_id			;//로그 아이디(PK)
	String log_class_path	;//클래스패스
	String log_sql			;//SQL문
	String log_param		;//파라매터
	String log_reg_dt		;//작성일
	String log_reg_id		;//작성아이디
	String log_lv			;//로그레벨
	String log_errormsg		;//에러메시지

	
	/***********************************************/
	//constructor
	/***********************************************/
	public LogVO() {
		
	}	
	public LogVO(String log_class_path, String log_sql, String log_param,
			String log_reg_id, String log_errormsg) {
		super();
		
		this.log_class_path = log_class_path;
		this.log_sql = log_sql;
		this.log_param = log_param;
		this.log_reg_id = log_reg_id;
		this.log_errormsg = log_errormsg;
	}

	/***********************************************/
	//getter and setter
	/***********************************************/
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getLog_class_path() {
		return log_class_path;
	}
	public void setLog_class_path(String log_class_path) {
		this.log_class_path = log_class_path;
	}
	public String getLog_sql() {
		return log_sql;
	}
	public void setLog_sql(String log_sql) {
		this.log_sql = log_sql;
	}
	public String getLog_param() {
		return log_param;
	}
	public void setLog_param(String log_param) {
		this.log_param = log_param;
	}
	public String getLog_reg_dt() {
		return log_reg_dt;
	}
	public void setLog_reg_dt(String log_reg_dt) {
		this.log_reg_dt = log_reg_dt;
	}
	public String getLog_reg_id() {
		return log_reg_id;
	}
	public void setLog_reg_id(String log_reg_id) {
		this.log_reg_id = log_reg_id;
	}
	public String getLog_lv() {
		return log_lv;
	}
	public void setLog_lv(String log_lv) {
		this.log_lv = log_lv;
	}
	public String getLog_errormsg() {
		return log_errormsg;
	}
	public void setLog_errormsg(String log_errormsg) {
		this.log_errormsg = log_errormsg;
	}

	/***********************************************/
	//method
	/***********************************************/
	@Override
	public String toString() {
		return "LogVO [log_id=" + log_id + ", log_class_path=" + log_class_path + ", log_sql=" + log_sql
				+ ", log_param=" + log_param + ", log_reg_dt=" + log_reg_dt + ", log_reg_id=" + log_reg_id + ", log_lv="
				+ log_lv + ", log_errormsg=" + log_errormsg + "]";
	}
	
}
