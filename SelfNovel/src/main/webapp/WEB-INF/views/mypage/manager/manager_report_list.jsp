<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>

<%@ page import="com.sn.common.StringUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
  //contextPath
  String contextPath = request.getContextPath();
  contextPath = "http://localhost:8080/"+contextPath;  
%>
<%
	//넘겨받아야 하는 값:
	//page_size
	//page_num
	//totalCnt
	//userList
	
	/**************************
	* 페이징 및 검색 버튼 처리를 위한 세팅  *
	***************************/
	int bottomCount = 10;//하단에 표시될 페이지 수
	String searchDiv = "";	//검색구분
	String searchWord = "";	//검색어
	String page_size = "10";//한 페이지에 표시될 글의 수
	String page_num = "1";	//페이지 번호	
	String totalCnt = "0";	//총글수

	//request로부터 검색어, 검색구분, 페이지 등의 값을 초기화
	searchDiv  = StringUtil.nvl(request.getAttribute("searchDiv").toString(), "");
	searchWord = StringUtil.nvl(request.getAttribute("searchWord").toString(), "");	
	page_size  = StringUtil.nvl(request.getAttribute("page_size").toString(), "10");
	page_num   = StringUtil.nvl(request.getAttribute("page_num").toString(), "1");	
	totalCnt   = StringUtil.nvl(request.getAttribute("totalCnt").toString(), "0");

	//page_size 및 page_num을 Integer값으로 넘기기 위한 캐스팅
	int oPage_size = Integer.parseInt(page_size);
	int oPage_num = Integer.parseInt(page_num);
	int oTotalCnt = Integer.parseInt(totalCnt);
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- JQuery CDN -->
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<!-- BootStrap CDN -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<title> 신고 내역</title>

<style type="text/css">
	.table-head-rows {
		text-align: center;
		background-color: #4f81bd;
		color: #FFF;
		font-size: large;
	}
	
	.table_body_rows {
		font-size: medium;
		margin: auto;				
	}
	
	.btn-label {position: relative;left: -12px;display: inline-block;padding: 6px 12px;background: rgba(0,0,0,0.15);border-radius: 3px 0 0 3px;}
	.btn-labeled {padding-top: 0;padding-bottom: 0;}	
</style>

<script type="text/javascript">
	//ready
	$(function(){
		/********************************************************/
		/* allCheck
		/********************************************************/
		$('#allCheck').on('change', function() {
			$("input[name='chk']").prop('checked', this.checked);
		});
		
		/********************************************************/
		/* btnDelete
		/********************************************************/
		$('#btnDelete').on('click', function(){
			if(confirm("정말 삭제하시겠습니까??") == true){	
				
				//1. 선택된 체크박스에서 msg_id들을 전부 읽어온다.
				var param = "";				
				$("input[name=msgChk]:checked").each(function() {
					if(param == ""){
						param = $(this).val();
					}else{
						param = param + "&" + $(this).val();	
					}//close if
				});//close each
				
				if(param == ""){
					alert("메시지를 선택해주세요.");
					return;
				}
				
				$.ajax({
					type : "POST",
					url : "do_deleteMsg.do",
					dataType : "html",
					data : {"msgList" : param},
					success : function(data){
						alert(data+"건 삭제");						
						location.reload();
					}
				});//ajax
			}//if confirm					
		})//btn_delete_on_click
	});
	//--ready
	
	//do_search_page
	function do_search_page(url, page_num) {
		console.log(url + "\t" + page_num);
		var searchFrm = document.reportForm;
		searchFrm.page_num.value = page_num;
		searchFrm.action = url;
		searchFrm.submit();
	}
	
	function searchUser(data){
		alert(data);
	}

</script>

</head>
<body>
	<!-- 전체 Div -->
	<div>
		<h2>신고 리스트</h2>
		<hr/>
		<!-- reportForm  -->
		<form id="reportForm" name="reportForm">
			<input type="hidden" name="page_size" value="<%=page_size%>"/>
			<input type="hidden" name="page_num" value="<%=page_num%>"/>
			<input type="hidden" name="totalCnt" value="<%=totalCnt%>"/>		
			<!-- reportTable -->			
			<table id="reportTable" class="table table-bordered table-hover table-condensed" border="1px" 
			   cellpadding="2" cellspacing="2" align="center" width="600px;" >
				<!-- thead -->
				<thead>
					<tr>
						<th class="table-head-rows text-center"><input type="checkbox"  id="allCheck"></th>						
						<th class="table-head-rows"><label>메시지ID</label></th>
						<th class="table-head-rows"><label>발신자ID</label></th>
						<th class="table-head-rows"><label>게시물 ID</label></th>
						<th class="table-head-rows"><label>게시물 작성자</label></th>
						<th class="table-head-rows"><label>메시지내용</label></th>
						<th class="table-head-rows"><label>작성일</label></th>
						
					</tr>					
				</thead>
				<!-- //thead -->
				<!-- tbody -->
				<tbody>					
					<c:choose>
						<c:when test="${reportList.size() > 0}" >			            
							<c:forEach var="msgVO" items="${reportList}">
			                	<tr>
			                		<td>
			                			<input type="checkbox" name="msgChk" value="${msgVO.msg_id }">
			                		</td>
			                		<td><!-- 메시지 ID -->
			                			${msgVO.msg_id }
			                		</td>
			                		<td><!-- 발신자 ID -->
			                			${msgVO.msg_sender }
			                		</td>
			                		<td><!-- 신고받은 게시물 -->			                		
			                		<a href="#" onClick="javascript:window.open('../../resume/do_searchOne.do?rsm_id=${msgVO.rsm_id }', 'popup', 'width=800, height=600')">
			                				${msgVO.rsm_id }
			                			</a>
			                		</td>
			                		<td><!-- 신고게시물 작성자 -->
			                			<a href="#" onClick="searchUser('${msgVO.msg_notify}')">
			                				${msgVO.msg_notify }
			                			</a>
			                		</td>
			                		<td><!-- 메시지내용 -->
			                			${msgVO.msg_content }
			                		</td>
			                		<td><!-- 작성일 -->
			                			${msgVO.msg_reg_dt }
			                		</td>			                		
			                	</tr>
		               		</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="7">리스트가 없습니다.</td>
							</tr>					
						</c:otherwise>
					</c:choose>
				</tbody>
				<!-- //tbody -->
			</table>		
			<!-- //reportTa0ble -->
			<!-- 페이징 -->
			<div class="form-inline text-center ">
				<%=StringUtil.renderPaging(oTotalCnt, oPage_num, oPage_size, 10, "manager_report_list.do", "do_search_page") %>
			</div>			
		</form>	
		<div align="right">			
			<button type="button" id="btnDelete" class="btn btn-labeled btn-default" >			
				<span class="btn-label" style="height: 34px;">  
					<i class="glyphicon glyphicon-erase" ></i>																											
				</span>
				메시지 삭제			
			</button>
		</div>
		<!-- //reportform -->
	</div>
	<!-- //전체 div -->
</body>
</html>