<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>신고하기</title>

<!-- JQuery CDN -->
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

<!-- BootStrap -->
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<!-- bootpag -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.bootpag.js"></script>

<!-- BootStrap CDN -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<script type="text/javascript">
	/**************************
	* Jquery ready            
	***************************/
	$(document).ready(function(){
		
		/**************************
		* 라디오버튼 변경시 이벤트
		***************************/
		$("input[name=msg_content]").on("change", function(){			
			var reportType = this.value;			
			if(reportType == "직접"){
				$("#msg_content_detail").removeAttr("disabled");
			}else{				
				$("#msg_content_detail").attr("disabled", "");
			}						
		});
		
		/**************************
		* 신고하기 버튼 클릭 이벤트
		***************************/
		$("#btnReport").on("click", function(){
			var msg_sender = $("#msg_sender").val();
			var msg_receiver = "admin";
			//var msg_content = $("msg_content").val();
			var msg_content = $("input:radio[name=msg_content]:checked").val();
			var rsm_id = $("#rsm_id").val();
			var msg_notify = $("#msg_notify").val();
			
			$.ajax({
				type : "POST",
				dataType : "html",
				url : "do_save.do",
				data : {
					"msg_sender" : msg_sender,
					"msg_receiver" : msg_receiver,
					"msg_content" : msg_content,
					"msg_sep" : "0",
					"msg_notify" : msg_notify,
					"rsm_id" : rsm_id
				},
				success : function(){
					alert("신고 완료!");
					window.close();
				}, error : function(){
					alert("신고 실패!");
				}
			});	        
		});
		
		/**************************
		* 닫기 버튼 클릭 이벤트
		***************************/
		$("#btnClose").on("click", function(){
			window.close();			
		});	
	});
</script>

<style type="text/css">
	textarea {
		width: 100%;
		resize: vertical;
		height: 150px;
	}
	.btn-label {position: relative;left: -12px;display: inline-block;padding: 6px 12px;background: rgba(0,0,0,0.15);border-radius: 3px 0 0 3px;}
	.btn-labeled {padding-top: 0;padding-bottom: 0;}	
</style>

</head>
<body>
	<!-- 전체 DIV -->
	<div style="width:100%; padding:20px;">	
		<h2>신고하기</h2>
		<hr/>			
		<form id="reportForm" name="reportForm">
			<input type="hidden" id="msg_receiver" value="${msg_receiver} "><!-- 받는사람 --> <!-- 대상 : 관리자 -->
			<input type="hidden" id="msg_sender" value="${msg_sender}"><!-- 작성자 ID -->				
			<input type="hidden" id="rsm_id" value="${rsm_id} "><!-- 글 아이디 --> <!-- 넘겨 받을 것 -->
			<input type="hidden" id="msg_notify" value="${msg_notify} "><!-- 신고대상 --> <!-- 넘겨 받을 것 -->
			<div id="row1" style="padding: 10px;">		
				<label>
					신고 대상 :
				</label>
				${rsm_title} - ${u_name}
			</div>			
			<div id="row2" style="padding: 10px;">
			<label>
				신고 사유 :
			</label>
			</div>			
			<div style="background-color: #FAFAFA; width:85%; height:100px; border: 1px solid #E6E6E6; padding: 10px;" align="center">
				<div>
					<label style="width:120px; float:left; text-align: left;">
						<input type="radio" name="msg_content" value="음란물">&nbsp;음란물
					</label>
					<label style="width:120px; float:left; text-align: left;">
						<input type="radio" name="msg_content" value="광고/상업성">&nbsp;광고/상업성
					</label>							
					<label style="width:120px; float:left; text-align: left;">
						<input type="radio" name="msg_content" value="개인정보유출">&nbsp;개인정보유출
					</label>
					<label style="width:120px; float:left; text-align: left;">
						<input type="radio" name="msg_content" value="욕설">&nbsp;욕설
					</label>
				</div>
			</div>
			<br>
			<div style="background-color: #FAFAFA; width:85%; height:100px; border: 1px solid #E6E6E6; padding: 10px;" align="center">
				<div>
					<label style="width:95%; float:left; text-align: left;">
						<input type="radio" name="msg_content" value="직접">&nbsp;신고사유 직접입력 (명예훼손 및 저작권 등)<br>						
						<input type="text" id="msg_content_detail" name="msg_content_detail" style="width: 100%;" disabled="disabled">
					</label>
				</div>						
			</div>

			<!-- 버튼영역 Div -->
			<br><br>
			<div align="center">
				<!-- 신고하기 버튼 -->
				<button type="button" class="btn btn-labeled btn-info" id="btnReport">
					<span class="btn-label" style="height:33px;">
						<i class="glyphicon glyphicon-flag"></i>
					</span>
					신고하기
				</button>
				<!-- 닫기 버튼 -->
				<button type="button" class="btn btn-default" id="btnClose">닫기</button>			
			</div>				
		</form>
	</div>
	<!-- //전체 DIV -->
</body>
</html>