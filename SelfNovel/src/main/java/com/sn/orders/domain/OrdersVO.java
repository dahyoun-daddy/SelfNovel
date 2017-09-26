package com.sn.orders.domain;

import com.sn.common.DTO;

/**
 * OrdersVO 
 * detail : 자소서 의뢰 테이블 vo
 * 최초작성: 2017-09-21
 * @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public class OrdersVO extends DTO {
	/***********************************************/
	//field
	/***********************************************/
	int	rsm_id			;	//	자소서 아이디(PK)(FK)		
	String	exp_id		;	//	의뢰 받은 사람(PK)(FK)		
	String	u_id		;	//	의뢰한 사람		
	String	ord_state	;	//	상태(코드)
	String	ord_reg_dt	;	//	작성일
	String 	ord_use_yn	;	//	사용여부
	
	String	ord_state_nm;	//	상태(텍스트). db에서 꺼내올 때만 set한다.
	String 	rsm_title	;	//	이력서 제목.
	
	/***********************************************/
	//constructor
	/***********************************************/
	public OrdersVO() {
		
	}
	public OrdersVO(int rsm_id, String exp_id, String u_id, String ord_state, String ord_reg_dt, String ord_use_yn) 
	{
		super();
		this.rsm_id = rsm_id;
		this.exp_id = exp_id;
		this.u_id = u_id;
		this.ord_state = ord_state;
		this.ord_reg_dt = ord_reg_dt;
		this.ord_use_yn = ord_use_yn;
	}
	/***********************************************/
	//getter and setter
	/***********************************************/
	public int getRsm_id() {
		return rsm_id;
	}
	public void setRsm_id(int rsm_id) {
		this.rsm_id = rsm_id;
	}
	public String getExp_id() {
		return exp_id;
	}
	public void setExp_id(String exp_id) {
		this.exp_id = exp_id;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getOrd_state() {
		return ord_state;
	}
	public void setOrd_state(String ord_state) {
		this.ord_state = ord_state;
	}
	public String getOrd_reg_dt() {
		return ord_reg_dt;
	}
	public void setOrd_reg_dt(String ord_reg_dt) {
		this.ord_reg_dt = ord_reg_dt;
	}
	public String getOrd_state_nm() {
		return ord_state_nm;
	}
	public void setOrd_state_nm(String ord_state_nm) {
		this.ord_state_nm = ord_state_nm;
	}
	public String getRsm_title() {
		return rsm_title;
	}
	public void setRsm_title(String rsm_title) {
		this.rsm_title = rsm_title;
	}
	public String getOrd_use_yn() {
		return ord_use_yn;
	}
	public void setOrd_use_yn(String ord_use_yn) {
		this.ord_use_yn = ord_use_yn;
	}
	/***********************************************/
	//method
	/***********************************************/
	@Override
	public String toString() {
		return "OrdersVO [rsm_id=" + rsm_id + ", exp_id=" + exp_id + ", u_id=" + u_id + ", ord_state=" + ord_state
				+ ", ord_reg_dt=" + ord_reg_dt + ", ord_use_yn=" + ord_use_yn + ", ord_state_nm=" + ord_state_nm
				+ ", rsm_title=" + rsm_title + "]";
	}

	
}
