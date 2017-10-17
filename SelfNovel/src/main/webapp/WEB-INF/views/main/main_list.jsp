<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  //contextPath
  String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 부트스트랩 -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.min.css" rel="stylesheet">
<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.js"></script>
<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>

	<h2>전체검색 결과</h2><br/>
	<h4>자기소개서</h4><br/>
	<c:forEach var="rsmVO" items="${rsmList }">
		<li>
		<a href="/controller/resume/do_searchOne.do?rsm_id=${rsmVO.rsm_id }">
			<c:out value="${rsmVO.rsm_title }"></c:out>
		</a>
		</li><br/>
	</c:forEach>
	<div align="right">
		<input type="button" value="검색결과 더 보기">
	</div>
	<hr/>
	<h4>전문가</h4><br/>
	<c:forEach var="expVO" items="${expList }">
		<li>
		<a href="/controller/expert/do_detail_list.do?exp_id=${expVO.exp_id }">
			<c:out value="${expVO.exp_title }"></c:out>
		</a>	
		</li><br/>
	</c:forEach>
	<div align="right">
		<input type="button" value="검색결과 더 보기">
	</div>	
</body>
</html>