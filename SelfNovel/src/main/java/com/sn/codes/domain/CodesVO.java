package com.sn.codes.domain;

import com.sn.common.DTO;

//selfnovel
//system

/**
 * CodesVO
 * detail: 코드관리 vo
 * 
 * 최초작성: 2017-09-21
 * @author sist
 *
 */
public class CodesVO extends DTO {
	/***********************************************/
	//field
	/***********************************************/
	String 	mst_cd_id	;
	String 	dtl_cd_id	;
	String 	mst_cd_nm	;
	String 	dtl_cd_nm	;
	int	seq	;
	String 	sup_mst_cd_id	;
	int	use_yn	;
	
	/***********************************************/
	//constructor
	/***********************************************/
	public CodesVO() {
		
	}
	public CodesVO(String mst_cd_id, String dtl_cd_id, String mst_cd_nm, String dtl_cd_nm, int seq,
			String sup_mst_cd_id, int use_yn) {
		super();
		this.mst_cd_id = mst_cd_id;
		this.dtl_cd_id = dtl_cd_id;
		this.mst_cd_nm = mst_cd_nm;
		this.dtl_cd_nm = dtl_cd_nm;
		this.seq = seq;
		this.sup_mst_cd_id = sup_mst_cd_id;
		this.use_yn = use_yn;
	}
	
	/***********************************************/
	//getter and setter
	/***********************************************/
	public String getMst_cd_id() {
		return mst_cd_id;
	}
	public void setMst_cd_id(String mst_cd_id) {
		this.mst_cd_id = mst_cd_id;
	}
	public String getDtl_cd_id() {
		return dtl_cd_id;
	}
	public void setDtl_cd_id(String dtl_cd_id) {
		this.dtl_cd_id = dtl_cd_id;
	}
	public String getMst_cd_nm() {
		return mst_cd_nm;
	}
	public void setMst_cd_nm(String mst_cd_nm) {
		this.mst_cd_nm = mst_cd_nm;
	}
	public String getDtl_cd_nm() {
		return dtl_cd_nm;
	}
	public void setDtl_cd_nm(String dtl_cd_nm) {
		this.dtl_cd_nm = dtl_cd_nm;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSup_mst_cd_id() {
		return sup_mst_cd_id;
	}
	public void setSup_mst_cd_id(String sup_mst_cd_id) {
		this.sup_mst_cd_id = sup_mst_cd_id;
	}
	public int getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(int use_yn) {
		this.use_yn = use_yn;
	}

	

	/***********************************************/
	//method
	/***********************************************/
	@Override
	public String toString() {
		return "CodesVO [mst_cd_id=" + mst_cd_id + ", dtl_cd_id=" + dtl_cd_id + ", mst_cd_nm=" + mst_cd_nm
				+ ", dtl_cd_nm=" + dtl_cd_nm + ", seq=" + seq + ", sup_mst_cd_id=" + sup_mst_cd_id + ", use_yn="
				+ use_yn + "]";
	}	
	
}
