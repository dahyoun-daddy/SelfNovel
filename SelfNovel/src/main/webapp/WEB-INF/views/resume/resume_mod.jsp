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
			
			//rsm_title 공백검사
			if($("#rsm_title").val().trim() == ""){
	    		alert("제목을 입력해주세요.");
	    		$("#rsm_title").focus();
	    		return;	    	
	    	}
			
			if($("#rsm_title").val().length > 50){
				alert("제목은 최대 50글자입니다.(공백포함)");
				$("#rsm_title").focus();
	    		return;
			}
			
			//rsm_content 공백검사
			else if($("#rsm_content").val().trim() == ""){
	    		alert("내용을 입력해주세요.");
	    		$("#rsm_content").focus();
	    		return;
	    	}
			
			var flag = true;
			
			//Item 제목 공백 검사
			$("input[name=itm_title]").each(function() {
				var itm_title = $(this).val();
				if(itm_title == ""){
					alert("항목 제목을 입력해주세요!");
					$(this).focus();
					flag = false;
					return;
				}
				
				if(itm_title.length > 60){
					alert("제목의 길이는 최대 60자입니다.(꽁백포함)");
					$(this).focus();
					flag = false;
					return;
				}
			});
			
			if(flag == false){				
				return;
			}
			
			flag = true;
			 
			//Item 내용 공백 검사
			$("textarea[name=itm_content").each(function() {
				var itm_content = $(this).val();
				if(itm_content == ""){
					alert("항목 내용을 입력해주세요!");
					$(this).focus();									
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
	        contents+= '<tr id="testTr">                                                                                                                                                 ';
	        contents+= '	<td colspan="2" style="">			                                                                                                                         ';
	        contents+= '		<div class="container">			  							                                                                                             ';
	        contents+= '			<div style="width:100%; display:inline-block;">                                                                                             ';
	        contents+= '				<table class="table table-bordered table-condensed" border="1px" align="center" style="width:100%; border:hidden;">					             ';
	        contents+= '					<tr>                                                                                                                                         ';
	        contents+= '						<td>                                                                                                                                     ';
	        contents+= '							<div style="width: 95%; float:left;">                                                                                                ';
	        contents+= '							<input type="hidden" id="itm_form_id" name="itm_form_id">																			 ';
	        contents+= '								<input type="text" id="itm_title" name="itm_title" style="width:100%; height:30px;"                           placeholder="제목"/>';
	        contents+= '							</div>                                                                                                                               ';
	        contents+= '							<div style="width: 5%; float:right;">                                                                                                ';
	        contents+= '								<button type="button" name="delRow" class="delRow btn-danger" style="width:100%;height:30px;">                                   ';
	        contents+= '									<span>                                                                                                                       ';
	        contents+= '										<i class="glyphicon glyphicon-trash"></i>                                                                                ';
	        contents+= '									</span>                                                                                                                      ';
	        contents+= '								</button>                                                                                                                        ';
	        contents+= '								<!-- <input type="button" name="delRow" class="delRow" value="x" /> -->															 ';
	        contents+= '							</div>                                                                                                                               ';
	        contents+= '						</td>														                                                                             ';
	        contents+= '					</tr>                                                                                                                                        ';
	        contents+= '					<tr height="150px;">                                                                                                                         ';
	        contents+= '						<td>                                                                                                                                     ';
	        contents+= '							<textarea id="itm_content" name="itm_content"  rows="10" cols="100" placeholder="내용을 입력해주세요."></textarea>                         ';
	        contents+= '						</td>                                                                                                                                    ';
	        contents+= '					</tr>							                                                                                                             ';
	        contents+= '					<tr>                                                                                                                                         ';
	        contents+= '						<td>                                                                                                                                     ';
	        contents+= '							<div class="btn-group" role="group" style="float:left;">                                                                             ';
	        contents+= '								<button type="button" class="btn btn-default" id="moveDown">                                                                     ';
	        contents+= '									<span>                                                                                                                       ';
	        contents+= '										<i class="glyphicon glyphicon-arrow-down"></i>                                                                           ';
	        contents+= '									</span>                                                                                                                      ';
	        contents+= '								</button>                                                                                                                        ';
	        contents+= '								<button type="button" class="btn btn-default" id="moveUp">                                                                       ';
	        contents+= '									<span>                                                                                                                       ';
	        contents+= '										<i class="glyphicon glyphicon-arrow-up"></i>                                                                             ';
	        contents+= '									</span>                                                                                                                      ';
	        contents+= '								</button>                                                                                                                        ';
	        contents+= '							</div>                                                                                                                               ';
	        contents+= '							<div style="float:right;">                                                                                                           ';
	        contents+= '								글자수 :                                                                                                                           ';
	        contents+= '								<span id="counter" name="counter">                                                                                               ';
	        contents+= '									0자                                                                                                                           ';
	        contents+= '								</span>                                                                                                                          ';
	        contents+= '							</div>                                                                                                                               ';
	        contents+= '						</td>                                                                                                                                    ';
	        contents+= '					</tr>                                                                                                                                        ';
	        contents+= '	   			</table><!-- 내용테이블 -->										                                                                                 ';
	        contents+= '			</div> <!-- span1 -->									                                                                                             ';
	        contents+= '		</div><!-- 컨테이너 -->                                                                                                                                     ';
	        contents+= '	</td>                                                                                                                                                        ';
	        contents+= '</tr><!-- testTr -->                                                                                                                                             ';
	        
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
	    	/* var $tr = $(this).parent().parent().parent().parent().parent().parent().parent().parent().parent(); // 클릭한 버튼이 속한 tr 요소 */
	    	var $tr = $(this).closest("#testTr");
	    	$tr.prev().before($tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기    	
	    })

	    $("#addTable").on("click","#moveDown",function(){	    	
	    	/* var $tr = $(this).parent().parent().parent().parent().parent().parent().parent().parent().parent(); // 클릭한 버튼이 속한 tr 요소 */
	    	var $tr = $(this).closest("#testTr");
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
	
	function moveScroll(param){
		//이동할 좌표 불러와서
		var offset = param.offset();
		//이동
		$('html, body').animate({scrollTop : offset.top}, 400);		
	}
	
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
		<form action="#" id="editForm" name="editForm" method="post">
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
														<td>
															<div style="width: 95%; float:left;">
																<input type="hidden" id="itm_form_id" name="itm_form_id" value="${item.itm_form_id}">
																<input type="text" id="itm_title" name="itm_title" style="width:100%; height:30px;" value="${item.itm_title}" placeholder="제목"/>
															</div>
															<div style="width: 5%; float:right;">
																<button type="button" name="delRow" class="delRow btn-danger" style="width:100%;height:30px;">
																	<span>
																		<i class="glyphicon glyphicon-trash"></i>
																	</span>
																</button>
																<!-- <input type="button" name="delRow" class="delRow" value="x" /> -->																
															</div>
														</td>														
													</tr>
													<tr height="150px;">
														<td>
															<textarea id="itm_content" name="itm_content"  rows="10" cols="100" placeholder="내용을 입력해주세요.">${item.itm_content }</textarea>
														</td>
													</tr>							
													<tr>
														<td>
															<div class="btn-group" role="group" style="float:left;">
																<button type="button" class="btn btn-default" id="moveDown">
																	<span>
																		<i class="glyphicon glyphicon-arrow-down"></i>
																	</span>
																</button>
																<button type="button" class="btn btn-default" id="moveUp">
																	<span>
																		<i class="glyphicon glyphicon-arrow-up"></i>
																	</span>
																</button>
															</div>
															<div style="float:right;">
																글자수 :
																<span id="counter" name="counter">
																	${fn:length(item.itm_content) }
																	자
																</span>
															</div>
														</td>
													</tr>
									   			</table><!-- 내용테이블 -->										
											</div> <!-- span1 -->									
										</div><!-- 컨테이너 -->
									</td>
								</tr><!-- testTr -->
							</c:if>					
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>							
							<td align="center">
								<a id="itemAdd"><img src="<c:url value='/resources/img/plus-button.png' />" width=50 height=50/></a>								 
								<!-- <input type="button" value="+" id="itemAdd" /> -->
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