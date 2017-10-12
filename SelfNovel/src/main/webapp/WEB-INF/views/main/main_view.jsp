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
	<table class="table table-bordered table-hover table-condensed" border="1px" align="center" ><!-- style="width: 610px;" -->
		<tr>
			<td align="center"><img src="../resources/img/main.jpg" /></td><!-- width="600px;" height="300px;" -->
		</tr>
		<tr>
			<td>
				<div align="center">
					<h3>
						<b>전문가 랭킹</b>
					</h3>
					<hr/>
					<table align="center"><!--  style="width: 610px;" -->
						<thead>
							<tr>
								<th style="text-align: center;">1위</th>
								<th style="text-align: center;">2위</th>
								<th style="text-align: center;">3위</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<c:forEach var="expertVO" items="${rank_list}">
									<td>
										<div>
											<br/>
											<table class="table table-bordered table-hover table-condensed" border="1px" align="center" style="width: 200px;">
												<tr>
													<td colspan="2">
														<img src="/controller/resources/exp_profiles/${expertVO.exp_profile}" width="190px;" height="150px;">
													</td>
												</tr>
												<tr>
													<td style="text-align: center;">전문가 ID</td>
													<td><p>${expertVO.exp_id}</p></td>
												</tr>
												<tr>
													<td style="text-align: center;">분야</td>
													<td><p>${expertVO.dtl_cd_nm}</p></td>
												</tr>
												<tr>
													<td style="text-align: center;">첨삭 건수</td>
													<td><p>${expertVO.exp_trade}</p></td>
												</tr>
											</table>
										</div>
									</td>
								</c:forEach>
							</tr>
						</tbody>
					</table>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>