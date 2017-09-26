package com.sn.expert.domain;

import com.sn.user.domain.UserVO;

/**
 * 전문가 회원 VO
 * USERS 테이블과 조인해서 사용 예정
 * UserVO를 상속 받고 다른 부분만 추가해 사용
 * @author 정현준
 */
public class ExpertVO extends UserVO {
	private String exp_id;		// 전문가 아이디(=USERS 테이블의 u_id)
	private String exp_title;	// 프로필 제목
	private String exp_profile;	// 프로필 사진 파일 이름
	private int exp_ctg;		// 전문분야
	private int exp_price;		// 가격
	private int exp_trade;		// 거래수

	@Override
	public String toString() {
		return "ExpertVO [exp_id=" + exp_id + ", exp_title=" + exp_title + ", exp_profile="
				+ exp_profile + ", exp_ctg=" + exp_ctg + ", exp_price=" + exp_price + ", exp_trade=" + exp_trade + "]";
	}

	public String getExp_id() {
		return exp_id;
	}

	public void setExp_id(String exp_id) {
		this.exp_id = exp_id;
	}
	
	public String getExp_title() {
		return exp_title;
	}

	public void setExp_title(String exp_title) {
		this.exp_title = exp_title;
	}

	public String getExp_profile() {
		return exp_profile;
	}

	public void setExp_profile(String exp_profile) {
		this.exp_profile = exp_profile;
	}

	public int getExp_ctg() {
		return exp_ctg;
	}

	public void setExp_ctg(int exp_ctg) {
		this.exp_ctg = exp_ctg;
	}

	public int getExp_price() {
		return exp_price;
	}

	public void setExp_price(int exp_price) {
		this.exp_price = exp_price;
	}

	public int getExp_trade() {
		return exp_trade;
	}

	public void setExp_trade(int exp_trade) {
		this.exp_trade = exp_trade;
	}

}
