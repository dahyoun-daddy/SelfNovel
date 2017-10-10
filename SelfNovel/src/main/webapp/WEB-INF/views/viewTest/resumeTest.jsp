<%@ page import="com.sn.common.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<!-- JQuery CDN -->
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
 
<!-- BootStrap CDN -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script type="text/javascript">
$(function() {
    $('#content_').keyup(function (e){
        var content = $(this).val();
        $(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
        $('#counter').html(content.length );
    });
    

    $('#content_').keyup();
});
</script>

<script type="text/javascript">
$(document).ready(function(){
    
    $("#testTable").on("click",".delRow",function(){// 삭제기능	
        //$(this).parent().parent().remove(); 
        //alert("aaa");
        $(this).closest("#testTr").remove();
    });
    
    
    $("#testTable").on("click","#itemAdd",function(){
        //alert("aa");
      
        
        var contents = '';
        contents += '	<tr id="testTr">                                                                               ';
        contents += '		<td colspan="2">                                                               ';
        contents += '                                                                                      ';
        contents += '<div class="container">                                                               ';
        contents += '			<div class="row">                                                          ';
        contents += ' 			<div class="col-md-10">                                                    ';
        contents += '		<table class="table table-bordered table-hover table-condensed" border="1px"   ';
        contents += '			   cellpadding="2" cellspacing="2" align="center">                         ';
        contents += '		                                                                               ';
        contents += '				<tr>                                                                   ';
        contents += '					<td><input type="text" name="title" value="제목(Not Null)"/></td>                ';
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
        contents += '	   </table><!-- 내용테이블 -->                                                       '; 
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
        contents += '			<input type="button" value="▲" id="moveUp"/><br/>                                     ';
        contents += '			<input type="button" value="▼" id="moveDown"/>                                          ';
        contents += '		</table>                                                                       ';
        contents += '	</div><!-- span2 -->                                                               ';
        contents += '</div> <!-- row div -->                                                               ';
        contents += '</div><!-- 컨테이너 -->                                                                 ';
        contents += '		</td>                                                                          ';
        contents += '	</tr>                                                                              ';
        
        $('#AddOption').append(contents); // 추가기능
       
    });
    
    $("#testTable").on("keyup","textarea[name='content']",function (){
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
    $("#testTable").on("click","#moveUp",function(){
    	var $tr = $(this).parent().parent().parent().parent().parent(); // 클릭한 버튼이 속한 tr 요소
    	$tr.prev().before($tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기    	
    });

    $("#testTable").on("click","#moveDown",function(){
    	var $tr = $(this).parent().parent().parent().parent().parent(); // 클릭한 버튼이 속한 tr 요소
    	$tr.next().after($tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기    	
    });
  //순서변경을 위한 함수 end
    //*********************************************************************//
    
    
 	//2017-10-10 개인용 팝업테스트
 	$('#popupTest').on("click",function(){
 	    //alert('openPopup');
 	    var url    ="pptUpload.do";
 	    var title  = "testpop";
 	    var status = "toolbar=yes,directories=yes,scrollbars=no,resizable=yes,status=yes,menubar=no,width=240, height=200, top=0,left=20"; 
 	    window.open(url, title,status); //window.open(url,title,status); window.open 함수에 url을 앞에와 같이 
  	});
});
  </script>
<title>Insert title here</title>
</head>
<body>
	<h2>form</h2>
	<hr/>

	<form name="testFrm" action="rsmParamTest.do" method="POST">
	<input type="hidden" name="rsm_ord_yn" value="1" />	
	<div>
		<table class="table table-bordered table-hover table-condensed" border="1px" 
				   cellpadding="2" cellspacing="2" align="center" id="testTable">
		
			    <tr>
					<td>분야</td>
					<td><select name="selectBox" id="selectBox" style="width:150px;" class="select_02">
						      <c:forEach var="codeVo" items="${list}">
						         <option value="${codeVo.dtl_cd_nm}">${codeVo.dtl_cd_nm}</option>
						      </c:forEach>
				   		</select>
					</td>


				</tr>
				<tr>
					<td>제목</td>
					<td><input type="text" name="stitle"/></td>
				</tr>
				<tr>
					<td>ppt첨부</td>
					<td><input type="button" value="파일첨부" id="popupTest"/>파일을 첨부해주세요. 
						<input type="text" id="img_id">
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td><input type="text" name="scontent"/></td>
				</tr>
				
			<!-- end 복붙 -->	
			<tbody id="AddOption" >	
			<tr id="testTr">
					<td colspan="2">
			
			<div class="container">
   			<div class="row">
	   			<div class="col-md-10">
					<table class="table table-bordered table-hover table-condensed" border="1px" 
						   cellpadding="2" cellspacing="2" align="center">
					
							<tr>
								<td><input type="text" name="title" value="제목(Not Null)"></input> </td>
							</tr>
							<tr height="150px;">
								<td><textarea  name="content"  rows="10" cols="100" >내용(Not Null)</textarea>
								</td>
							</tr>
							
							<tr>
								<td align="right">글자수 용량:<span id="counter" name="counter">###</span></td>
							</tr>
							<tr height="100px;">
								<td align="right"><input type="button" value="+" id="itemAdd" />
								<input type="button" name="delRow" class="delRow" value="-" /></td>
							</tr>
				   </table><!-- 내용테이블 -->
					<table class="table table-bordered table-hover table-condensed" border="1px" 
						   cellpadding="2" cellspacing="2" align="center">
						   				   
	
					</table>
					
					
				</div> <!-- span1 -->
				<div class="col-md-2">
					<table class="table table-bordered table-hover table-condensed" border="1px" 
						   cellpadding="2" cellspacing="2" align="center">
						<input type="button" value="▲" id="moveUp"/><br/>
						<input type="button" value="▼" id="moveDown"/>
					</table>
				</div><!-- span2 -->
			</div> <!-- row div -->
			</div><!-- 컨테이너 -->
					</td>
				</tr>	 
			</tbody>
			
			
			<tr>
				<td colspan="2">
				<div align="center">
					<input type="button" value="작성취소">
					<input type="button" value="작성완료">
				</div>	
				</td>
			</tr>
							
		</table><!-- 바깥테이블 -->
	</div><!-- 바깥테이블 div -->
	
	<input type="submit" value="버튼">
	</form>

</body>
</html>