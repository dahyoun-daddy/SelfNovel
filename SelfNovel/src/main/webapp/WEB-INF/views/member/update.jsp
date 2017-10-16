<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("utf-8"); %>
<%
	//contextPath
	String contextPath = request.getContextPath();
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
	var re_password = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{4,20}$/;
	var re_price = /^[0-9_-]{1,6}$/; // 가격 검사식
	var u_level = '<%=session.getAttribute("u_level")%>';
	var userSep = "";
	
	$(document).ready(function(){
		if(u_level == '1'){ userSep='user'; }
		else { userSep='expert'; }
		
		if(userSep=='user'){
			for(var i=1 ; i<11; i++){
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
            	$("#h_password").val(data.u_password);
            	if(u_level == '2'){
            		$("#exp_title").val(data.exp_title);
            		$("#exp_price").val(data.exp_price);
            		var str = data.exp_profile.substring(0,6);
            		if(str == 'https:'){
            			$("#profileHolder").attr("src", data.exp_profile);	
            		} else {
            			$("#profileHolder").attr("src", "/controller/resources/exp_profiles/" + data.exp_profile);	
            		}            		
            		$("#exp_ctg").val(data.exp_ctg);
            		$("#old_profile").val(data.exp_profile);
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
	
	function changeProfile(functionSep){
		if(functionSep == 'password'){
			if(re_password.test($("#new_password").val()) != true){
				alert("새로운 비밀번호가 잘못 입력되었습니다.");
				$("#new_password").focus();
				return;
			} else if(re_password.test($("#new_password2").val()) != true){
				alert("비밀번호 확인이 잘못 입력되었습니다.");
				$("#new_password2").focus();
				return;
			} else if($("#new_password").val() != $("#new_password2").val()){
				alert("새로운 비밀번호가 서로 다릅니다.");
				$("#new_password").focus();
				return;
			} else if($("#u_password").val() == $("#new_password").val()){
				alert("같은 비밀번호로 변경할 수 없습니다.");
				$("#new_password").focus();
				return;
			} else if($("#h_password").val() != $("#u_password").val()) {
				alert("기존 비밀번호가 틀렸습니다.");
				$("#u_password").focus();
				return;
			}
		} else {
			if($("#u_name").val().length > 16 || $("#u_name").val().length < 4){
				alert("이름(닉네임)이 잘못 입력되었습니다.");
				$("#u_name").focus();
				return;
			} else if(<%=session.getAttribute("u_level")%> == '2'){
				if($("#exp_title").val().length > 40 || $("#exp_title").val().length < 1){
					alert("프로필 제목이 잘못 입력되었습니다.");
					$("#exp_title").focus();
					return;
				} else if(re_price.test($("#exp_price").val()) != true){
					alert("가격이 잘못 입력되었습니다.");
					$("#exp_price").focus();
					return;
				}
			}
		}
		
		var formData = new FormData();
		formData.append("u_id",$("#u_id").val());
		formData.append("u_password",$("#new_password").val());
		formData.append("u_name", $("#u_name").val());
		formData.append("exp_profile", $("#exp_profile")[0].files[0]);
		formData.append("exp_title", $("#exp_title").val());
		formData.append("exp_price", $("#exp_price").val());
		formData.append("exp_ctg", $("#ext_ctg").val());
		formData.append("functionSep", functionSep);
		
		$.ajax({
		        url: userSep+"/do_update.do",
		        processData: false,
		        contentType: false,
		        data: formData,
		        type: 'POST',
		        success: function(result){
		        	if(result == 'fail'){
		        		alert("회원 정보 수정 실패.");
		        	} else if(result == 'success'){
		            	updateFrm.submit();	
		        	}
		        }
		    });
	}
	
	function getImage(){
	   	document.getElementById("exp_profile").click();
	 }
	
	function chk_file_type(event) {
		var thumbext = document.getElementById("exp_profile").value; //파일을 추가한 input 박스의 값
		thumbext = thumbext.slice(thumbext.indexOf(".") + 1).toLowerCase(); //파일 확장자를 잘라내고, 비교를 위해 소문자로 만듭니다.
		if(thumbext != "jpg" && thumbext != "png" &&  thumbext != "gif" &&  thumbext != "bmp"){ //확장자를 확인합니다.
			alert("이미지 파일만 등록할 수 있습니다.");
			return;
		} else {
			var selectedFile = event.target.files[0];
			  var reader = new FileReader();
			  var imgtag = document.getElementById("profileHolder");
			  imgtag.title = selectedFile.name;

			  reader.onload = function(event) {
			    imgtag.src = event.target.result;
			    imgtag.width = 150;
			    imgtag.height = 150;
			  };
			  reader.readAsDataURL(selectedFile);
		}
	}
	
	function open_delete(){
		$('#auth_modal').modal({backdrop: 'static', keyboard: false});
	}
	
	function do_delete(){
		if($("#authPwd").val() != $("#h_password").val()){
			alert("비밀번호가 틀렸습니다.");
			return;
		}
		
		$.ajax({
	        url: "user/do_delete.do",
	        data: {u_id: $("#u_id").val()},
	        type: 'POST',
	        success: function(result){
	        	if(result == 'fail'){
	        		alert("회원탈퇴 실패.");
	        	} else if(result == 'success'){
	            	updateFrm.submit();	
	        	}
	        }
	    });
	}
</script>
<title>Insert title here</title>
</head>
<body>
	회원 정보 수정
	<hr/>
	<input type="hidden" value="" id="h_password" />
	<input type="hidden" value="" id="old_profile" />
	<form action="<%=contextPath %>/main/main.do" method="POST" id="updateFrm">
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
				<td><input class="form-control" id="u_password" type="password"/></td>
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
				<td><input class="form-control" id="new_password" type="password"/></td>
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
				<td><input class="form-control" id="new_password2" type="password"/></td>
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
						<input class="btn btn-warning" type="button" onclick="changeProfile('password')" value="비밀번호 수정">
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div style="padding-left:50px; border-left: 2px solid #EAEAEA;; width:49%; display: inline-block;">
		<table style="width:80%;">
			<tr>
				<td style="width:30%;"><div align="right">아이디(닉네임):</div></td>
				<td><input class="form-control" type="text" id="u_name" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="center">
						<label id="lu_name" style="color: red;"></label>
					</div>
				</td>
			</tr>
			<tr>
				<td style="width:30%;">
					<div id="item9" align="right">
						프로필 제목:
					</div> 
				</td>
				<td style="width:70%;">
					<div id="item1">
						<input class="form-control" id="exp_title" name="exp_title" type="text"/>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<div id="item2" align="center">
						<label id="lexp_title" style="color: red;"></label>
						<br/>
					</div>
				</td>
			</tr>
			<tr>
				<td style="width:30%;">
					<div id="item8" align="right">
						전문분야: 
					</div>
				</td>
				<td style="width:70%;">
					<div class="col-xs-5 selectContainer" id="item3">
            			<select id="ext_ctg" name="ext_ctg" class="form-control">
						  <option value="1">서비스업</option>
						  <option value="2">제조·화학</option>
						  <option value="3">의료·제약·복지</option>
						  <option value="4">판매·유통</option>
						  <option value="5">교육업</option>
						  <option value="6">건설업</option>
						  <option value="7">IT·웹·통신</option>
						  <option value="8">미디어·디자인</option>
						  <option value="9">은행·금융업</option>
						  <option value="10">기관·협회</option>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<td style="width:30%;">
					<div id="item10" align="right">
						가격: 
					</div>
				</td>
				<td style="width:70%;">
					<div id="item4">
						<input class="form-control" id="exp_price" name="exp_price" type="text"/>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<div id="item5" align="center">
						<label id="lexp_price" style="color: red;"></label>
						<br/>
					</div>
				</td>
			</tr>
			<tr>
				<td style="width:30%;">
				</td>
				<td  style="width:70%;">
					<div id="item6" align="center">
						<img id="profileHolder" width="150" height="150"/>
					</div>
				</td>
			</tr>
			<tr>
				<td style="width:30%;">
				</td>
				<td style="width:70%;">
					<div id="item7" align="center">
						<div class="btn btn-success" align="center" id="yourBtn" onclick="getImage()">프로필 사진 변경</div>
							<input id="exp_profile" name="exp_profile" type="file" style="display:none" onchange="chk_file_type(event)" accept="image/*"/>
						<br/>
					</div>
				</td>
			</tr>
			<tr>
				<td style="width:30%;">
				</td>
				<td style="width:70%;">
					<div align="right">
						<input class="btn btn-warning" type="button" onclick="changeProfile('name')" value="프로필 정보 수정">
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
	<div align="center">
		<input class="btn btn-danger" type="button" value="회원탈퇴" onclick="open_delete()"/>
	</div>
	
	<!-- 회원탈퇴 비밀번호 입력 모달 -->
	<div class="modal fade" id="auth_modal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content panel-info %>" >
					<div class="modal-header panel-heading">
						<h4 class="modal-title">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="cancelAuth();">
			          		×
			        		</button>
							<b>비밀번호 입력</b>
						</h4>
					</div>
					<div class="modal-body" id="checkMessage">
						<table cellpadding="5" style="text-align: center; solid #dddddd">
						<tr>
							<td align="left" style="width: 110px;"><h5><b>비밀번호</b></h5></td>
							<td><input class="form-control" type="password" id="authPwd" name="authPwd" maxLength="20"></td>
							<td style="width: 110px;">
								<button class="btn btn-danger" type="button" onclick="do_delete()"><b>탈퇴하기</b></button>
							</td>
						</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>