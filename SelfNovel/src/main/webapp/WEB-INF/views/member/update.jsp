<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	var re_password = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{4,20}$/;
	var u_level = '<%=session.getAttribute("u_level")%>';
	var userSep = "";
	
	$(document).ready(function(){
		if(u_level == '1'){ userSep='user'; }
		else { userSep='expert'; }
		
		if(userSep=='user'){
			for(var i=1 ; i<8; i++){
				document.getElementById('item'+i).style.display = "none";
			}
		}
		
		var u_id = '<%=session.getAttribute("u_id")%>';
		$.ajax({
            url: userSep+"/do_preview.do",
            data: {u_id: u_id},
            type: 'POST',
            success: function(result){
            	var data = JSON.parse(result);
            	$("#u_id").val(data.u_id);
            	$("#u_name").val(data.u_name);
            	$("#u_password").val(data.u_password);
            	if(u_level == '2'){
            		$("#exp_title").val(data.exp_title);
            		$("#exp_price").val(data.exp_price);
            		/* $("#exp_profile")
            		$("#exp_ctg") */
            	}
            }
        });
		
		$("#new_password").keyup(function(){
			if($("#new_password").val().length == 0){
				$("#lnew_password").text('');
			} else if($("#new_password").val().length < 4){
				$("#lnew_password").text('너무 짧습니다.');
			} else if($("#new_password").val().length > 20){
				$("#lnew_password").text('너무 깁니다.');
			} else if(re_password.test($("#new_password").val()) != true){
				$("#lnew_password").text('영문/숫자/특수문자 조합이어야 합니다.');
			} else if($("#new_password").val() != $("#new_password2").val()){
				$("#lnew_password").text('비밀번호가 서로 다릅니다.');
			} else{
				$("#lnew_password").text('');
				$("#lnew_password2").text('');
			}
		});
		
		$("#new_password2").keyup(function(){
			if($("#new_password2").val().length == 0){
				$("#lnew_password2").text('');
			} else if($("#new_password2").val().length < 4){
				$("#lnew_password2").text('너무 짧습니다.');
			} else if($("#new_password2").val().length > 20){
				$("#lnew_password2").text('너무 깁니다.');
			} else if(re_password.test($("#new_password2").val()) != true){
				$("#lnew_password2").text('영문/숫자/특수문자 조합이어야 합니다.');
			} else if($("#new_password").val() != $("#new_password2").val()){
				$("#lnew_password2").text('비밀번호가 서로 다릅니다.');
			} else{
				$("#lnew_password").text('');
				$("#lnew_password2").text('');
			}
		});
		
		$("#u_name").keyup(function(){
			if($("#u_name").val().length == 0){
				$("#lu_name").text('');
			}else if($("#u_name").val().length < 4){
				$("#lu_name").text('너무 짧습니다.');
			} else if($("#u_name").val().length > 16){
				$("#lu_name").text('너무 깁니다.');
			} else {
				$("#lu_name").text('');
			}
		});
		
		$("#exp_title").keyup(function(){
			if($("#exp_title").val().length > 40){
				$("#lexp_title").text('너무 깁니다.');
			} else {
				$("#lexp_title").text('');
			}
		});
		
		$("#exp_price").keyup(function(){
			if($("#exp_price").val().length == 0){
				$("#lexp_price").text('');
			} else if($("#exp_price").val().length > 6){
				$("#lexp_price").text('숫자가 너무 큽니다.');
			} else if(re_price.test($("#exp_price").val()) != true){
				$("#lexp_price").text('숫자만 입력할 수 있습니다.');
			} else{
				$("#lexp_price").text('');
			}
		});
	});
	
	function changePwd(){
		
	}
	
	function changeName(){
		
	}
</script>
<title>Insert title here</title>
</head>
<body>
	회원 정보 수정
	<hr/>
	<input type="hidden" value="" id="u_password"/>
	<div style="width:49%; display: inline-block;" align="center">
		<table style="width:80%;">
			<tr>
				<td><div align="right">아이디(이메일):</div></td>
				<td><input class="form-control" id="u_id" type="text" readonly/></td>
			</tr>
			<tr>
				<td colspan="2"><label id="lu_id" style="color: red;"></label></td>
			</tr>
			<tr>
				<td><div align="right">기존 비밀번호:</div></td>	
				<td><input class="form-control" id="u_password" type="text"/></td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="right">
						<label id="lu_password" style="color: red;"></label>
					</div>
				</td>
			</tr>
			<tr>
				<td><div align="right">새로운 비밀번호:</div></td>
				<td><input class="form-control" id="new_password" type="text"/></td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="right">
						<label id="lnew_password" style="color: red;"></label>
					</div>
				</td>
			</tr>
			<tr>
				<td><div align="right">새로운 비밀번호 확인:</div></td>
				<td><input class="form-control" id="new_password2" type="text"/></td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="right">
						<label id="lnew_password2" style="color: red;"></label>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="right">
						<input class="btn btn-warning" type="button" onclick="changePwd()" value="비밀번호 수정">
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div style="width:49%; display: inline-block;">
		<table style="width:80%;">
			<tr>
				<td><div align="right">아이디(닉네임):</div></td>
				<td><input class="form-control" type="text" id="u_name" /></td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<div id="item1">
						<input class="form-control" id="exp_title" name="exp_title" type="text" placeholder="프로필 제목 입력"/>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<div id="item2">
						<label id="lexp_title" style="color: red;"></label>
						<br/>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<div class="col-xs-5 selectContainer" id="item3">
            			<select id="ext_ctg" name="ext_ctg" class="form-control">
						  <option value="1">서비스업</option>
						  <option value="2">제조·화학</option>
						  <option value="3">의료·제약·복지</option>
						  <option value="4">판매·유통</option>
						  <option value="5">교육업</option>
						  <option value="6">건설업</option>
						  <option value="7" selected>IT·웹·통신</option>
						  <option value="8">미디어·디자인</option>
						  <option value="9">은행·금융업</option>
						  <option value="10">기관·협회</option>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<div id="item4">
						<input class="form-control" id="exp_price" name="exp_price" type="text" placeholder="가격 입력"/>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<div id="item5">
						<label id="lexp_price" style="color: red;"></label>
						<br/>
					</div>
				</td>
			</tr>
			<tr>
				<td >
					<div id="item6">
						<img id="profileHolder" width="150" height="150" src="<c:url value='/resources/img/default.png' />">
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="item7">
						<div class="btn btn-success" align="center" id="yourBtn" onclick="getImage()">프로필 사진 업로드</div>
						<input id="exp_profile" name="exp_profile" type="file" style="display:none" onchange="chk_file_type(event)" accept="image/*"/>
						<br/>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="right">
						<input class="btn btn-warning" type="button" onclick="changeName()" value="프로필 정보 수정">
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>