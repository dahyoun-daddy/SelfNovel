<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("utf-8"); %>
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
		
		if(u_level == '1'){ userSep = "user" }
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
            	} else {
            		var data = JSON.parse(result);
            		$("#u_id").val(data.u_id);
            		$("#u_name").val(data.u_name);
            		$("#u_level").val(data.u_level);
            		
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
			<input style="width:30%;" type="button" value="구글 로그인" onclick="g_login()"/>
			<input type="hidden" id="u_id" name="u_id" value="" />
			<input type="hidden" id="u_name" name="u_name" value="" />
			<input type="hidden" id="u_level" name="u_level" value="" />
		</form>
	</div>
</body>
</html>