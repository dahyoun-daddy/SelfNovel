package com.sn.user.domain;

import com.sn.common.DTO;

/**
 * 회원 공통 VO
 * @author 정현준
 *
 */
public class UserVO extends DTO {
	private String u_id;		// 회원 아이디(이메일)
	private String u_password;	// 비밀번호
	private String u_name;		// 닉네임
	private String u_reg_dt;	// 가입일
	private int u_level;		// 등급
	private int u_write_cnt;	// 게시물 등록수
	private int u_mod_cnt;		// 첨삭 수
	private String u_naver;

	@Override
	public String toString() {
		return "UserVO [u_id=" + u_id + ", u_password=" + u_password + ", u_name=" + u_name + ", u_reg_dt=" + u_reg_dt
				+ ", u_level=" + u_level + ", u_write_cnt=" + u_write_cnt + ", u_mod_cnt=" + u_mod_cnt + "]";
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getU_password() {
		return u_password;
	}

	public void setU_password(String u_password) {
		this.u_password = u_password;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_reg_dt() {
		return u_reg_dt;
	}

	public void setU_reg_dt(String u_reg_dt) {
		this.u_reg_dt = u_reg_dt;
	}

	public int getU_level() {
		return u_level;
	}

	public void setU_level(int u_level) {
		this.u_level = u_level;
	}

	public int getU_write_cnt() {
		return u_write_cnt;
	}

	public void setU_write_cnt(int u_write_cnt) {
		this.u_write_cnt = u_write_cnt;
	}

	public int getU_mod_cnt() {
		return u_mod_cnt;
	}

	public void setU_mod_cnt(int u_mod_cnt) {
		this.u_mod_cnt = u_mod_cnt;
	}

	public String getU_naver() {
		return u_naver;
	}

	public void setU_naver(String u_naver) {
		this.u_naver = u_naver;
	}

	
}
