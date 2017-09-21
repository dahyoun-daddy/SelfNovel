package com.sn.order.domain;

import com.sn.common.DTO;

/**
 * OrederVO 
 * detail : 자소서 의뢰 테이블 vo
 * 최초작성: 2017-09-21
 * @author SeulGi <dev.leewisdom92@gmail.com>
 *
 */
public class OrderVO extends DTO {
	/***********************************************/
	//field
	/***********************************************/
	int	rsm_id			;	//	자소서 아이디(PK)(FK)		
	String	exp_id		;	//	의뢰 받은 사람(PK)(FK)		
	String	u_id		;	//	의뢰한 사람		
	String	org_state	;	//	상태(코드)		
	String	ord_reg_dt	;	//	작성일		

	
	/***********************************************/
	//constructor
	/***********************************************/
	public OrderVO() {
		
	}
	public OrderVO(int rsm_id, String exp_id, String u_id, String org_state, String ord_reg_dt) {
		super();
		this.rsm_id = rsm_id;
		this.exp_id = exp_id;
		this.u_id = u_id;
		this.org_state = org_state;
		this.ord_reg_dt = ord_reg_dt;
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
	public String getOrg_state() {
		return org_state;
	}
	public void setOrg_state(String org_state) {
		this.org_state = org_state;
	}
	public String getOrd_reg_dt() {
		return ord_reg_dt;
	}
	public void setOrd_reg_dt(String ord_reg_dt) {
		this.ord_reg_dt = ord_reg_dt;
	}
	
	/***********************************************/
	//method
	/***********************************************/
	@Override
	public String toString() {
		return "OrderVO [rsm_id=" + rsm_id + ", exp_id=" + exp_id + ", u_id=" + u_id + ", org_state=" + org_state
				+ ", ord_reg_dt=" + ord_reg_dt + "]";
	}
}
