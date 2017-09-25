package com.sn.msg.domain;

import com.sn.common.DTO;
/**
 * MsgVO 
 * detail : 메시지 vo
 * 최초작성: 2017-09-25
 * 최종수정: 2017-09-25
 * @author @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public class MsgVO extends DTO {
	/***********************************************/
	//field
	/***********************************************/
	private String msg_id			;//메시지 아이디(PK)
	private String msg_sender		;//보내는 사람
	private String msg_receiver		;//받는 사람
	private String msg_content		;//메시지 내용
	private int    msg_sep			;//구분
	private String msg_reg_dt		;//시간
	private int	   rsm_id			;//글 아이디
	private String msg_notify		;//신고 대상
	
	private int    read_yn			;//읽음여부

	
	/***********************************************/
	//constructor
	/***********************************************/
	public MsgVO() {
		super();
	}
	public MsgVO(String msg_id, String msg_sender, String msg_receiver, String msg_content, int msg_sep,
			String msg_reg_dt, int rsm_id, String msg_notify, int read_yn) {
		super();
		this.msg_id = msg_id;
		this.msg_sender = msg_sender;
		this.msg_receiver = msg_receiver;
		this.msg_content = msg_content;
		this.msg_sep = msg_sep;
		this.msg_reg_dt = msg_reg_dt;
		this.rsm_id = rsm_id;
		this.msg_notify = msg_notify;
		this.read_yn = read_yn;
	}	
	
	
	/***********************************************/
	//getter and setter
	/***********************************************/
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getMsg_sender() {
		return msg_sender;
	}
	public void setMsg_sender(String msg_sender) {
		this.msg_sender = msg_sender;
	}
	public String getMsg_receiver() {
		return msg_receiver;
	}
	public void setMsg_receiver(String msg_receiver) {
		this.msg_receiver = msg_receiver;
	}
	public String getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}
	public int getMsg_sep() {
		return msg_sep;
	}
	public void setMsg_sep(int msg_sep) {
		this.msg_sep = msg_sep;
	}
	public String getMsg_reg_dt() {
		return msg_reg_dt;
	}
	public void setMsg_reg_dt(String msg_reg_dt) {
		this.msg_reg_dt = msg_reg_dt;
	}
	public int getRsm_id() {
		return rsm_id;
	}
	public void setRsm_id(int rsm_id) {
		this.rsm_id = rsm_id;
	}
	public String getMsg_notify() {
		return msg_notify;
	}
	public void setMsg_notify(String msg_notify) {
		this.msg_notify = msg_notify;
	}
	public int getRead_yn() {
		return read_yn;
	}
	public void setRead_yn(int read_yn) {
		this.read_yn = read_yn;
	}

	
	/***********************************************/
	//method
	/***********************************************/
	@Override
	public String toString() {
		return "MsgVO [msg_id=" + msg_id + ", msg_sender=" + msg_sender + ", msg_receiver=" + msg_receiver
				+ ", msg_content=" + msg_content + ", msg_sep=" + msg_sep + ", msg_reg_dt=" + msg_reg_dt + ", rsm_id="
				+ rsm_id + ", msg_notify=" + msg_notify + ", read_yn=" + read_yn + "]";
	}	
	
	
}
