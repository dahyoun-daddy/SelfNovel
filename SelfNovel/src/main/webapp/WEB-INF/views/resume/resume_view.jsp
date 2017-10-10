<%@page import="com.sn.common.StringUtil"%>
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

<script src="<%=contextPath%>/resources/js/jquery.bootpag.js"></script>

<script type="text/javascript">
		
	$(function() {
		//첨삭부분의 히든속성을 토글시켜주는 메소드
		$("form[name=frm]").on("click", "#doShowEdit", function(){
			
			//버튼 모양 변경
			if($(this).val() == "첨삭보기"){
				$(this).val("첨삭접기");
			}else if($(this).val() == "첨삭접기"){
				$(this).val("첨삭보기");
			}
			
			var parent = $(this).parent();					//현재 선택된 Item
			var editDiv = $(parent).find(".editDiv");		//첨삭영역
			var content = $(parent).find("#content");		//내용영역
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
					
					//페이징
			        $(page).bootpag({
			            total: itm_childs
			        }).on("page", function(event, num){
			        	var item_tableN = draw_item_table(item[num-1]);			        	
			        	content.empty();			        	
			        	content.append(item_tableN);
			        });
				},
				error : function(){
					
				}
			});
			
			editDiv.toggle();//토글
		});//close doShowEdit_click
		
		/**************************
		* '수정하기'버튼 클릭시 이벤트
		***************************/
		$("#btnModify").on("click", function(){
			location.href="modifyView.do";
		});//close btnModify_on_click
		
		/**************************
		* 항목의 '수정'버튼 클릭시 이벤트
		***************************/
		$("form[name=frm]").on("click", "#btnModResume", function(){
			var selected = $(this).parent().parent().parent();	   //현재 선택된 항목
			var sel_title = selected.find("#itm_title").val();	   //선택된 항목의 제목
			var sel_content = selected.find("#itm_content").html();//첨삭 선택된 항목의 내용
			var sel_id = selected.find("#itm_form_id").val();	   //첨삭 선택된 항목의 id
			
			$("#modTitle").val(sel_title);
			$("#modContent").val(sel_content);			
			$("#modItmId").val(sel_id);
			$("#modModal").modal();
		});//close btnAddResume_click
		
		/**************************
		* 항목수정 모달창의 '수정완료'버튼 클릭시 이벤트 :
		* detail : do_update_item
		***************************/
		$("#btnModSave").on("click", function(){
			var itm_form_id = $("#modItmId").val();
			var itm_title = $("#modTitle").val();
			var itm_content = $("#modContent").val();
			
			$.ajax({
				type : "POST",
				url : "do_update_item.do",
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
			
			$("#modalTitleOrigin").val(sel_title);
			$("#modalContentOrigin").val(sel_content);
			$("#modalTitleNew").val(sel_title);
			$("#modalContentNew").val(sel_content);
			$("#modalItmId").val(sel_id);
			$("#itmModal").modal();
		});//close btnAddResume_click		
		
		/**************************
		* '작성'버튼 클릭시 이벤트 :
		* detail : do_save
		***************************/
		$("#btnItmSave").on("click", function(){			
			var rsm_id = $("#rsm_id").val();
			var itm_title = $("#modalTitleNew").val();
			var itm_content = $("#modalContentNew").val();
			var itm_prd_id = $("#modalItmId").val();
			var u_id = $("#u_id").val();//TODO : 세션에서 작성자 아이디 받아오도록 수정할 것
			
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
		$("#btnBackToList").on("click", function(){
			location.href="do_search.do";						
		});//close btnBacToList_on_click
		
		/**************************
		* '신고'버튼 클릭시 이벤트
		* detail : TODO
		***************************/
		$("#btnReport").on("click", function(){
									
		});//close btn_report_on_click
		
		/**************************
		* '자소서Down'버튼 클릭시 이벤트
		* detail : TODO
		***************************/
		$("#btnResumeDown").on("click", function(){
			
		});
		
		/**************************
		* 'pptDown'버튼 클릭시 이벤트
		* detail : TODO
		***************************/
		$("#btnPptDown").on("click", function(){
			
		});
		
	});//close .ready(function)
	
	/**************************
	* 첨삭영역의 테이블을 그리는 함수
	***************************/
	function draw_item_table(item) {
		var item_table = "<table class='table table-bordered table-hover table-condensed' border='1px'" 
		   	+ "cellpadding='2' cellspacing='2' align='center' width='550px;'>"
		   	+ "<tr>"
		   	+ "<td>작성자</td>"
		   	+ "<td><input type='text' id='u_id' value=" + item.u_id + "></td>"
		   	+ "<td>작성일</td>"
		   	+ "<td><input type='text' id='item_reg_dt' value=" + item.itm_reg_dt + "></td>"
		   	+ "</tr>"
			+ "<tr>"
			+ "<td colspan='4'>"
			+ "<input type='text' id='itm_title' value=" + item.itm_title + ">"
			+ "<input type='hidden' id='itm_form_id' value=" + item.itm_form_id + ">"
			+ "<input type='hidden' id='itm_prd_id' value=" + item.itm_prd_id + ">"
			+ "</td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td colspan='4'>"
			+ "<textarea rows='5' cols='80' style='border: 0px;'>" + item.itm_content + "</textarea>"
			+ "</td>"
			+ "</tr>"
		+"</table>"	
		return item_table;
	}
	
</script>
<title>Insert title here</title>
</head>
<body>
	<h2>자기소개서 view</h2>
	<hr/>
	<div id="good"></div>
	<form action="#" name="frm" method="post" class="form-inline">
		<input type="hidden" id="rsm_id" value="${rsmVO.rsm_id}"><!-- 자소서 id -->
		<table class="table table-bordered table-hover table-condensed" border="1px" align="center" width="600px;">
			<tr>				
				<td align="right" colspan="3">					
					<!-- TODO : 세션아이디와 동일한 경우만 수정가능하도록 -->
					<c:if test="${rsmVO.u_id ne '세션아이디'}">
						<input type="button" id="btnModify" value="수정하기" class="btn btn-default">
					</c:if>										
					<input type="button" id="btnReport" value="신고하기" class="btn btn-default">
					<input type="button" id="btnBackToList" value="목록으로" class="btn btn-default">
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" width="15%">제목</td>
				<td width="70%">
					<!-- <input type="text" value="제목,분야" style="border: 0px;">-->
					<input type="text" value="${rsmVO.rsm_title}" style="border: 0px;">
				</td>
				<td align="right" width="15%">					
					<input type="text" style="text-align: right; border: 0px;" value="${rsmVO.rsm_reg_dt}">
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">작성자</td>
				<td colspan="2"><input type="text" id="u_name" style="border: 0px;" value="${rsmVO.u_name}" ></td>				
			</tr>
			<tr>
				<td style="text-align: center;">내용</td>
				<td colspan="2"><textarea rows="2" cols="50" style="border: 0px;">${rsmVO.rsm_content }</textarea> </td>
			</tr>
			<tr>
				<td colspan="3">
					<br/>
					<div align="right">
						<input type="button" id="btnResumeDown" value="자기소개서 Down" class="btn btn-default">
						<input type="button" id="btnPptDown" value="PPT Down" class="btn btn-default">
					</div>
					<br/>
					<div id="items" align="center">
					<!-- **************************** forEach 시작부분 **************************** -->					
					<c:forEach var="item" items="${itmList}">
						<c:choose>							
							<c:when test="${item.itm_prd_id eq null}">
								<!-- 본문영역 -->						
								<table class="table table-bordered table-hover table-condensed" border="1px" 
											cellpadding="2" cellspacing="2" align="center" width="550px;">									
									<tr>
										<td>
											<input id="itm_title" type="text" value="${item.itm_title}" style="border: 0px;">
											<input type="hidden" id="itm_form_id" value="${item.itm_form_id}"><!-- 항목 아이디 -->
											<input type="hidden" id="itm_childs" value="${item.totalNo}"><!-- 하위노드 개수 -->
										</td>
										
									</tr>
									<tr>
										<td><textarea id="itm_content" rows="5" cols="80" style="border: 0px;">${item.itm_content}</textarea></td>
									</tr>
									<tr>
										<td style="float: right;">
											<c:choose>											
												<c:when test="${item.u_id ne '세션아이디'}">
												<!-- TODO : 세션값의 ID에 따라 버튼 다르게 처리할 것 -->
												<!-- 세션값의 아이디와 글의 아이디가 같은 경우 수정버튼 -->												
													<input type="button" value="수정하기" id="btnModResume" class="btn btn-default">
												</c:when>															
												<c:otherwise>
													<input type="button" value="첨삭하기" id="btnAddResume" class="btn btn-default">
												</c:otherwise>					
											</c:choose>
										</td>
									</tr>
									<c:if test="${item.totalNo ne '0'}">
										<!-- <img src="" width="60px" height="50px" name="doShowEdit" id="doShowEdit">-->
										<tr>
											<td>
												<input type="button" name="doShowEdit" id="doShowEdit" value="첨삭보기">
												<!-- 임의로 버튼으로 구현을 시도한다. 나중에 이미지 변경시 같은 이름으로 만들어주거나, 아니면 버튼을 이미지로 만들어도 좋다 -->
												<!-- 2017-09-26 pinkbean -->																		
												<!-- 숨겨지는 부분이다. 버튼을 클릭하면 토글된다. -->
												<div id="editDiv" style="display:none;" class="editDiv">
													<div id="content"></div>
													<div id="page-selection"></div>													
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
	</form>
	
	<!-- 수정하기 Modal Window -->
	<div id="modModal" class="modal fade" role="dialog">
		<form name="modModalFrm">
		<!-- modal-dialog -->
  		<div class="modal-dialog">
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
					<table class="table table-bordered table-hover table-condensed" border="1px" 
					   				   			cellpadding="2" cellspacing="2" align="center" width="100%">
						<tr>
							<td>
								<input id="modTitle" type="text" style="width:100%; border: 0px;">
							</td>					
						</tr>
						<tr>
							<td>
								<textarea id="modContent" style="width:100%; border: 0px;"></textarea>
							</td>
						</tr>
					</table>	
			    </div>
			    <!-- //modal-body -->
				<div class="modal-footer">
					<button id="btnModSave" type="button" class="btn btn-default">수정완료</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
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
					<table class="table table-bordered table-hover table-condensed" border="1px" 
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
					<button id="btnItmSave" type="button" class="btn btn-default">작성완료</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
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