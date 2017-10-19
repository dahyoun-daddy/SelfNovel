<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//contextPath
	String contextPath = request.getContextPath();
%>
<% 
	request.setCharacterEncoding("utf-8");
	if(session.getAttribute("isNaver") == null){
		session.setAttribute("isNaver","false");
	}
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link href="<c:url value='/resources/css/layout.css' />" rel="stylesheet"></link>
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
	var re_id = /^([\w\.-]+)@([a-z\d\.-]+)\.([a-z\.]{2,6})$/; //이메일 검사식
	var re_password = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{4,20}$/;
	var re_price = /^[0-9_-]{1,6}$/; // 가격 검사식
	
	$(document).ready(function(){		
		for(var i=1 ; i<8; i++){
			document.getElementById('item'+i).style.display = "none";
		}
		
		$("#u_id").keyup(function(){
			if($("#u_id").val().length == 0){
				$("#lu_id").text('');
			} else if(re_id.test($("#u_id").val()) != true){
				$("#lu_id").text('이메일 형식이 아닙니다.');
			} else{
				$("#lu_id").text('');
			}
		});
		
		$("#u_password").keyup(function(){
			if($("#u_password").val().length == 0){
				$("#lu_password").text('');
			} else if($("#u_password").val().length < 4){
				$("#lu_password").text('너무 짧습니다.');
			} else if($("#u_password").val().length > 20){
				$("#lu_password").text('너무 깁니다.');
			} else if(re_password.test($("#u_password").val()) != true){
				$("#lu_password").text('영문/숫자/특수문자 조합이어야 합니다.');
			} else if($("#u_password").val() != $("#u_password2").val()){
				$("#lu_password").text('비밀번호가 서로 다릅니다.');
			} else{
				$("#lu_password").text('');
				$("#lu_password2").text('');
			}
		});
		
		$("#u_password2").keyup(function(){
			if($("#u_password2").val().length == 0){
				$("#lu_password2").text('');
			} else if($("#u_password2").val().length < 4){
				$("#lu_password2").text('너무 짧습니다.');
			} else if($("#u_password2").val().length > 20){
				$("#lu_password2").text('너무 깁니다.');
			} else if(re_password.test($("#u_password2").val()) != true){
				$("#lu_password2").text('영문/숫자/특수문자 조합이어야 합니다.');
			} else if($("#u_password").val() != $("#u_password2").val()){
				$("#lu_password2").text('비밀번호가 서로 다릅니다.');
			} else{
				$("#lu_password").text('');
				$("#lu_password2").text('');
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
		
		if(<%=session.getAttribute("isNaver").equals("true")%>){
			$("#u_name").val('<%=request.getAttribute("nickName")%>');
			$("#u_naver").val('<%=request.getAttribute("naverId")%>');
			$("#profileHolder").attr("src",'<%=request.getAttribute("profileImage")%>');
			alert("최초 로그인 시 회원가입이 필수입니다.");
		}
	})
	
	function do_userSep(userSep){	// 가입 항목 동적 생성
		if(userSep == "2"){
			for(var i=1 ; i<8; i++){
				document.getElementById('item'+i).style.display = "";
			}
		}else{
			for(var i=1 ; i<8; i++){
				document.getElementById('item'+i).style.display = "none";
			}
		}
	}
	
	function do_register(){	// 회원가입 버튼 클릭 시 동작
		if(re_id.test($("#u_id").val()) != true){
			alert("이메일(아이디)이 잘못 입력되었습니다.");
			$("#u_id").focus();
			return;
		} else if($("#isAuth").val() != '1'){
			alert("이메일을 인증해 주세요.");
			return;
		} else if(re_password.test($("#u_password").val()) != true){
			alert("비밀번호가 잘못 입력되었습니다.");
			$("#u_password").focus();
			return;
		} else if(re_password.test($("#u_password2").val()) != true){
			alert("비밀번호 확인이 잘못 입력되었습니다.");
			$("#u_password2").focus();
			return;
		} else if($("#u_password").val() != $("#u_password2").val()){
			alert("비밀번호가 서로 다릅니다.");
			$("#u_password").focus();
			return;
		} else if($("#u_name").val().length > 16 || $("#u_name").val().length < 4){
			alert("이름(닉네임)이 잘못 입력되었습니다.");
			$("#u_name").focus();
			return;
		} else if($(":input:radio[name=userSep]:checked").val() == '2'){
			if($("#exp_title").val().length > 40 || $("#exp_title").val().length < 1){
				alert("프로필 제목이 잘못 입력되었습니다.");
				$("#exp_title").focus();
				return;
			} else if(re_price.test($("#exp_price").val()) != true){
				alert("가격이 잘못 입력되었습니다.");
				$("#exp_price").focus();
				return;
			} else if($("#exp_profile")[0].files[0] == null){
				if(<%=session.getAttribute("isNaver").equals("false")%>){
					alert("프로필 사진 등록은 필수입니다.");
					return;	
				}
			}
		}
	
		var formData = new FormData();
		formData.append("u_id", $("#u_id").val());
		formData.append("u_naver", $("#u_naver").val());
		formData.append("u_password", $("#u_password").val());
		formData.append("u_name", $("#u_name").val());
		if($("#exp_profile")[0].files[0] == null){
			formData.append("exp_profile", $("#profileHolder").attr("src"));
		} else {
			formData.append("exp_profile", $("#exp_profile")[0].files[0]);	
		}
		
		formData.append("exp_title", $("#exp_title").val());
		formData.append("exp_price", $("#exp_price").val());
		formData.append("u_level", $(":input:radio[name=userSep]:checked").val());
		formData.append("exp_ctg", $("#ext_ctg option:selected").val());
		
		var userSep = '';
		if($(":input:radio[name=userSep]:checked").val() == '1'){ userSep = "user"; }
		else { userSep = "expert"; }
		
		$.ajax({
            url: "<%=contextPath %>/"+userSep+"/do_save.do",
            processData: false,
            contentType: false,
            data: formData,
            type: 'POST',
            success: function(result){
            	if(result == 'fail'){
            		alert("수정 실패.");
            	} else if(result == 'success'){
                	registerFrm.submit();	
            	}
            	
            }
        });
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
	
	function getImage(){
	   	document.getElementById("exp_profile").click();
	 }
	
	function sendMail(){
		if(re_id.test($("#u_id").val()) != true){
			alert('아이디(이메일) 형식이 잘못되었습니다.');
			$("#u_id").focus();
			return;
		}
		
		
		$.ajax({
            url: "<%=contextPath %>/user/do_chkId.do",
            data: {u_id: $("#u_id").val()},
            type: 'POST',
            success: function(result){
            	if(result == 'fail'){
            		alert("이미 존재하는 아이디(이메일)입니다.");
            		return;
            	} else{
            		$('#emailAuth_modal').modal({backdrop: 'static', keyboard: false});
	            	$.ajax({
	            	        url: "<%=contextPath %>/user/send_email.do",
	            	        data: {u_id: $("#u_id").val()},
	            	        type: 'POST',
	            	        success: function(authNum){
	            	        	$("#h_authNum").val(authNum);
	            	        }
	            	    });
            	}
            	
            }
        });
	}
	
	function do_auth(){
		if($("#h_authNum").val() == $("#authNum").val()){
			$("#isAuth").val('1');
			$("#authNum").val('');
			$('#emailAuth_modal').modal('hide');
		} else{
			alert('인증번호를 다시 확인해 주세요.');
		}
	}
	
	
</script>
<title>:::Register:::</title>
</head>
<body>
	<input type="hidden" value="0" id="isAuth" name="isAuth" />
	<input type="hidden" value="" id="h_authNum" name="h_authNum" />
	회원가입
	<hr/>
	<div align="center" style="width:50%; height:800px; display: inline-block;" >
		<form action="<%=contextPath %>/login_user.do" method="POST" name="registerFrm" id="registerFrm">
		<table>
			<tr>
				<td colspan="2">
					<div class="form-group" style="text-align: center; margin: 0 auto;">
						<div class="btn-group" data-toggle="buttons">
							<label class="btn btn-primary active" onclick="do_userSep('1')">
								<input type="radio" name="userSep" value="1" checked>일반
							</label>
							<label class="btn btn-primary" onclick="do_userSep('2')">
								<input type="radio" name="userSep" value="2">전문가
							</label>
						</div>
					</div>
					<br/>
				</td>
			</tr>
			<tr>
				<td style="width:80%;">
					<input class="form-control" id="u_id" name="u_id" type="text" placeholder="아이디(이메일) 입력"/>
				</td>
				<td style="width:20%;">
					<input class="btn btn-danger" style="width:100%;" type="button" value="인증하기" onclick="sendMail()"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<label id="lu_id" style="color: red;"></label>
					<br/>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<input class="form-control" id="u_password" name="u_password" type="password" placeholder="비밀번호 입력"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<label id="lu_password" style="color: red;"></label>
					<br/>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<input class="form-control" id="u_password2" name="u_password2" type="password" placeholder="비밀번호 확인"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<label id="lu_password2" style="color: red;"></label>
					<br/>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<input class="form-control" id="u_name" name="u_name" type="text" placeholder="닉네임(이름) 입력"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%;">
					<label id="lu_name" style="color: red;"></label>
					<br/>
				</td>
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
		</table>
		<input type="hidden" value="" id="u_level" name="u_level" />
		<input type="hidden" value="" id="u_naver" name="u_naver" />
		</form>
	</div>
	<div style="position:absolute; border-left: 1px solid #DFDFDF; padding-left: 50px; width:30%; height: auto; display: inline-block;">
            <div class="panel panel-default margin-top-0">
                <div class="panel-heading" align="center">
                    	이용약관 내용 고지
                </div>
                <ul class="list-group">
                    <li class="list-group-item" align="center">서비스 이용약관</li>
                </ul>
                <div class="panel-body signup-agreement-body">
                    <textarea style="width:100%; height:100px; resize:none;" readonly>
                        제1장 총칙
                        제1조 (목적)
                        이 약관은 주식회사 SelfNovel(이하 “회사”라고 합니다) 가 “SelfNovel” 인터넷 서비스 마켓플레이스 사이트와
                        스마트폰 등 이동통신기기를 통해 제공되는 SelfNovel” 모바일 애플리케이션(이하 “SelfNovel”이라고 합니다)을 통하여 제공하는
                        중개서비스 및 기타 정보서비스(이하 "서비스"라고 합니다)와 관련하여 회사와 회원간의 권리와
                        의무, 책임사항 및 회원의 서비스이용절차에 관한 사항을 규정함을 목적으로 합니다.
                    </textarea>
                </div>

                <ul class="list-group">
                    <li class="list-group-item" align="center">개인정보 수집·이용</li>
                </ul>
                <div class="panel-body signup-agreement-body">
                    <textarea style="width:100%; height:100px; resize:none;" readonly>
                        개인정보 수집·이용 동의
                        ① 목적 및 항목
                        회사는 다음과 같은 목적으로 개인정보를 수집하여 이용할 수 있습니다. 다만, 전자상거래 등에서의 소비자보호에 관한 법률, 국세기본법, 전자금융거래법 등 관련법령에 따라 주민등록번호 및 은행계좌번호의 수집• 보관이 불가피한 경우에는 이용자에게 고지하여 해당 정보를 수집할 수 있습니다.
                        1) 일반회원
                        - 이메일주소, 휴대폰번호: 회사가 제공하는 서비스의 이용에 따르는 본인확인 등
                        2) 구매회원
                        - 성명, 생년월일, 성별, 외국인등록번호, 이동전화번호, 아이디(e-mail), 비밀번호, 연계정보(CI), 중복가입정보(DI): 회사가 제공하는 서비스의 이용에 따르는 본인확인, 민원사항처리, 회원의 서비스 이용 통계 및 설문 등
                        - 회사명, 대표자명, 사업자등록번호, 업태, 종목, 전자세금계산서 발급용 이메일, 사업장 혹은 당당자 연락처: 사업자 회원 서비스 제공, 부가가치세법 제32조에 따른 세금계산서 등의 발행 등
                        - 이메일 주소, 사업장번호, 이동전화번호: 거래의 원활한 진행, 본인의사의 확인, 불만처리, 새로운 상품, 서비스 정보와 고지사항의 안내, 회원의 서비스 이용 통계 및 설문 등
                        - 수취인 성명, 주소, 전화번호: 서비스 또는 상품과 경품 배송을 위한 배송지 확인 등
                        - 은행계좌정보, 이동전화번호정보: 대금결제서비스의 제공 등
                    </textarea>
                </div>
            </div>
            <div class="col-xs-12">
                <div class="row">
                    <button id="signup_btn" class="btn btn-primary btn-lg btn-block" onclick="do_register()" >동의하고 가입완료</button>
                </div>
            </div>
	</div>
	
	<!-- 이메일 인증 모달 -->
	<div class="modal fade" id="emailAuth_modal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content panel-info %>" >
					<div class="modal-header panel-heading">
						<h4 class="modal-title">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			          		×
			        		</button>
							<b>이메일 인증</b>
						</h4>
					</div>
					<div class="modal-body" id="checkMessage">
						<table cellpadding="5" style="text-align: center; solid #dddddd">
						<tr>
							<td align="left" style="width: 110px;"><h5><b>인증 번호</b></h5></td>
							<td><input class="form-control" type="text" id="authNum" name="authNum" maxLength="20"></td>
							<td style="width: 110px;">
								<button class="btn btn-primary" type="button" onclick="do_auth()"><b>인증하기</b></button>
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