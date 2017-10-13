<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("utf-8"); %>
<%
	String contextPath = request.getContextPath();
	contextPath = "http://localhost:8080/"+contextPath;
%>
<%
	String clientId = "KohD8sjB6zl3Ue8J49uV";//애플리케이션 클라이언트 아이디값";
	String redirectURI = URLEncoder.encode("http://localhost:8080/controller/user/naver_callback.do", "UTF-8");
	SecureRandom random = new SecureRandom();
	String state = new BigInteger(130, random).toString();
	String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
	apiURL += "&client_id=" + clientId;
	apiURL += "&redirect_uri=" + redirectURI;
	apiURL += "&state=" + state;
	session.setAttribute("state", state);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link href="<c:url value='/resources/css/layout.css' />" rel="stylesheet"></link>
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function n_login(){
		var u_id = $("#login_id").val();
		var u_password = $("#login_pwd").val();
		var u_level = $(":input:radio[name=userSep]:checked").val();
		var userSep = "";
		
		if(u_id == '' || u_id == null || u_password == '' || u_password == null){
			alert("아이디 또는 비밀번호를 입력해 주세요.");
			return;
		}
		
		if(u_level == '1' || u_level == '0'){ userSep = "user" }
		else { userSep = "expert" }
		
		$.ajax({
            url: userSep+"/do_searchOne.do",
            data: {u_id: u_id,
            	   u_password: u_password,
            	   u_level: u_level
            	  },
            type: 'POST',
            success: function(result){
            	if(result == "fail"){
            		alert("아이디 또는 비밀번호를 확인해 주세요.");
            	} else{
            		loginFrm.submit();
            	}
            }
        });
	}
</script>
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<div class="form-group" style="text-align: center; margin: 0 auto;">
			<div class="btn-group" data-toggle="buttons">
				<label class="btn btn-primary active">
					<input type="radio" name="userSep" value="1" checked>일반
				</label>
				<label class="btn btn-primary">
					<input type="radio" name="userSep" value="2">전문가
				</label>
			</div>
		</div>
		<br/>
		<input class="form-control" style="width:30%;" id="login_id" type="text" /><br>
		<input class="form-control" style="width:30%;" id="login_pwd" type="password" /><br>
		<form id="loginFrm" action="home.do" method="POST" >
			<input class="btn btn-warning" style="width:30%;" type="button" value="일반 로그인" onclick="n_login()"/><br><br> 
		</form>
		<form id="naverFrm" action="register_user.do">
			<a href="<%=apiURL%>"><img height="50" src="<c:url value='/resources/img/naverid_login_button.png' />"/></a>
		</form>
	</div>
</body>
</html>