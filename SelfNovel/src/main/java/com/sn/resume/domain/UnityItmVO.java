package com.sn.resume.domain;

/**
 * 데이터 전송 및 수신이 용이하도록
 * 통합 VO 생성함.
 * ItmVO에 RsmVO를 상속받으면 되지만 형이 싫어하셔서.. 쥬륵
 * @author sist_01
 */
public class UnityItmVO extends RsmVO{
	private int itm_form_id ;//항목 아이디(P-key)
	private String itm_prd_id  ;//상위 항목 ID
	private String itm_title   ;//소제목
	private String itm_content ;//소내용
	private String itm_reg_dt  ;//작성일
	private int    itm_seq     ;//순서
	private int    itm_use_yn  ;//공개 여부(1: 공개 / 0:비공개)
	
	
	
	
	public int getItm_form_id() {
		return itm_form_id;
	}
	public void setItm_form_id(int itm_form_id) {
		this.itm_form_id = itm_form_id;
	}
	public String getItm_prd_id() {
		return itm_prd_id;
	}
	public void setItm_prd_id(String itm_prd_id) {
		this.itm_prd_id = itm_prd_id;
	}
	public String getItm_title() {
		return itm_title;
	}
	public void setItm_title(String itm_title) {
		this.itm_title = itm_title;
	}
	public String getItm_content() {
		return itm_content;
	}
	public void setItm_content(String itm_content) {
		this.itm_content = itm_content;
	}
	public String getItm_reg_dt() {
		return itm_reg_dt;
	}
	public void setItm_reg_dt(String itm_reg_dt) {
		this.itm_reg_dt = itm_reg_dt;
	}
	public int getItm_seq() {
		return itm_seq;
	}
	public void setItm_seq(int itm_seq) {
		this.itm_seq = itm_seq;
	}
	public int getItm_use_yn() {
		return itm_use_yn;
	}
	public void setItm_use_yn(int itm_use_yn) {
		this.itm_use_yn = itm_use_yn;
	}
	@Override
	public String toString() {
		return "UnityItmVO [itm_form_id=" + itm_form_id + ", itm_prd_id=" + itm_prd_id + ", itm_title=" + itm_title
				+ ", itm_content=" + itm_content + ", itm_reg_dt=" + itm_reg_dt + ", itm_seq=" + itm_seq
				+ ", itm_use_yn=" + itm_use_yn + "]";
	}
}
