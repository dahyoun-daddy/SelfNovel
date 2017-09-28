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
    $('#content').keyup(function (e){
        var content = $(this).val();
        $(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
        $('#counter').html(content.length );
    });
    $('#content').keyup();
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
        contents += '					<td><input type="text" value="제목(Not Null)"/></td>                ';
        contents += '				</tr>                                                                  ';
        contents += '				<tr height="150px;">                                                   ';
        contents += '				<td><textarea  id="content"  rows="10" cols="100" >내용(Not Null)</textarea></td>   ';
        contents += '				</tr>                                                                  ';
        contents += '				<tr>                                                                   ';
        contents += '					<td align="right">글자수 용량:<span id="counter">###</span></td>       ';
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
        contents += '			<input type="button" value="▲" /><br/>                                     ';
        contents += '			<input type="button" value="▼" />                                          ';
        contents += '		</table>                                                                       ';
        contents += '	</div><!-- span2 -->                                                               ';
        contents += '</div> <!-- row div -->                                                               ';
        contents += '</div><!-- 컨테이너 -->                                                                 ';
        contents += '		</td>                                                                          ';
        contents += '	</tr>                                                                              ';
        
        $('#AddOption').append(contents); // 추가기능
       

    });
});



  </script>
<title>Insert title here</title>
</head>
<body>
	<h2>form</h2>
	<hr/>
	<div>
		<table class="table table-bordered table-hover table-condensed" border="1px" 
				   cellpadding="2" cellspacing="2" align="center" width="500px;" height="100px;" id="testTable">
		
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
					<td><input type="text" /></td>
				</tr>
				<tr>
					<td>ppt첨부</td>
					<td><input type="button" value="파일첨부" />파일을 첨부해주세요. </td>
				</tr>
				<tr>
					<td>내용</td>
					<td></td>
				</tr>
				<tr id="testTr">
					<td colspan="2">
			
			<div class="container">
   			<div class="row">
	   			<div class="col-md-10">
					<table class="table table-bordered table-hover table-condensed" border="1px" 
						   cellpadding="2" cellspacing="2" align="center">
					
							<tr>
								<td><input type="text" value="제목(Not Null)"/> </td>
							</tr>
							<tr height="150px;">
								<td><textarea  id="content"  rows="10" cols="100" >내용(Not Null)</textarea>
								</td>
							</tr>
							
							<tr>
								<td align="right">글자수 용량:<span id="counter">###</span></td>
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
						<input type="button" value="▲" /><br/>
						<input type="button" value="▼" />
					</table>
				</div><!-- span2 -->
			</div> <!-- row div -->
			</div><!-- 컨테이너 -->
					</td>
				</tr>
			<!-- end 복붙 -->	
			<tbody id="AddOption" >
							 
			</tbody>		
		</table><!-- 바깥테이블 -->
	</div><!-- 바깥테이블 div -->
	
	<div align="center">
		<tr>
			<input type="button" value="작성취소">
			<input type="button" value="작성완료">
		</tr>
	</div>
</body>
</html>
