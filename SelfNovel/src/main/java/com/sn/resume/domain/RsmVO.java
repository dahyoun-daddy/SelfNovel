package com.sn.resume.domain;

import com.sn.common.DTO;

/**
 * RsmVO : 자소서 게시판 테이블 VO
 * @author @author MinSeok <dev.edwinner@gmail.com>
 *
 */
public class RsmVO extends DTO {
	private String rsm_id;				//글 아이디
	private String itm_id;				//소제목 아이디
	private String img_id;				//PPT아이디
	private String rsm_title;			//글 제목
	private String rsm_content;			//글 내용
	private String u_id;				//작성자
	private String rsm_reg_dt;			//작성일
	private String rsm_recommend;		//추천수
	private String rsm_use_yn;			//공개 여부
	private String rsm_ord_yn;			//신청 여부
	
	/**
	 * Default Constructor
	 */
	public RsmVO() {}
	
	/**
	 * @param rsm_id
	 * @param itm_id
	 * @param img_id
	 * @param rsm_title
	 * @param rsm_content
	 * @param u_id
	 * @param rsm_reg_dt
	 * @param rsm_recommend
	 * @param rsm_use_yn
	 * @param rsm_ord_yn
	 */
	public RsmVO(String rsm_id, String itm_id, String img_id, String rsm_title, String rsm_content, String u_id,
			String rsm_reg_dt, String rsm_recommend, String rsm_use_yn, String rsm_ord_yn) {
		super();
		this.rsm_id = rsm_id;
		this.itm_id = itm_id;
		this.img_id = img_id;
		this.rsm_title = rsm_title;
		this.rsm_content = rsm_content;
		this.u_id = u_id;
		this.rsm_reg_dt = rsm_reg_dt;
		this.rsm_recommend = rsm_recommend;
		this.rsm_use_yn = rsm_use_yn;
		this.rsm_ord_yn = rsm_ord_yn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RsmVO [rsm_id=" + rsm_id + ", itm_id=" + itm_id + ", img_id=" + img_id + ", rsm_title=" + rsm_title
				+ ", rsm_content=" + rsm_content + ", u_id=" + u_id + ", rsm_reg_dt=" + rsm_reg_dt + ", rsm_recommend="
				+ rsm_recommend + ", rsm_use_yn=" + rsm_use_yn + ", rsm_ord_yn=" + rsm_ord_yn + "]";
	}
	
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
	 * @return the itm_id
	 */
	public String getItm_id() {
		return itm_id;
	}
	/**
	 * @param itm_id the itm_id to set
	 */
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	/**
	 * @return the img_id
	 */
	public String getImg_id() {
		return img_id;
	}
	/**
	 * @param img_id the img_id to set
	 */
	public void setImg_id(String img_id) {
		this.img_id = img_id;
	}
	/**
	 * @return the rsm_title
	 */
	public String getRsm_title() {
		return rsm_title;
	}
	/**
	 * @param rsm_title the rsm_title to set
	 */
	public void setRsm_title(String rsm_title) {
		this.rsm_title = rsm_title;
	}
	/**
	 * @return the rsm_content
	 */
	public String getRsm_content() {
		return rsm_content;
	}
	/**
	 * @param rsm_content the rsm_content to set
	 */
	public void setRsm_content(String rsm_content) {
		this.rsm_content = rsm_content;
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
	 * @return the rsm_reg_dt
	 */
	public String getRsm_reg_dt() {
		return rsm_reg_dt;
	}
	/**
	 * @param rsm_reg_dt the rsm_reg_dt to set
	 */
	public void setRsm_reg_dt(String rsm_reg_dt) {
		this.rsm_reg_dt = rsm_reg_dt;
	}
	/**
	 * @return the rsm_recommend
	 */
	public String getRsm_recommend() {
		return rsm_recommend;
	}
	/**
	 * @param rsm_recommend the rsm_recommend to set
	 */
	public void setRsm_recommend(String rsm_recommend) {
		this.rsm_recommend = rsm_recommend;
	}
	/**
	 * @return the rsm_use_yn
	 */
	public String getRsm_use_yn() {
		return rsm_use_yn;
	}
	/**
	 * @param rsm_use_yn the rsm_use_yn to set
	 */
	public void setRsm_use_yn(String rsm_use_yn) {
		this.rsm_use_yn = rsm_use_yn;
	}
	/**
	 * @return the rsm_ord_yn
	 */
	public String getRsm_ord_yn() {
		return rsm_ord_yn;
	}
	/**
	 * @param rsm_ord_yn the rsm_ord_yn to set
	 */
	public void setRsm_ord_yn(String rsm_ord_yn) {
		this.rsm_ord_yn = rsm_ord_yn;
	}
	
	
}//--RsmVO
