<%@ page import="com.sn.common.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	//contextPath
	String contextPath = request.getContextPath();
	contextPath = "http://localhost:8080/"+contextPath;
%>	

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- JQuery CDN -->
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
 
<!-- BootStrap CDN -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<title>Insert title here</title>
</head>
<body>
	<h2>form</h2>
	<hr/>
	<div>
		<table class="table table-bordered table-hover table-condensed" border="1px" 
				   cellpadding="2" cellspacing="2" align="center" width="500px;" height="100px;">
		
			    <tr>
					<td>분야</td>
					<td>구분자</td>
				</tr>
				<tr>
					<td>제목</td>
					<td></td>
				</tr>
				<tr>
					<td>ppt첨부</td>
					<td><input type="button" value="파일첨부" />파일을 첨부해주세요. </td>
				</tr>
				<tr>
					<td>내용</td>
					<td></td>
			</tr>
			
			
   			<div class="row">
	   			<div class="span1">
					<table class="table table-bordered table-hover table-condensed" border="1px" 
						   cellpadding="2" cellspacing="2" align="center"  width="80%">
					
							<tr>
								<td>제목(Not Null)</td>
							</tr>
							<tr height="150px;">
								<td>내용(Not Null)</td>
							</tr>
							<tr>
								<td align="right">글자수 용량</td>
							</tr>
							<tr height="100px;">
								<td align="right"><input type="button" value="+" /><input type="button" value="-" /></td>
							</tr>
					
					</table><!-- 내용테이블 -->
				</div> <!-- span1 -->
				<div class="span2">
					<table class="table table-bordered table-hover table-condensed" border="1px" 
						   cellpadding="2" cellspacing="2" align="center"  width="20%">
						<input type="button" value="▲" /><br/>
						<input type="button" value="▼" />
					</table>
				</div><!-- span2 -->
			
			</div> <!-- row div -->
			
		
		
		</table><!-- 바깥테이블 -->
	</div><!-- 바깥테이블 div -->
	
	<div align="center">
		<tr>
			<input type="button" value="작성취소">
			<input type="button" value="작성완료">
		</tr>
	</div>
</body>
</html>