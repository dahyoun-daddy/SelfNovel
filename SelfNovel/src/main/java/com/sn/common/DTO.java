package com.sn.common;

import java.util.Hashtable;

/**
 * DTO
 * detail: vo 최상위 클래스
 * 
 * 최초작성: 2017-09-21
 * @author sist 
 *
 */
public class DTO {
	/**
	 * 조회인자
	 * 조회구분: searchDiv,
	 * 조회Data: searchWord
	 * 조회Page_size: pageSize
	 * 
	 */
	private Hashtable<String,String> param = new Hashtable<String,String>();
	
	/**
	 * 글번호
	 */
	private int no;
	/**
	 * 총글수
	 */
	private int totalNo;

	public Hashtable<String, String> getParam() {
		return param;
	}

	public void setParam(Hashtable<String, String> param) {
		this.param = param;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(int totalNo) {
		this.totalNo = totalNo;
	}
}
