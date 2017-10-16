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
	
	$(document).ready(function(){

	    /*
	    	textarea 글자수 count 기능
	    */
	    $("#addTable").on("keyup","textarea[name='itm_content']",function (){
	        var content = $(this).val();        
	        $(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
	        
	        var tbl = $(this).parent().parent().parent().parent();
	        counter = $(tbl).find("#counter");
	        $(counter).html(content.length );
	    })  
		
		/*********************
			작성 취소 버튼
		**********************/
		$("#btnCancel").on("click", function(){
			if(doubleSubmitCheck()) {
				return;
			}
			history.back();
		})
		
		/*********************
			작성 완료 버튼
		**********************/
		$("#btnSubmit").on("click", function(){
			//이 함수를 빠져나가야 함
			
			var flag = true;
			
			//제목 공백 검사
			$("input[name=itm_title]").each(function() {
				var itm_title = $(this).val();
				if(itm_title == ""){
					alert("제목을 입력해주세요!");					
					flag = false;
					return;
				}
			})
			
			if(flag == false){
				return;
			}
			
			flag = true;
			 
			//내용 공백 검사
			$("textarea[name=itm_content").each(function() {
				var itm_title = $(this).val();
				if(itm_title == ""){
					alert("내용을 입력해주세요!");
					flag = false;
					return;
				}
			})
			
			if(flag == false){
				return;
			}
			
			if(doubleSubmitCheck()) return;			
			
			var frm = document.editForm;			
			frm.action = "do_update.do";
			frm.submit();
		})	
		
	    $("#addTable").on("click",".delRow",function(){// 삭제기능
	    	var count = 0;
	    	
	    	$("tr[id=testTr]").each(function(){
	    		count++;	
	    	})
	    	
	    	if(count > 1){
	    		$(this).closest("#testTr").remove();	
	    	}else{
	    		alert("마지막 남은 항목을 삭제할 수 없습니다.");
	    		return;
	    	}
	    	
	    })    
	    
	    $("#addTable").on("click","#itemAdd",function(){	      
	        
	        var contents = '';
	        contents+= '<tr id="testTr">                                                                                                                     ';
	        contents+= '	<td colspan="2" style="">			                                                                                             ';
	        contents+= '		<div class="container">			  							                                                                 ';
	        contents+= '						<div style="width:100%; display:inline-block;">                                                              ';
	        contents+= '				<table class="table table-bordered table-condensed" border="1px" align="center" style="width:100%; border:hidden;">	';		
	        contents+= '					<tr>                                                                                                             ';
	        contents+= '						<td style="width:98%;">                                                                                      ';
	        contents+= '							<input type="hidden" id="itm_form_id" name="itm_form_id" >                    ';
	        contents+= '							<input type="text" id="itm_title" name="itm_title" style="width:100%;" placeholder="제목" />';										 
	        contents+= '						</td>                                                                                                        ';
	        contents+= '						<td style="width:2%;">                                                                                       ';
	        contents+= '							<input type="button" name="delRow" class="delRow" value="x" />                                           ';
	        contents+= '						</td>														                                                 ';
	        contents+= '					</tr>                                                                                                            ';
	        contents+= '					<tr height="150px;">                                                                                             ';
	        contents+= '						<td colspan="2">                                                                                             ';
	        contents+= '							<textarea id="itm_content" name="itm_content"  rows="10" cols="100" placeholder="내용을 입력해주세요."></textarea>     ';
	        contents+= '						</td>                                                                                                        ';
	        contents+= '					</tr>							                                                                                 ';
	        contents+= '					<tr>                                                                                                             ';
	        contents+= '						<td colspan="2" align="right">글자수 :                                                                         ';
	        contents+= '							<span id="counter" name="counter">                                                                       ';
	        contents+= '								0                                                                     ';
	        contents+= '								                                                                                                    ';
	        contents+= '							</span>                                                                                                  ';
	        contents+= '						</td>                                                                                                        ';
	        contents+= '					</tr>                                                                                                            ';
	        contents+= '										<tr height="100px;">                                                                         ';
	        contents+= '						<td align="right">                                                                                           ';
	        contents+= '							<input type="button" value="+" id="itemAdd" />                                                           ';
	        contents+= '							<input type="button" name="delRow" class="delRow" value="-" />                                           ';
	        contents+= '						</td>                                                                                                        ';
	        contents+= '					</tr>                                                                                                       ';
	        contents+= '	   			</table>								                                                     ';
	        contents+= '			</div> <!-- span1 -->                                                                                                    ';
	        contents+= '			<div style="margine:0 auto;">                                                                                            ';
	        contents+= '				<input type="button" id="moveDown" value="▼" style="width: 50%; float:right;"/>                                      ';
	        contents+= '				<input type="button" id="moveUp" value="▲" style="width: 50%; float:left;"/>                                         ';
	        contents+= '			</div><!-- span2 -->									                                                                 ';
	        contents+= '		</div><!-- 컨테이너 -->                                                                                                         ';
	        contents+= '	</td>                                                                                                                            ';
	        contents+= '</tr>                                                                                                               ';
	        
	        $("#AddOption").append(contents); // 추가기능	       
	    })

		
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
	    $("#addTable").on("click","#moveUp",function(){	    	
	    	var $tr = $(this).parent().parent().parent().parent(); // 클릭한 버튼이 속한 tr 요소
	    	$tr.prev().before($tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기    	
	    })

	    $("#addTable").on("click","#moveDown",function(){	    	
	    	var $tr = $(this).parent().parent().parent().parent(); // 클릭한 버튼이 속한 tr 요소
	    	$tr.next().after($tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기    	
	    })
		//순서변경을 위한 함수 end	    

		/*******************
		* 셀렉트박스 유지 처리
		********************/
		$("#selectBox > option[value=" + ${resume.rsm_div} + "]").attr("selected", true)
		
		
	 	//2017-10-12 다른 파일 업로드
	 	$('#popupTest').on("click",function(){
	 	    //alert('openPopup');
	 	    var url    ="pptUpload.do";
	 	    var title  = "testpop";
	 	    var status = "toolbar=yes,directories=yes,scrollbars=yes,resizable=yes,status=yes,menubar=no,width=500, height=150, top=0,left=0"; 
	 	    window.open(url, title,status); //window.open(url,title,status); window.open 함수에 url을 앞에와 같이 
	  	});
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
<style type="text/css">
	textarea {
		width : 100%;
		resize: none;
		height: 200px;
	}

</style>
</head>
<body>	
	<h2>수정하기</h2>
	<hr/>
	<div align="center">
		<!-- 전체 form -->
		<form action="#" id="editForm" name="editForm">
			<!-- 자소서 id값 -->
			<input type="hidden" id="rsm_id" name="rsm_id" value="${resume.rsm_id }">
			<input type="hidden" id="u_id" name="u_id" value="${resume.u_id }">
			<!-- 전체 table -->
			<table class="table table-bordered table-condensed" border="1px" align="center" id="editTable">								 	
				<tr>					
					<td align="center" style="width:10%; background-color: D6E6F5;">
						<label>카테고리</label>
					</td>
					<td>
						<select name="selectBox" id="selectBox" style="width:150px;" class="select_02">
							<c:forEach var="codeVo" items="${codeList}" varStatus="status">
								<option value="${status.index}">${codeVo.dtl_cd_nm}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="center" style="width:10%; background-color: D6E6F5;">
						<label>제목</label>
					</td>
					<td>																		
						<input type="text" id="rsm_title" name="rsm_title" value="${resume.rsm_title}" style="width:100%;"/>
					</td>
				</tr>
				<tr>
					<td align="center" style="width:10%; background-color: D6E6F5;">
						<label>ppt첨부 </label>
					</td>
					<td>
						<!-- for ppt -->
						<!-- 2017-10-12 @autor: lsg -->
						<c:choose>
							<c:when test="${resume.img_id eq null}">
								<input type="hidden" name="img_id" id="img_id">
								<input type="button" id="popupTest" value="파일첨부"/>파일을 첨부해주세요.
							</c:when>
							<c:otherwise>
								<input type="hidden" name="img_id" id="img_id" value="${resume.img_id}">
								<input type="button" id="popupTest" value="다른 파일첨부"/> 기존에 첨부한 파일이 있습니다.
							</c:otherwise>
						</c:choose> 
						<!-- for ppt end -->
					</td>
				</tr>
				<tr>
					<td align="center" style="width:10%; background-color: D6E6F5;">
						<label>내용</label>
					</td>
					<td>
						<textarea id="rsm_content" name="rsm_content">${resume.rsm_content }</textarea>						
					</td>
				</tr>
				</table>
				<!-- edit table -->								
				<table class="table table-bordered table-condensed" align="center" id="addTable" style="border: hidden;">				
					<tbody id="AddOption" >				
						<c:forEach var="item" items="${itemList}" varStatus="status">
							<c:if test="${item.u_id eq resume.u_id }">
								<tr id="testTr">
									<td colspan="2" style="">			
										<div class="container">			  							
					   						<div style="width:100%; display:inline-block;">
												<table class="table table-bordered table-condensed" border="1px" align="center" style="width:100%; border:hidden;">					
													<tr>
														<td style="width:98%;">
															<input type="hidden" id="itm_form_id" name="itm_form_id" value="${item.itm_form_id}">
															<input type="text" id="itm_title" name="itm_title" style="width:100%;" value="${item.itm_title}" placeholder="제목"/>														 
														</td>
														<td style="width:2%;">
															<input type="button" name="delRow" class="delRow" value="x" />
														</td>														
													</tr>
													<tr height="150px;">
														<td colspan="2">
															<textarea id="itm_content" name="itm_content"  rows="10" cols="100" placeholder="내용을 입력해주세요.">${item.itm_content }</textarea>
														</td>
													</tr>							
													<tr>
														<td colspan="2" align="right">글자수 :
															<span id="counter" name="counter">
																${fn:length(item.itm_content) }
																자
															</span>
														</td>
													</tr>
<!--  													<tr height="100px;">
														<td align="right">
															<input type="button" value="+" id="itemAdd" />
															<input type="button" name="delRow" class="delRow" value="-" />
														</td>
													</tr> -->
									   			</table><!-- 내용테이블 -->										
											</div> <!-- span1 -->
											<div style="margine:0 auto;">
												<input type="button" id="moveDown" value="▼" style="width: 50%; float:right;"/>
												<input type="button" id="moveUp" value="▲" style="width: 50%; float:left;"/>
											</div><!-- span2 -->									
										</div><!-- 컨테이너 -->
									</td>
								</tr><!-- testTr -->
							</c:if>					
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td align="center">								 
								<input type="button" value="+" id="itemAdd" />
							</td>
						</tr>
						<tr>							
							<td colspan="2">							
							<div align="center">
								<input type="button" id="btnCancel" value="작성취소">
								<input type="button" id="btnSubmit" value="작성완료">
							</div>	
							</td>
						</tr>		
					</tfoot>					
				</table><!-- 바깥테이블 -->
			</form>
		</div><!-- 바깥테이블 div -->
</body>
</html>