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
	String SEARCH_DIV = "";	//검색구분
	String SEARCH_WORD = "";	//검색어
	String PAGE_SIZE = "10";//한 페이지에 표시될 글의 수
	String PAGE_NUM = "1";	//페이지 번호
	String totalCnt = "0";	//총글수

	//request로부터 검색어, 검색구분, 페이지 등의 값을 초기화
	SEARCH_DIV  = StringUtil.nvl(request.getParameter("SEARCH_DIV"), "");
	SEARCH_WORD = StringUtil.nvl(request.getParameter("SEARCH_WORD"), "");	
	PAGE_SIZE  = StringUtil.nvl(request.getParameter("PAGE_SIZE"), "10");
	PAGE_NUM   = StringUtil.nvl(request.getParameter("PAGE_NUM"), "1");
	totalCnt   = StringUtil.nvl(request.getParameter("totalCnt"), "0");

	//page_size 및 page_num을 Integer값으로 넘기기 위한 캐스팅
	int oPage_size = Integer.parseInt(PAGE_SIZE);
	int oPage_num = Integer.parseInt(PAGE_NUM);
	int oTotalCnt = Integer.parseInt(totalCnt);
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
		<input type="hidden" name="PAGE_SIZE" value="<%=PAGE_SIZE%>"/>
		<input type="hidden" name="PAGE_NUM" value="<%=PAGE_NUM%>"/>
		<input type="hidden" name="totalCnt" value="<%=oTotalCnt%>"/>
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
	<h3>총 회원수: <c:out value="">${totalCnt}명</c:out></h3>
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
			<c:choose>
				<c:when test="${userList.size()>0}">
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
				</c:when>	
				<c:otherwise>
					리스트가 없습니다.
				</c:otherwise>
			</c:choose>			
		</table>
	</form>
	<form action="">
		<input type="button" value="관리자 추가">
	</form>
	
	
</body>
</html>