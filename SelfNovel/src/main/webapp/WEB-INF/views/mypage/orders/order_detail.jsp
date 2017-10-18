<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script type="text/javascript">
	function do_save(rsm_id){
		var itm_titles = "";
		var itm_contents = "";
		var itm_form_ids = "";
		var i=1;
		
		while(true){
			if($("#itm_title"+i).val() == null){
				break;
			} else{
				itm_titles += $("#itm_title"+i).val() + "\\";
				itm_contents += $("#itm_content"+i).val() + "\\";
				itm_form_ids += $("#itm_form_id"+i).val() + "\\";
				i++;
			}
		}
		
		$.ajax({
            url: "do_save.do",
            data: {itm_titles: itm_titles,
            	   itm_contents: itm_contents,
            	   itm_form_ids: itm_form_ids,
            	   u_id: '<%=session.getAttribute("u_id")%>',
            	   rsm_id: rsm_id
            	  },
            type: 'POST',
            success: function(result){
            	if(result == "fail"){
            		alert("저장하기 실패");
            	} else{
            		opener.parent.location.reload();
            		window.close();
            	}
            }
        });
	}
</script>
<title>:::ORDER DETAIL:::</title>
</head>
<body>
	<c:if test="${list[0].itm_prd_id eq null && isRev ne 'true'}">
		<table class="w3-table-all w3-large" style="width: 100%;">
			<tr>
				<td style="width: 30%;"><div align="center">제목</div></td>
				<td style="width: 70%;"><div align="left"><c:out value="${list[0].rsm_title }" /></div>
			</tr>
			<tr>
				<td><div align="center">의뢰자</div></td>
				<td><c:out value="${list[0].u_id }" /></td>
			</tr>
			<tr>
				<td colspan="2"><c:out value="${list[0].rsm_content }" /></td>
			</tr>
		</table>
	</c:if>
	<table style="width: 100%;">
		<% int i=0; %>
		<c:choose>
    	<c:when test="${list.size()>0}" >
    		<c:forEach var="unityItmVO" items="${list}">
				<tr>
					<td>
						<hr/>
						<% i++; %>
						<input type="hidden" value="${unityItmVO.itm_form_id }" id="itm_form_id<%=i %>" />
						<c:if test="${isRev ne 'true'}">
							<input type="text"class="form-control" style="width: 100%; background-color: white;" value="${unityItmVO.itm_title }" readonly/>
						</c:if>
						<c:if test="${isRev eq 'true'}">
							<input type="text" id="itm_title<%=i %>" name="itm_title<%=i %>" class="form-control" style="width: 100%; background-color: white;" value="${unityItmVO.itm_title }"/>
						</c:if>
					<td>
				<tr>
				<tr>
					<td>
						<c:if test="${isRev ne 'true'}">
							<textarea class="form-control"style="resize: none; width:100%; height: 300px; background-color: white;" readonly>${unityItmVO.itm_content }</textarea>
						</c:if>
						<c:if test="${isRev eq 'true'}">
							<textarea class="form-control" id="itm_content<%=i %>" name="itm_content<%=i %>" style="resize: none; width:100%; height: 300px; background-color: white;">${unityItmVO.itm_content }</textarea>
						</c:if>
					<td>
				<tr>
			</c:forEach>
		</c:when>
	</c:choose>
		<c:if test="${isRev eq 'true'}">
			<tr>
				<td>
					<div align="center">
						<input class="btn btn-success" type="button" value="저장하기" onclick="do_save(${list[0].rsm_id})"/>
					</div>
				</td>
			</tr>
		</c:if>
	</table>
</body>
</html>