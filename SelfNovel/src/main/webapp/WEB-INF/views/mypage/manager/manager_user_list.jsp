<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.sn.common.StringUtil"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%
	//넘겨받아야 하는 값:
	//page_size
	//page_num
	//totalCnt
	//userList
	
	/**************************
	* 페이징 및 검색 버튼 처리를 위한 세팅  *
	***************************/
	int    bottomCount = 10;//하단에 표시될 페이지 수
	String searchDiv = "";	//검색구분
	String searchWord = "";	//검색어
	String page_size = "10";//한 페이지에 표시될 글의 수
	String page_num = "1";	//페이지 번호
	int    totalCnt = 0;	//총글수

	//request로부터 검색어, 검색구분, 페이지 등의 값을 초기화
	searchDiv  = StringUtil.nvl(request.getParameter("searchDiv"), "");
	searchWord = StringUtil.nvl(request.getParameter("searchWord"), "");	
	page_size  = StringUtil.nvl(request.getParameter("page_size"), "10");
	page_num   = StringUtil.nvl(request.getParameter("page_num"), "1");
	totalCnt   = Integer.parseInt(StringUtil.nvl(request.getParameter("totalCnt"), "0"));

	//page_size 및 page_num을 Integer값으로 넘기기 위한 캐스팅
	int oPage_size = Integer.parseInt(page_size);
	int oPage_num = Integer.parseInt(page_num);
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>회원 리스트</h2>
	<hr/>
	<form action="">
		<input type="hidden" name="page_size" value="$<%=page_size%>"/>
		<input type="hidden" name="page_num" value="<%=page_num%>"/>
		<input type="hidden" name="page_num" value="<%=totalCnt%>"/>
		<table border="1px" >
			<tr>
				<td>검색</td>
				<td>
					<input type="text" value="검색조건:아이디"/>
					<input type="button" value="검색"/>
				</td>
			</tr>
		</table>
	</form>
	<h3>총 회원수: <%=totalCnt%>명</h3>
	<hr/>
	
	<form action="">
		선택 멤버를 <input type="button" value="강제 탈퇴"/>
		<select>
			<option>정렬기준</option>
		</select>
		<table border="1px" >
			<tr>
				<th><input type="checkbox"></th>
				<th>글번호</th>
				<th>아이디</th>
				<th>가입일</th>
				<th>등급</th>
				<th>게시글 수</th>
				<th>첨삭 수</th>
			</tr>
			<c:forEach var="userVO" items="${userList}">
				<tr>
					<td><input type="checkbox"></td>
					<td>글번호</td>
					<td>${userVO.u_id}</td>
					<td>${userVO.u_reg_dt}</td>
					<td>${userVO.u_level}</td>
					<td>${userVO.u_write_cnt}</td>
					<td>${userVO.u_mod_cnt}</td>
				</tr>
			</c:forEach>
		</table>
	</form>
	<form action="">
		<input type="button" value="관리자 추가">
	</form>
	
	
</body>
</html>