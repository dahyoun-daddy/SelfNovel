package com.sn.img.domain;

import com.sn.common.DTO;

/**
 * ImgVO
 * 
 * 최초작성: 2017-09-21
 * @author sist
 *
 */ 
public class ImgVO extends DTO {
	private int img_id;     //이미지ID
	private int img_num;    //이미지 순서
	private String img_org_nm; //원본 파일 이름
	private String img_sv_nm;  //저장 파일 이름
	private String img_path;   //파일 경로
	private int img_use_yn; //사용여부
	
	/***********************************************/
	//constructor
	/***********************************************/
	public ImgVO() {
		
	}
	
	/***********************************************/
	//getter and setter
	/***********************************************/
	public int getImg_id() {
		return img_id;
	}

	public void setImg_id(int img_id) {
		this.img_id = img_id;
	}

	public int getImg_num() {
		return img_num;
	}

	public void setImg_num(int img_num) {
		this.img_num = img_num;
	}

	public String getImg_org_nm() {
		return img_org_nm;
	}

	public void setImg_org_nm(String img_org_nm) {
		this.img_org_nm = img_org_nm;
	}

	public String getImg_sv_nm() {
		return img_sv_nm;
	}

	public void setImg_sv_nm(String img_sv_nm) {
		this.img_sv_nm = img_sv_nm;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public int getImg_use_yn() {
		return img_use_yn;
	}

	public void setImg_use_yn(int img_use_yn) {
		this.img_use_yn = img_use_yn;
	}

	/***********************************************/
	//method
	/***********************************************/
	@Override
	public String toString() {
		return "ImgVO [img_id=" + img_id + ", img_num=" + img_num + ", img_org_nm=" + img_org_nm + ", img_sv_nm="
				+ img_sv_nm + ", img_path=" + img_path + ", img_use_yn=" + img_use_yn + "]";
	}
	
}
