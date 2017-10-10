<%@ page import="com.sn.common.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
	//contextPath
	String contextPath = request.getContextPath();
	contextPath = "http://localhost:8080/"+contextPath;
%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<!-- JQuery CDN -->
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<!-- BootStrap CDN -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<script type="text/javascript">
	
	$(function() {
		$('#itm_content').keyup(function (e){
			var content = $(this).val();
			$(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
			$('#counter').html(content.length );
		});
		$('#itm_content').keyup();
	});	
	
	$(document).ready(function(){	 
		
		/*********************
			작성 취소 버튼
		**********************/
		$("#btnCancel").on("click", function(){
			if(doubleSubmitCheck()) return;			
			history.back();
		});
		
		/*********************
			작성 완료 버튼
		**********************/
		$("#btnSubmit").on("click", function(){
			if(doubleSubmitCheck()) return;			
			
			var frm = document.editForm;			
			frm.action = "do_update.do";
			frm.submit();
		});		
		
	    $("#editTable").on("click",".delRow",function(){// 삭제기능
	        $(this).closest("#testTr").remove();
	    });	    
	    
	    $("#editTable").on("click","#itemAdd",function(){	      
	        
	        var contents = '';
	        contents += '	<tr id="testTr">                                                                   ';
	        contents += '		<td colspan="2">                                                               ';
	        contents += '                                                                                      ';
	        contents += '<div class="container">                                                               ';
	        contents += '			<div class="row">                                                          ';
	        contents += ' 			<div class="col-md-10">                                                    ';
	        contents += '		<table class="table table-bordered table-hover table-condensed" border="1px"   ';
	        contents += '			   cellpadding="2" cellspacing="2" align="center">                         ';
	        contents += '		                                                                               ';
	        contents += '				<tr>                                                                   ';
	        contents += '					<td><input type="text" value="제목(Not Null)"/></td>                ';
	        contents += '				</tr>                                                                  ';
	        contents += '				<tr height="150px;">                                                   ';
	        contents += '				<td><textarea  name="content"  rows="10" cols="100" >내용(Not Null)</textarea></td>   ';
	        contents += '				</tr>                                                                  ';
	        contents += '				<tr>                                                                   ';
	        contents += '					<td align="right">글자수 용량:<span name="counter" id="counter">###</span></td>       ';
	        contents += '				</tr>                                                                  ';
	        contents += '				<tr height="100px;">                                                   ';
	        contents += '					<td align="right"><input type="button" value="+" id="itemAdd" />   ';
	        contents += '					<input type="button" name="delRow" class="delRow" value="-" /></td>';
	        contents += '				</tr>                                                                  ';
	        contents += '	   </table><!-- 내용테이블 -->                                                         '; 
	        contents += '		<table class="table table-bordered table-hover table-condensed" border="1px"   ';
	        contents += '			   cellpadding="2" cellspacing="2" align="center">                         ';
	        contents += '			   				                                                           ';
	        contents += '                                                                                      ';
	        contents += '		</table>                                                                       ';
	        contents += '		                                                                               ';
	        contents += '		                                                                               ';
	        contents += '	</div> <!-- span1 -->                                                              ';
	        contents += '	<div class="col-md-2">                                                             ';
	        contents += '		<table class="table table-bordered table-hover table-condensed" border="1px"   ';
	        contents += '			   cellpadding="2" cellspacing="2" align="center">                         ';
	        contents += '			<input type="button" id="moveUp" value="▲" /><br/>                         ';
	        contents += '			<input type="button" id="moveDown" value="▼" />                            ';
	        contents += '		</table>                                                                       ';
	        contents += '	</div><!-- span2 -->                                                               ';
	        contents += '</div> <!-- row div -->                                                               ';
	        contents += '</div><!-- 컨테이너 -->                                                                  ';
	        contents += '		</td>                                                                          ';
	        contents += '	</tr>                                                                              ';
	        
	        $('#AddOption').append(contents); // 추가기능	       
	    });
	    
	    $("#editTable").on("keyup","textarea[name='content']",function (){
	        var content = $(this).val();        
	        $(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
	        
	        var tbl = $(this).parent().parent().parent().parent();
	        counter = $(tbl).find("#counter");
	        $(counter).html(content.length );
	    });	    
		
	    //*********************************************************************//
	    //순서변경을 위한 함수
	    //단계:
	    	/*
	    		1. 위쪽 화살표 버튼에 id=moveUp를 준다. 기존 버튼과 동적 할당 버튼 모두에게 줘야 함!
	    		2. 아래쪽 화살표 버튼에 id=moveDown를 준다. 기존 버튼과 동적 할당 버튼 모두에게 줘야 함!
	    	
	    		3. 기존 jsp에서, default 테이블 1개가 있고 그 밑에 tbody[id='AddOption']이 있는 구조였다.
	    			이 tbody태그를 default테이블까지 감싸도록 수정한다.
	    			
	    		4. 혹시 개선하고 싶다면 아래 확인(소스 출처)
	 			 //http://ktsmemo.cafe24.com/s/jQueryTip/64
	    	*/
	    //*********************************************************************//
	    $("#editTable").on("click","#moveUp",function(){	    	
	    	var $tr = $(this).parent().parent().parent().parent().parent(); // 클릭한 버튼이 속한 tr 요소
	    	$tr.prev().before($tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기    	
	    });

	    $("#editTable").on("click","#moveDown",function(){	    	
	    	var $tr = $(this).parent().parent().parent().parent().parent(); // 클릭한 버튼이 속한 tr 요소
	    	$tr.next().after($tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기    	
	    });
		//순서변경을 위한 함수 end	    

		/*******************
		* 셀렉트박스 유지 처리
		********************/
		$("#selectBox > option[value=" + ${resume.rsm_div} + "]").attr("selected", true);
		
	});//close ready
	
	/**
	 * 중복서브밋 방지
	 * 
	 * @returns {Boolean}
	 */
	var doubleSubmitFlag = false;
	function doubleSubmitCheck(){
	    if(doubleSubmitFlag){
	        return doubleSubmitFlag;
	    }else{
	        doubleSubmitFlag = true;
	        return false;
	    }
	}	
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>수정하기</title>
</head>
<body>	
	<h2>수정하기</h2>
	<hr/>
	<div>
		<!-- 전체 form -->
		<form action="#" id="editForm" name="editForm">
		<!-- 자소서 id값 -->
		<input type="hidden" id="rsm_id" name="rsm_id" value="${resume.rsm_id }">
		<!-- 전체 table -->
		<table class="table table-bordered table-hover table-condensed" border="1px" 
				   cellpadding="2" cellspacing="2" align="center" id="editTable">								 	
				<tr>					
					<td>분야</td>
					<td>
						<select name="selectBox" id="selectBox" style="width:150px;" class="select_02">
						      <c:forEach var="codeVo" items="${codeList}" varStatus="status">
						         <option value="${status.index}">${codeVo.dtl_cd_nm}</option>
						      </c:forEach>
				   		</select>
					</td>
				</tr>
				<tr>
					<td>제목</td>
					<td>
						<input type="text" id="rsm_title" name="rsm_title" value="${resume.rsm_title}"/>
					</td>
				</tr>
				<tr>
					<td>ppt첨부</td>
					<td><input type="button" value="파일첨부" />파일을 첨부해주세요. </td>
				</tr>
				<tr>
					<td>내용</td>
					<td>
						<textarea id="rsm_content" name="rsm_content">${resume.rsm_content }</textarea>						
					</td>
				</tr>
			<!-- end 복붙 -->
			<tbody id="AddOption" >				
				<c:forEach var="item" items="${itemList}" >
					<c:if test="${item.u_id eq resume.u_id }">
						<tr id="testTr">
							<td colspan="2">			
								<div class="container">
			  							<div class="row">
			   							<div class="col-md-10">
											<table class="table table-bordered table-hover table-condensed" border="1px" 
								   							cellpadding="2" cellspacing="2" align="center">					
												<tr>
													<td>
														<input type="text" id="itm_form_id" name="itm_form_id" value="${item.itm_form_id}">
														<input type="text" id="itm_title" name="itm_title" value="${item.itm_title}"/> 
													</td>
												</tr>
												<tr height="150px;">
													<td>
														<textarea id="itm_content" name="itm_content"  rows="10" cols="100" >${item.itm_content }</textarea>
													</td>
												</tr>							
												<tr>
													<td align="right">글자수 :
														<span id="counter" name="counter">
															${fn:length(item.itm_content) }
															자
														</span>
													</td>
												</tr>
												<tr height="100px;">
													<td align="right"><input type="button" value="+" id="itemAdd" />
														<input type="button" name="delRow" class="delRow" value="-" />
													</td>
												</tr>
							   				</table><!-- 내용테이블 -->
											<table class="table table-bordered table-hover table-condensed" border="1px" 
											   			cellpadding="2" cellspacing="2" align="center">
											</table>
										</div> <!-- span1 -->
										<div class="col-md-2">
											<input type="button" id="moveUp" value="▲" /><br/>
											<input type="button" id="moveDown" value="▼" />
										</div><!-- span2 -->
									</div> <!-- row div -->
								</div><!-- 컨테이너 -->
							</td>
						</tr>
					</c:if>					
				</c:forEach>
			</tbody>
			<tr>
				<td colspan="2">
				<div align="center">
					<input type="button" id="btnCancel" value="작성취소">
					<input type="button" id="btnSubmit" value="작성완료">
				</div>	
				</td>
			</tr>							
		</table><!-- 바깥테이블 -->
		</form>
	</div><!-- 바깥테이블 div -->
</body>
</html>