package com.sn.resume.domain;

import com.sn.common.DTO;

/**
 * ItmVO 
 * detail : 첨삭 항목 테이블 VO
 * 최초작성 : 2017-09-21
 * @author MinSeok <dev.edwinner@gmail.com>
 *
 */
public class ItmVO extends DTO {
	
	/***********************************************/
	//field
	/***********************************************/
	
	private String rsm_id      ;//글 아이디	(F-key)
	private String itm_form_id ;//항목 아이디(P-key)
	private String itm_prd_id  ;//상위 항목 ID
	private String itm_title   ;//소제목
	private String itm_content ;//소내용
	private String u_id        ;//작성자
	private String itm_reg_dt  ;//작성일
	private int    itm_seq     ;//순서
	private int    itm_use_yn  ;//공개 여부(1: 공개 / 0:비공개)
		
	
	/***********************************************/
	//constructor
	/***********************************************/
	
	/**
	 * Default Constructor
	 */
	public ItmVO() {}	
	
	/**
	 * @param rsm_id
	 * @param itm_form_id
	 * @param itm_prd_id
	 * @param itm_title
	 * @param itm_content
	 * @param u_id
	 * @param itm_reg_dt
	 * @param itm_seq
	 * @param itm_use_yn
	 */
	public ItmVO(String rsm_id, String itm_form_id, String itm_prd_id, String itm_title, String itm_content,
			String u_id, String itm_reg_dt, int itm_seq, int itm_use_yn) {
		super();
		this.rsm_id = rsm_id;
		this.itm_form_id = itm_form_id;
		this.itm_prd_id = itm_prd_id;
		this.itm_title = itm_title;
		this.itm_content = itm_content;
		this.u_id = u_id;
		this.itm_reg_dt = itm_reg_dt;
		this.itm_seq = itm_seq;
		this.itm_use_yn = itm_use_yn;
	}



	/***********************************************/
	//getter and setter
	/***********************************************/

	/**
	 * @return the rsm_id
	 */
	public String getRsm_id() {
		return rsm_id;
	}

	/**
	 * @param rsm_id the rsm_id to set
	 */
	public void setRsm_id(String rsm_id) {
		this.rsm_id = rsm_id;
	}

	/**
	 * @return the itm_form_id
	 */
	public String getItm_form_id() {
		return itm_form_id;
	}

	/**
	 * @param itm_form_id the itm_form_id to set
	 */
	public void setItm_form_id(String itm_form_id) {
		this.itm_form_id = itm_form_id;
	}

	/**
	 * @return the itm_prd_id
	 */
	public String getItm_prd_id() {
		return itm_prd_id;
	}

	/**
	 * @param itm_prd_id the itm_prd_id to set
	 */
	public void setItm_prd_id(String itm_prd_id) {
		this.itm_prd_id = itm_prd_id;
	}

	/**
	 * @return the itm_title
	 */
	public String getItm_title() {
		return itm_title;
	}

	/**
	 * @param itm_title the itm_title to set
	 */
	public void setItm_title(String itm_title) {
		this.itm_title = itm_title;
	}

	/**
	 * @return the itm_content
	 */
	public String getItm_content() {
		return itm_content;
	}

	/**
	 * @param itm_content the itm_content to set
	 */
	public void setItm_content(String itm_content) {
		this.itm_content = itm_content;
	}

	/**
	 * @return the u_id
	 */
	public String getU_id() {
		return u_id;
	}

	/**
	 * @param u_id the u_id to set
	 */
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	/**
	 * @return the itm_seq
	 */
	public int getItm_seq() {
		return itm_seq;
	}

	/**
	 * @param itm_seq the itm_seq to set
	 */
	public void setItm_seq(int itm_seq) {
		this.itm_seq = itm_seq;
	}

	/**
	 * @return the itm_use_yn
	 */
	public int getItm_use_yn() {
		return itm_use_yn;
	}

	/**
	 * @param itm_use_yn the itm_use_yn to set
	 */
	public void setItm_use_yn(int itm_use_yn) {
		this.itm_use_yn = itm_use_yn;
	}

	/**
	 * @return the itm_reg_dt
	 */
	public String getItm_reg_dt() {
		return itm_reg_dt;
	}

	/**
	 * @param itm_reg_dt the itm_reg_dt to set
	 */
	public void setItm_reg_dt(String itm_reg_dt) {
		this.itm_reg_dt = itm_reg_dt;
	}
	
	/***********************************************/
	//method
	/***********************************************/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItmVO [rsm_id=" + rsm_id + ", itm_form_id=" + itm_form_id + ", itm_prd_id=" + itm_prd_id
				+ ", itm_title=" + itm_title + ", itm_content=" + itm_content + ", u_id=" + u_id + ", itm_reg_dt="
				+ itm_reg_dt + ", itm_seq=" + itm_seq + ", itm_use_yn=" + itm_use_yn + "]";
	}	

}//--ItmVO
