<%@page import="com.sn.common.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  //contextPath
  String contextPath = request.getContextPath();
  contextPath = "http://localhost:8080/"+contextPath;  
%>
<%-- 
<%
	int bottomCount = 10;
	String searchDiv = "";
	String searchWord = "";
	String page_size = "10";
	String page_num = "1";
	int totalCnt = 0; //총글수

	searchDiv = StringUtil.nvl(request.getParameter("searchDiv"), "");
	searchWord = StringUtil.nvl(request.getParameter("searchWord"), "");
	page_size = StringUtil.nvl(request.getParameter("page_size"), "10");
	page_num = StringUtil.nvl(request.getParameter("page_num"), "1");

	int oPage_size = Integer.parseInt(page_size);
	int oPage_num = Integer.parseInt(page_num);

	totalCnt = Integer.parseInt(StringUtil.nvl(request.getAttribute("totalCnt").toString(), "0"));
%> 
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 부트스트랩 -->
<link href="<%=contextPath%>/resources/css/bootstrap.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/css/bootstrap-theme.min.css" rel="stylesheet">
<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
<script type="text/javascript" src="<%=contextPath%>/resources/js/jquery-3.2.1.js"></script>
<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
<script src="<%=contextPath%>/resources/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<h2>전문가 조회</h2>
	<hr/>
	<form action="#" name="frm" method="post" class="form-inline">
		<table class="table table-bordered table-hover table-condensed" border="1px" 
			   cellpadding="2" cellspacing="2" align="center" width="600px;">
			<tr>
				<td style="text-align: center;">분야</td>
				<td>
					<input type="checkbox">유통·무역</input>
					<input type="checkbox">영업고객상담</input>
					<input type="checkbox">IT인터넷</input>
					<input type="checkbox">건설</input><br/>
					<input type="checkbox">생산·제조</input>
					<input type="checkbox">경영사무</input>
					<input type="checkbox">전문직</input>
					<input type="checkbox">서비스</input><br/>
					<input type="checkbox">디자인</input>
					<input type="checkbox">미디어</input>
					<input type="checkbox">의료</input>
					<input type="checkbox">교육</input>
					<input type="checkbox">특수계층·공금</input>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">검색</td>
				<td>
					<input type="text" />
					<input type="submit" value="검색"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">총 n건이 검색되었습니다.</td>
			</tr>
		</table> 
		<hr/>
		<div align="right">
			<select class="form-control input-sm">
				<option value="">정렬기준</option>
				<option value="최신순">최신순</option>
				<option value="조회순">조회순</option>
				<option value="추천순">추천순</option>
			</select>
		</div>
		<table class="table table-bordered table-hover table-condensed" border="1px" 
			   cellpadding="2" cellspacing="2" align="center" width="600px;">
			<tr>
				<td><img alt="" > </td>
			</tr>
		</table>
	</form>
<%-- 	
	<div class="form-inline text-center ">
		<%=StringUtil.renderPaging(totalCnt, oPage_num, oPage_size, bottomCount, "do_search.do", "do_search_page") %>
	</div> 
--%>
</body>
</html>