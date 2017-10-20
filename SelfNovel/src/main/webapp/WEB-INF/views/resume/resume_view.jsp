<%@page import="com.sn.common.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<jsp:useBean id="toDay" class="java.util.Date" />

<%
  //contextPath
  String firstContextPath = request.getContextPath();
  String contextPath = "http://localhost:8080/"+firstContextPath;
%>

<%
	//jstl 개행문자 처리	
	pageContext.setAttribute("cn", "\n"); //Space, Enter
	pageContext.setAttribute("br", "<br/>"); //br 태그
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
		
	$(function() {
		
		var btn_show = "첨삭 보기" 
						+ "<br/>"
						+ "<i class='glyphicon glyphicon-menu-down'></i>";						
						
		var btn_hide = "첨삭 접기"
						+ "<br/>"
		  				+ "<i class='glyphicon glyphicon-menu-up'></i>";
		
		//첨삭부분의 히든속성을 토글시켜주는 메소드
		$("form[name=frm]").on("click", "#doShowEdit", function(){
			
			//버튼 모양 변경
			if($("#toggleFlag").val() == "false"){				
				$("#toggleFlag").val("true");
				$(this).html(btn_hide);
			}else{
				$("#toggleFlag").val("false");				
				$(this).html(btn_show);				
			}			
			
			var parent = $(this).parent();					//현재 선택된 Item
			var editDiv = $(parent).find(".editDiv");		//첨삭영역			
			var content = $(parent).find("#content");		//내용영역
			var btnArea = $(parent).find(".modBtnArea");
			var page = $(parent).find("#page-selection");	//Page
			var itm_prd_id = $(parent).parent().parent().find("#itm_form_id").val();
			var itm_childs = $(parent).parent().parent().find("#itm_childs").val();					
		
			
			$.ajax({				
				type : "POST",
				url : "do_search_child.do",
				dataType : "html",
				data : {"itm_prd_id" : itm_prd_id},
				success : function(data){					
					var item = JSON.parse(data);
					var item_table = draw_item_table(item[0]);
					
					content.empty();			//첨삭영역을 비우고
					content.append(item_table);	//첫 페이지의 Item으로 그린다.
					
					draw_item_edit_buttons(item[0], btnArea);// 항목의 수정/삭제 버튼을 그린다.
					
					//페이징
			        $(page).bootpag({
			            total: itm_childs
			        }).on("page", function(event, num){
			        	var item_tableN = draw_item_table(item[num-1]);			        	
			        	content.empty();			        	
			        	content.append(item_tableN);
			        	draw_item_edit_buttons(item[num-1]);
			        });
				},
				error : function(){
					
				}
			});
			
			editDiv.toggle();//토글
		});//close doShowEdit_click
		
		/**************************
		* '삭제하기' 버튼 클릭시 이벤트
		**************************/
		$("#btnDelete").on("click", function(){			
			if(confirm("정말 삭제하시겠습니까??") == true){				
				var rsm_id = $("#rsm_id").val();
				$.ajax({
					type : "POST",
					url : "do_delete.do",
					dataType : "html",
					data : {"rsm_id" : rsm_id},
					success : function(data){
						alert("삭제 성공!");						
						location.href="do_search.do";
					}
				});
			}				
		});//close btnDelete_on_click
		
		/**************************
		* '수정하기'버튼 클릭시 이벤트
		***************************/
		$("#btnModify").on("click", function(){			
			var frm = document.frm;
			frm.action = "modifyView.do";
			frm.submit();			
		});//close btnModify_on_click
		
		/**************************
		* 항목의 '수정'버튼 클릭시 이벤트
		***************************/
		$("form[name=frm]").on("click", "#btnModResume", function(){
			var selected = $(this).parent().parent().parent();	   //현재 선택된 항목
			var sel_title = selected.find("#itm_title").val();	   //선택된 항목의 제목
			var sel_content = selected.find("#itm_content_origin").val();//첨삭 선택된 항목의 내용			
			var sel_id = selected.find("#itm_form_id").val();	   //첨삭 선택된 항목의 id
			
			var sel_contentR = sel_content.trim();
			
			$("#modTitle").val(sel_title);
			$("#modContent").val(sel_content);			
			$("#modItmId").val(sel_id);
			$("#modModal").modal();
		});//close btnAddResume_click
		
		/**************************
		* 항목수정 모달창의 '수정완료'버튼 클릭시 이벤트 :
		* detail : do_updateOne
		***************************/
		$("#btnModSave").on("click", function(){
			var itm_form_id = $("#modItmId").val();
			var itm_title = $("#modTitle").val();
			var itm_content = $("#modContent").val();
			
			if(itm_title.length > 60){
				alert("제목의 길이가 너무 깁니다.(공백포함 최대 60글자)");
				return;
			}
			
			$.ajax({
				type : "POST",
				url : "do_updateOne.do",
				dataType : "html",
				data : {
					"itm_form_id" : itm_form_id,
					"itm_title" : itm_title,
					"itm_content" : itm_content
				},
				success : function(data){
					var flag = ($.trim(data));
					$(".modal").modal("hide");
					location.reload();	
				}
			});//close ajax
		});//close btnModSave
		
		/**************************
		* '첨삭'버튼 클릭시 이벤트
		***************************/
		$("form[name=frm]").on("click", "#btnAddResume", function(){
			var selected = $(this).parent().parent().parent();	   //현재 선택된 항목
			var sel_title = selected.find("#itm_title").val();	   //선택된 항목의 제목
			var sel_content = selected.find("#itm_content").html();//첨삭 선택된 항목의 내용
			var sel_id = selected.find("#itm_form_id").val();	   //첨삭 선택된 항목의 id
			
			var sel_contentR = sel_content.trim();//첨삭 선택된 항목의 내용
			
			sel_contentR = sel_contentR.replace(/<br>/mgi, '\r\n');
			
			$("#modalTitleOrigin").val(sel_title);
			$("#modalContentOrigin").val(sel_contentR);
			$("#modalTitleNew").val(sel_title);
			$("#modalContentNew").val(sel_contentR);
			$("#modalItmId").val(sel_id);
			$("#itmModal").modal();
		});//close btnAddResume_click		
		
		/**************************
		* '작성'버튼 클릭시 이벤트 :
		* detail : do_save
		***************************/
		$("#btnItmSave").on("click", function(){	

			//rsm_title 공백검사
			if($("#modalTitleNew").val().trim() == ""){
	    		alert("제목을 입력해주세요.");
	    		$("#modalTitleNew").focus();
	    		return;	    	
	    	}
			
			if($("#modalTitleNew").val() > 60){
				alert("제목의 길이가 너무 깁니다.(공백포함 최대 60글자)");
				return;
			}
			
			//rsm_title 공백검사
			if($("#modalContentNew").val().trim() == ""){
	    		alert("내용을 입력해주세요.");
	    		$("#modalContentNew").focus();
	    		return;	    	
	    	}
			
			
			var rsm_id = $("#rsm_id").val();
			var itm_title = $("#modalTitleNew").val();
			var itm_content = $("#modalContentNew").val();
			var itm_prd_id = $("#modalItmId").val();			
			var u_id = "${sessionScope.u_id }";
			
			$.ajax({
				type : "POST",
				url : "do_save_edit.do",
				dataType : "html",
				data : {
					"rsm_id" : rsm_id,
					"itm_prd_id" : itm_prd_id,
					"itm_title" : itm_title,
					"itm_content" : itm_content,
					"u_id" : u_id,
					"itm_seq" : "0"
					},
				success : function(data){
					var flag = ($.trim(data));
					$(".modal").modal("hide");
					location.reload();					
				},
				error : function(data){
					//에러
				}
			})//close ajax
		});//close btnItmSave_on_click
		
		/**************************
		* '목록'버튼 클릭시 이벤트
		***************************/
		$(document).on("click", "#btnBackToList", function(){
			location.href="do_search.do";
		});//close btnBacToList_on_click
		
		/*
		* '추천'버튼 클릭시 이벤트
		*/
		$(document).on("click", "#btnRecommend", function(){
			
			var rsm_id = $("#rsm_id").val();
			
			$.ajax({
				type : "post",
				url : "do_updateRecommend.do",
				dataType : "html",
				data : {
					"rsm_id" : rsm_id	
				},
				success : function(data){
					if(data == 404){
						alert("이미 추천한 게시물입니다.");
						return;
					}
					location.reload();
				}
			});
		});//close btnRecommend_on_click
		
		
		
		/**************************
		* '신고하기'버튼 클릭시 이벤트
		***************************/
		$("#btnReport").on("click", function(){			
			
			var pop_title = "popup_window"
			var frm = document.frm;
			frm.setAttribute("current_id", "current_id", "${sessionScope.u_id}")	
						
			window.open("", pop_title, "width=500, height=600, scrollbars=no");		
	        
	        frm.target = pop_title;
	        frm.action = "../message/reportForm.do";	         
	        frm.submit() ;
	        
		});//close btn_report_on_click
		
		/**************************
		* '자소서Down'버튼 클릭시 이벤트		
		***************************/
		$("#btnResumeDown").on("click", function(){
			var frm = document.doExcelDown;
			frm.submit();			
		});
		
		/*****************************
		* '첨삭항목 수정' 버튼 클릭시 이벤트		
		*****************************/
		$(".modBtnArea").on("click", "#btnEditItem",function(){
			console.log(1);
			
			var itm_form_id = $("#eItm_form_id").val();
			var itm_title = $("#eItm_title").val();			
			var itm_content = $("#eItm_content").html();
			itm_content = itm_content.replace(/<br>/mgi, '\r\n');			
			
			$("#eModTitle").val(itm_title);
			$("#eModContent").val(itm_content);
			$("#eModItmId").val(itm_form_id);
			
			$("#eModModal").modal();
		});
		
		/*****************************
		* '첨삭항목 수정'모달의 '수정완료' 버튼 클릭시 이벤트		
		*****************************/
		$("#btnEModSave").on("click", function(){
			var itm_form_id = $("#eModItmId").val();
			var itm_title = $("#eModTitle").val();
			var itm_content = $("#eModContent").val();
			
			if(itm_title.length > 60){
				alert("제목의 길이가 너무 깁니다.(공백포함 최대 60글자)");
				return;
			}
			
			$.ajax({
				type : "POST",
				url : "do_updateOne.do",
				dataType : "html",
				data : {
					"itm_form_id" : itm_form_id,
					"itm_title" : itm_title,
					"itm_content" : itm_content
				},
				success : function(data){
					var flag = ($.trim(data));
					$(".modal").modal("hide");
					location.reload();	
				}
			});//close ajax
		})//close btnEditItem
		
		/*****************************
		* '첨삭항목 삭제' 버튼 클릭시 이벤트		
		*****************************/
		$(".modBtnArea").on("click", "#btnDeleteItem",function(){
			if(confirm("정말로 삭제하시겠습니까?") == true){
				var itm_form_id = $("#eItm_form_id").val();
				
				$.ajax({
					type : "POST",
					url : "do_delete_item.do",
					dataType : "html",
					data : {
						"itm_form_id" : itm_form_id,					
					},
					success : function(data){
						var flag = ($.trim(data));
						alert(flag + "삭제 성공");
						$(".modal").modal("hide");
						location.reload();	
					}
				});//close ajax				
			}//close if
		});//close btnDelteItem
		
	});//close .ready(function)
	
	/**************************
	* 첨삭영역의 수정/삭제 버튼을 그리는 함수
	***************************/
	function draw_item_edit_buttons(item, btnArea){
		
		var item_u_id = item.u_id;
		var session_id = '<%=session.getAttribute("u_id")%>';
		
		var buttons =" 	<button type='button' class='btn btn-labeled btn-warning' id='btnEditItem'> "
		+ " 		<span class='btn-label' style='height: 35px;'>                          "
		+ " 			<i class='glyphicon glyphicon-edit'></i>                            "
		+ " 		</span>                                                                 "
		+ " 		첨삭수정													                "
		+ " 	</button>                                                                   "
		+ " 	&nbsp;                                                                      "
		+ " 	<button type='button' class='btn btn-labeled btn-danger' id='btnDeleteItem'>"
		+ " 		<span class='btn-label' style='height: 35px;'>                          "
		+ " 			<i class='glyphicon glyphicon-trash'></i>                           "
		+ " 		</span>                                                                 "
		+ " 		첨삭삭제														            "
		+ " 	</button>														            "			
		
		//세션 u_id와 아이템의 작성자가 일치하면 메뉴 드로우
		if(session_id != null && session_id == item_u_id){
			btnArea.empty();
			btnArea.append(buttons);				
		}	
		
		return buttons;
	}
	
	/**************************
	* 글자 수를 구하는 함수
	***************************/	
	function getStrLength(str){		
        var strReplace = str.replace(/<br>/mgi, ' ');
        var counter = strReplace.length;
        
        return counter;
	}
	
	/**************************
	* 글자용량을 구하는 함수
	***************************/
	function getStrBytes(str){
		var strReplace = str.replace(/<br>/mgi, ' ');
	    var l= 0;
	     
	    for(var idx=0; idx < strReplace.length; idx++) {
	        var c = escape(strReplace.charAt(idx));
	         
	        if( c.length==1 ) l ++;
	        else if( c.indexOf("%u")!=-1 ) l += 2;
	        else if( c.indexOf("%")!=-1 ) l += c.length/3;
	    }	     
	    return l;	
	}
	
	/**************************
	* 첨삭영역의 테이블을 그리는 함수
	***************************/
	function draw_item_table(item) {
		
		//줄바꿈
		var item_content = item.itm_content.replace(/(\n|\r\n)/g, '<br>');		
		//공백 제거
		var item_replaced = item.itm_content.replace(/(\n| )/mgi, '');
		
		var u_name;
		
		if(item.u_name == undefined){
			u_name = "탈퇴한 회원"; 
		}else{
			u_name = item.u_name;
		}
		
		var item_table = "<table class='table table-bordered table-condensed' border='1px'" 
		   	+ "cellpadding='2' cellspacing='2' align='center' width='550px;'>"
		   	+ "<tr>"		   	 
		   	+ "<td style='text-align: center; background-color: D6E6F5; width:15%;'>"
		   	+ "<label>"
		   	+ "작성자"
		   	+ "</label>"
		   	+ "</td>"
		   	+ "<td style='width:50%;'>"		   	
		   	+ u_name		   	
		   	+ "</td>"
		   	+ "<td style='text-align: center; background-color: D6E6F5; width:15%;'>"
		   	+ "<label>"
		   	+ "작성일"
		   	+ "</label>"
		   	+ "</td>"
		   	+ "<td style='width:30%'>"		   	
		   	+ item.itm_reg_dt		   			   	
		   	+ "</td>"
		   	+ "</tr>"
			+ "<tr>"
			+ "<td colspan='5' >"
			+ "<h4><b>"
			+ "RE:" + item.itm_title
			+ "</b></h4>"
			+ "<input type='hidden' id='eU_id' value=" + item.u_id + ">"
			+ "<input type='hidden' id='eItm_title' value=" + item.itm_title + ">"
			+ "<input type='hidden' id='eItm_content_origin' value=" + item.itm_content + ">"
			+ "<input type='hidden' id='eItm_form_id' value=" + item.itm_form_id + ">"
			+ "<input type='hidden' id='eItm_prd_id' value=" + item.itm_prd_id + ">"			
			+ "</td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td colspan='5'>"
			+ "<div id='eItm_content' style='background-color: #FAFAFA; border: 1px solid #E6E6E6;'>"
			+ item_content
		    + "</div>"			
			+ "</td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td colspan='5'>"
			+ "<div style='float:right;'>"
			+ "공백포함 : "
			+ "<b>" +getStrLength(item_content)+ "</b>"+"&nbsp;자&nbsp;"
			+ "("+ getStrBytes(item_content) + "&nbsp;Byte)"			
			+ "&nbsp;공백제외 : "
			+ "<b>" +getStrLength(item_replaced.replace())+ "</b>"+"&nbsp;자&nbsp;"
			+ "("+ getStrBytes(item_replaced) + "&nbsp;Byte)"
			+ "</div>"
			+ "</td>"
			+ "</tr>"
		+"</table>"	
		return item_table;
	}	
	
</script>
<title>Insert title here</title>
<style type="text/css">
	textarea {
		width : 100%;
		resize: vertical;
		height: 200px;
	}
	img, input {
	   vertical-align:middle;  
	}
	
	.btn-recommend{
		padding-top: 0;
		padding-bottom: 0;
		height: 35px;		
	}
	
	.btn-label {position: relative;left: -12px;display: inline-block;padding: 6px 12px;background: rgba(0,0,0,0.15);border-radius: 3px 0 0 3px;}
	.btn-labeled {padding-top: 0;padding-bottom: 0;}	
	
</style>

</head>
<body>
	<!-- for excel download -->
	<!-- 2017-10-11 autor: lsg -->
	<form name="doExcelDown" action="do_excelDown.do" method="post">
		<input type="hidden" name="excel_rsm_id" value="${rsmVO.rsm_id}"><!-- 자소서 id -->
		<input type="hidden" name="u_name" value="${rsmVO.u_name}"><!-- 작성자 id -->
	</form>
	<!-- for excel download end -->

	<h2>자기소개서 view</h2>
	<hr/>	
	<form action="#" name="frm" method="post" class="form-inline">		
		<input type="hidden" id="rsm_id" name="rsm_id" value="${rsmVO.rsm_id}"><!-- 자소서 id -->
		<input type="hidden" id="rsm_u_id" name="rsm_u_id" value="${rsmVO.u_id }"> <!-- 작성자 id -->
		<input type="hidden" id="u_name" name="u_name" value="${rsmVO.u_name}" ><!-- 작성자 닉네임 -->
		<input type="hidden" id="rsm_title" name="rsm_title" value="${rsmVO.rsm_title}"><!-- 자소서 제목 -->
		
		<!-- 버튼 div -->
		<div style="float:right;">
		
		<!-- 세션에 아이디가 존재하고, 세션의 유저아이디와 작성자 아이디가 일치하는 경우에만 수정하기 버튼을 보여준다. -->
		<c:if test="${sessionScope.u_id ne null && sessionScope.u_id eq rsmVO.u_id}">
			<button type="button" id="btnDelete" value="삭제하기 " class="btn btn-labeled btn-danger" >			
				<span class="btn-label" style="height: 34px;">  
					<i class="glyphicon glyphicon-erase" ></i>																											
				</span>
				삭제			
			</button>						
			&nbsp;
			<button type="button" id="btnModify" value="수정하기" class="btn btn-labeled btn-warning">
				<span class="btn-label" style="height: 34px;">
					<i class="glyphicon glyphicon-edit"></i>																																
				</span>
				수정
			</button>
			&nbsp;			
		</c:if>										
		<!-- 세션아이디가 존재하고, 세션의 유저아이디와 작성자 아이디가 다른 경우에만 신고하기 버튼을 보여준다. -->
		<c:if test="${sessionScope.u_id ne null && sessionScope.u_id ne rsmVO.u_id}">
			<button type="button" id="btnReport" value="신고하기" class="btn btn-labeled btn-danger">
				<span class="btn-label" style="height: 34px;">
					<i class="glyphicon glyphicon-flag"></i>			
				</span>
				신고
			</button>
			&nbsp;			
		</c:if>			
		<button type="button" id="btnBackToList" value="목록으로" class="btn btn-labeled btn-success">
			<span class="btn-label" style="height: 34px;">
				<i class="glyphicon glyphicon-list"></i>				
			</span>			
			목록
		</button>				
		<br>
		<br/>				
		<br>
		</div>
		<!-- //버튼 div -->
		<table class="table table-bordered table-condensed" border="1px" align="center">
			<tr>
				<td style="text-align: center; background-color: D6E6F5;" width="15%">
					<label>제목</label>
				</td>
				<td width="50%">					
					${rsmVO.rsm_title}					
				</td>
				<td style="text-align: center; background-color: D6E6F5;" width="15%">					
					<label class="table_item">
						작성일
					</label>
				</td>
				<td width="30%" align="center">
					<c:set var="temp" value="${rsmVO.rsm_reg_dt }" />
					<fmt:parseDate value="${temp}" pattern="yyyy-MM-dd HH:mm:SS" var="reg_dt" />
					<fmt:formatDate value="${reg_dt }" pattern="HH:mm:SS" var="f_reg_tm" />
					<fmt:formatDate value="${reg_dt }" pattern="yyyy-MM-dd" var="f_reg_dt" />
					<fmt:formatDate value="${toDay}" pattern="yyyy-MM-dd" var="now"/>
					<!-- 작성일이 오늘이면 시간만 표시, 그 외는 날짜만 표시 -->								
					<c:choose>										
						<c:when test="${f_reg_dt eq now}">											
							<c:out value="${f_reg_tm }"></c:out>
						</c:when>
						<c:otherwise>
							<c:out value="${f_reg_dt}"/>
						</c:otherwise>
					</c:choose>					
				</td>
			</tr>
			<tr>
				<td style="text-align: center; background-color: D6E6F5;">
					<label>작성자</label>
				</td>
				<td colspan="3">
					${rsmVO.u_name}			
				</td>				
			</tr>
			<tr>
				<td style="text-align: center; background-color: D6E6F5;">
					<label>
						내용
					</label>
				</td>
				<td colspan="3">				
					${rsmVO.rsm_content }									 
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<br/>
					<div align="right">
						<button type="button" id="btnResumeDown" value="자기소개서 Down" class="btn btn-labeled btn-default">
							<span class="btn-label" style="height: 34px;">
								<i class="glyphicon glyphicon-download" style="size: 350"></i>
							</span>							
							자기소개서 Down
						</button>
					</div>
					<br/>
					<div id="items" align="center">
					<!-- **************************** forEach 시작부분 **************************** -->					
					<c:forEach var="item" items="${itmList}">
						<c:choose>							
							<c:when test="${item.itm_prd_id eq null}">
								<!-- 본문영역 -->
								<hr/>						
								<table class="table table-condensed" style="border: hidden;" 
											cellpadding="2" cellspacing="2" align="center" width="550px;">									
									<tr>
										<td>
											<h4><b>${item.itm_title }</b></h4>											
											<input type="hidden" id="itm_title" value="${item.itm_title}">
											<input type="hidden" id="itm_form_id" value="${item.itm_form_id}"><!-- 항목 아이디 -->
											<input type="hidden" id="itm_childs" value="${item.totalNo}"><!-- 하위노드 개수 -->
										</td>
										
									</tr>
									<tr>
										<td style="border: hidden;">
											<input type="hidden" id="itm_content_origin" value="${item.itm_content }">											
											<div id="itm_content" style="background-color: #FAFAFA; border: 1px solid #E6E6E6;">
												<c:set value="${item.itm_content}" var="content"></c:set>												
												${fn:replace(content, cn, br)}																											
											</div>											
										</td>
									</tr>
									<tr>
										<td style="border: hidden;" align="right">
											<c:set var = "str_origin" value="${item.itm_content }" />
											<c:set var = "str_trim" value = "${fn:trim(str_origin)}" />
											<c:set var = "str_replace" value ="${fn:replace(str_trim, ' ', '')}"/>
											<c:set var = "str_replace" value ="${fn:replace(str_replace, cn, '')}"/>
											<c:set var = "str_replace" value ="${fn:replace(str_replace, cncr, '')}"/>
											공백포함 : <b>${fn:length(str_trim) } </b>자 (${fn:length(str_trim.bytes) } Byte)
											공백제외 : <b>${fn:length(str_replace) } </b>자 (${fn:length(str_replace.bytes) } Byte)											
										</td>
									</tr>
									<tr>																					
										<!-- 1. 글의 작성자와 세션아이디가 일치하면 수정하기 버튼을 보여준다. -->
										<c:if test="${sessionScope.u_id ne null && sessionScope.u_id eq item.u_id}">
											<td style="float: right; border:hidden;">												
												<button type="button" id="btnModResume" value="수정하기" class="btn btn-labeled btn-default">
													<span class="btn-label" style="height: 34px;">
														<i class="glyphicon glyphicon-edit"></i>																																
													</span>
													수정하기
												</button>
											</td>
										</c:if>
										<!-- 2. 글의 작성자와 세션아이디가 다르면 첨삭하기 버튼을 보여준다. -->
										<c:if test="${sessionScope.u_id ne null && sessionScope.u_id ne item.u_id}">										
											<td style="float: right; border:hidden;">	
												<button type="button" id="btnAddResume" class="btn btn-labeled btn-default">
													<span class="btn-label" style="height: 34px;">
														<i class="glyphicon glyphicon-edit"></i>
													</span>
													첨삭하기
												</button> 
												<!-- <input type="button" value="첨삭하기" id="btnAddResume" class="btn btn-default"> -->
											</td>
										</c:if>										
									</tr>
									<c:if test="${item.totalNo ne '0'}">
										<!-- <img src="" width="60px" height="50px" name="doShowEdit" id="doShowEdit">-->
										<tr>											 
											<td align="center" style="border:hidden;">
												<input type="hidden" id="toggleFlag" value="false">
												<button type="button" name="doShowEdit" id="doShowEdit" class="btn btn-info">
													첨삭보기
													<br>													
													<i class="glyphicon glyphicon-menu-down"></i>
												</button>													
												<!-- 임의로 버튼으로 구현을 시도한다. 나중에 이미지 변경시 같은 이름으로 만들어주거나, 아니면 버튼을 이미지로 만들어도 좋다 -->
												<!-- 2017-09-26 pinkbean -->																		
												<!-- 숨겨지는 부분이다. 버튼을 클릭하면 토글된다. -->
												<div id="editDiv" style="display:none;" class="editDiv">
													<div id="content"></div>
													<div class="modBtnArea" style="display: block; text-align: right;"></div>
													<div id="page-selection" style="display: block;"></div>																																							
												</div>																								
											<!-- end_editDiv -->
											</td>
										</tr>								
									</c:if>
								</table>
								<!-- end 본문영역 -->								
							</c:when>
						</c:choose>						
					</c:forEach>
					<!-- **************************** end forEach **************************** -->					
					</div>					
				</td>
			</tr>
		</table>
			
		<!-- **************************** ppt 미리보기 부분 ****************************-->
		<c:if test="${imgList ne null}">
			<div align="center">
	
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
				  <!-- Indicators -->
				  <ol class="carousel-indicators">
				    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
				    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
				    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
				  </ol>
				    
				  <div class="carousel-inner" role="listbox">
				    <!-- images -->
				    <c:forEach var="imgVO" items="${imgList}" varStatus="status">
				    	<c:choose>
				    		<c:when test="${status.count eq 1}">
				    		<!-- 1일 경우에만 item active가 되도록. 하나는 반드시 이걸 가지고 있어야 하기 때문 -->
							    <div class="item active">
							      <img alt="" src="<%=firstContextPath%>/resources/${imgVO.img_path}/${imgVO.img_sv_nm}">
							      <div class="carousel-caption">
							        ...
							      </div>
							    </div>					    		
				    		</c:when>
				    		<c:otherwise>
							    <div class="item">
							      <img alt="" src="<%=firstContextPath%>/resources/${imgVO.img_path}/${imgVO.img_sv_nm}">
							      <div class="carousel-caption">
							        ...
							      </div>
							    </div>					    		
				    		</c:otherwise>
				    	</c:choose>
					</c:forEach>
				</div>
				
				  <!-- Controls -->
				  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
				    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				    <span class="sr-only">Previous</span>
				  </a>
				  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
				    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				    <span class="sr-only">Next</span>
				  </a>
				</div>
			</div>
			<br/><br/>
		</c:if>	
		<!-- **************************** ppt 미리보기 부분 end**************************** -->
		<div>
			<div align="center">				
				<button type="button" id="btnRecommend" class="btn btn-labeled btn-primary btn-recommend" >								
                	<span class="btn-label">
                		<i class="glyphicon glyphicon-thumbs-up"></i>
                	</span>
                	${rsmVO.rsm_recommend }
                </button>				
			</div>			
			<div align="right">
				<button type="button" id="btnBackToList" value="목록으로" class="btn btn-labeled btn-success">
					<span class="btn-label" style="height: 34px;">
						<i class="glyphicon glyphicon-list"></i>				
					</span>			
					목록
				</button>
			</div>
		</div>
	</form>	
	
	<!-- 수정하기 Modal Window -->
	<div id="modModal" class="modal fade" role="dialog">
		<form name="modModalFrm">
		<!-- modal-dialog -->
  		<div class="modal-dialog modal-lg">
    		<!-- Modal content-->    		
	    	<div class="modal-content">
	    		<!-- modal-header -->
				<div class="modal-header">
	        		<!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
	        		<h3 class="modal-title">수정하기</h3>
	      		</div>
	      		<!-- //modal-header -->
	      		<!-- modal-body -->
				<div class="modal-body">
					<input type="hidden" id="modItmId">					
					<table class="table table-bordered table-condensed" border="1px" 
					   				   			cellpadding="2" cellspacing="2" align="center" width="100%">
						<tr>
							<td>
								<input id="modTitle" type="text" style="width:100%; border: 0px;">
							</td>					
						</tr>
						<tr>
							<td>
								<textarea id="modContent" style="width:100%; border: 0px; height: 300px;"></textarea>
							</td>
						</tr>
					</table>	
			    </div>
			    <!-- //modal-body -->
				<div class="modal-footer">				
					<button id="btnModSave" type="button" class="btn btn-labeled btn-success">
						<span class="btn-label" style="height:35px;">
							<i class="glyphicon glyphicon-ok"></i>
						</span>
						수정완료
					</button>
					<button id="btnModClose" type="button" class="btn btn-labeled btn-danger" data-dismiss="modal">
						<span class="btn-label" style="height:35px;">
							<i class="glyphicon glyphicon-remove"></i>
						</span>						
						닫기
					</button>					
				</div>
				<!-- //modal-footer -->	
			</div>	
			<!-- //Modal content-->		
		</div>
		<!-- //Modal dialog -->
		</form>
	</div>	
	<!-- //Modal -->
	
	<!-- 첨삭하기 Modal Window -->
	<div id="itmModal" class="modal fade" role="dialog">
		<form name="itmModalFrm">
		<!-- modal-dialog -->
  		<div class="modal-dialog">
    		<!-- Modal content-->    		
	    	<div class="modal-content">
	    		<!-- modal-header -->
				<div class="modal-header">
	        		<!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
	        		<h4 class="modal-title">첨삭하기</h4>
	      		</div>
	      		<!-- //modal-header -->
	      		<!-- modal-body -->
				<div class="modal-body">
					<input type="hidden" id="modalItmId">
					<h3>원본</h3>
					<table class="table table-bordered table-condensed" border="1px" 
					   				   			cellpadding="2" cellspacing="2" align="center" width="100%">
						<tr>
							<td>
								<input id="modalTitleOrigin" type="text" style="width:100%; border: 0px;" readonly>
							</td>					
						</tr>
						<tr>
							<td>
								<textarea id="modalContentOrigin" style="width:100%; border: 0px;" readonly></textarea>
							</td>
						</tr>
					</table>					
					<br>		
					<h3>첨삭하기</h3>							
					<table class="table table-bordered table-hover table-condensed" border="1px" 
					   				   			cellpadding="2" cellspacing="2" align="center" width="100%">
						<tr>
							<td>
								<input id="modalTitleNew" type="text" style="width:100%; border: 0px;">
							</td>					
						</tr>
						<tr>
							<td>
								<textarea id="modalContentNew" style="width:100%; border: 0px;"></textarea>
							</td>
						</tr>
					</table>									
					<br>					
			    </div>
			    <!-- //modal-body -->
				<div class="modal-footer">												
					<button id="btnItmSave" type="button" class="btn btn-labeled btn-default">
						<span class="btn-label" style="height:35px;">
							<i class="glyphicon glyphicon-ok"></i>
						</span>
						작성완료
					</button>
					<button type="button" class="btn btn-labeled btn-default" data-dismiss="modal">
						<span class="btn-label" style="height:35px;">
							<i class="glyphicon glyphicon-remove"></i>
						</span>
						작성취소						
					</button>
				</div>
				<!-- //modal-footer -->	
			</div>	
			<!-- //Modal content-->		
		</div>
		<!-- //Modal dialog -->
		</form>
	</div>	
	<!-- //Modal -->
	
	<!-- 첨삭 수정하기 Modal Window -->
	<div id="eModModal" class="modal fade" role="dialog">
		<form name="eModModalFrm">
		<!-- modal-dialog -->
  		<div class="modal-dialog modal-lg">
    		<!-- Modal content-->    		
	    	<div class="modal-content">
	    		<!-- modal-header -->
				<div class="modal-header">
	        		<!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
	        		<h3 class="modal-title">첨삭 수정</h3>
	      		</div>
	      		<!-- //modal-header -->
	      		<!-- modal-body -->
				<div class="modal-body">
					<input type="hidden" id="eModItmId">					
					<table class="table table-bordered table-condensed" border="1px" 
					   				   			cellpadding="2" cellspacing="2" align="center" width="100%">
						<tr>
							<td>
								<input id="eModTitle" type="text" style="width:100%; border: 0px;">
							</td>					
						</tr>
						<tr>
							<td>
								<textarea id="eModContent" style="width:100%; border: 0px; height: 300px;"></textarea>
							</td>
						</tr>
					</table>	
			    </div>
			    <!-- //modal-body -->
				<div class="modal-footer">
					<button id="btnEModSave" type="button" class="btn btn-labeled btn-success">
						<span class="btn-label" style="height:35px;">
							<i class="glyphicon glyphicon-ok"></i>
						</span>
						수정완료
					</button>
					<button id="btnEModClose" type="button" class="btn btn-labeled btn-danger" data-dismiss="modal">
						<span class="btn-label" style="height:35px;">
							<i class="glyphicon glyphicon-remove"></i>
						</span>						
						닫기
					</button>
				</div>
				<!-- //modal-footer -->	
			</div>	
			<!-- //Modal content-->		
		</div>
		<!-- //Modal dialog -->
		</form>
	</div>	
	<!-- //Modal -->

</body>
</html>