<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link href="<c:url value='/resources/css/layout.css' />" rel="stylesheet"></link>
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

</head>
<body>
	<div class="table-responsive">
		<table class="table" id="layout">
			<tr>
				<td colspan="3" id="header"><tiles:insertAttribute name="header" /></td>
			</tr>
			<tr>
				<td colspan="3" id="menu"><tiles:insertAttribute name="menu" /></td>
			</tr>
			<tr>			
				<td id="left_blank"><tiles:insertAttribute name="left_blank" /></td>
				<td id="body"><tiles:insertAttribute name="body" /></td>
				<td id="right_blank"><tiles:insertAttribute name="right_blank" /></td>
			</tr>
			<tr>
				<td id="footer" colspan="3"><tiles:insertAttribute name="footer" /></td>
			</tr>
		</table>
	</div>
</body>
</html>