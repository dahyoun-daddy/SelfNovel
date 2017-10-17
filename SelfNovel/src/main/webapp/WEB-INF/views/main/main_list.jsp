<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
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
<script type="text/javascript">
	$(document).ready(function(){
		$("#doSearchRsm").click(function(){
			var frm = document.searchFrm;
			frm.action = "/controller/resume/do_search.do";
			frm.submit();
		});
		
		$("#doSearchExp").click(function(){
			var frm = document.searchFrm;
			frm.action = "/controller/expert/do_search.do";
			frm.submit();				
		});
		
	});
</script>
</head>
<body>
	<!-- 검색용 form -->
	<form name="searchFrm" id="searchFrm" action="" method="POST">
		<input type="hidden" name="searchWord" value="${searchWord}">
	</form>


	<h2>전체검색 결과</h2><br/>
	<h4>자기소개서</h4><br/>
	
	<c:choose>
		<c:when test="${fn:length(rsmList) eq 0}">
		<!-- resume list. 사이즈가 0일 경우 X -->
			<div align="center">
				<p>검색 결과가 없습니다.</p>
			</div>	
		</c:when>
		<c:otherwise>
		<!-- resume list. 사이즈가 0이 아닐 경우. 최대 10개 출력된다. -->
			<div class="jumbotron" style="padding: 20px;">
				<c:forEach var="rsmVO" items="${rsmList }">
					<a href="/controller/resume/do_searchOne.do?rsm_id=${rsmVO.rsm_id }">
						<c:out value="${rsmVO.rsm_title }"></c:out>
					</a>
					<br/>
				</c:forEach>
			</div>
			<c:if test="${fn:length(rsmList) eq 10}">
				<!-- 10개 이상인 경우 검색결과 더 보기 -->
				<div align="right">
					<input class="btn btn-default" type="button" id="doSearchRsm" value="검색결과 더 보기">
				</div>
			</c:if>				
		</c:otherwise>
	</c:choose>
		
	<hr/>
	<h4>전문가</h4><br/>
	
	<c:choose>
		<c:when test="${fn:length(expList) eq 0}">
		<!-- expert list. 사이즈가 0일 경우 X -->
			<div align="center">
				<p>검색 결과가 없습니다.</p>
			</div>			
		</c:when>
		<c:otherwise>
		<!-- expert list. 사이즈가 0이 아닐 경우. 최대 10개 출력된다. -->
			<div class="jumbotron" style="padding: 20px;">
				<c:forEach var="expVO" items="${expList }">
					<a href="/controller/expert/do_detail_list.do?exp_id=${expVO.exp_id }">
						<c:out value="${expVO.exp_title }"></c:out>
					</a>	
					<br/>
				</c:forEach>
			</div>
			<c:if test="${fn:length(expList) eq 10}">
				<!-- 10개 이상인 경우 검색결과 더 보기 -->
				<div align="right">
					<input class="btn btn-default" type="button" id="doSearchExp" value="검색결과 더 보기">
				</div>	
			</c:if>			
		</c:otherwise>
	</c:choose>	
</body>
</html>