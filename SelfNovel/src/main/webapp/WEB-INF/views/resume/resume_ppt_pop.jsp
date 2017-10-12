<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String contextPath = request.getContextPath();
	contextPath = "http://localhost:8080/"+contextPath;
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language="javascript">
function do_fileSave(){
	var frm = document.frm;
	frm.submit();
	
	//실행결과 보여줄 target을 parent로 변경한다.
	//exelForm.target = opener.parent;
	//폼째로 던져준다. submit은 저쪽에서 할거다.
	//opener.do_excelUpload_pop(exelForm);
	//self.close();
}
</script>
</head>
<body>

	<c:choose>
		<c:when test="${img_id eq null}">
			<form name="frm" method="post" action="pptUpload.do" class="form-horisontal" enctype="multipart/form-data">
				<label class="col-lg-4 control-label">File</label>
				<div class="col-lg-8">
					<input type="file" name="file" id="file" class="form-control input-sm"/>
					<input type="text" name="contextPath" id="contextPath" class="form-control input-sm" value="<%=contextPath %>"/>
				</div>
				<button type="button" class="btn btn-success" onclick="do_fileSave()">업로드</button>
			</form>		
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
				{
					alert(${img_id});
					opener.document.getElementById("img_id").value=${img_id};
					self.close();
				}
			</script>
		</c:otherwise>
	</c:choose>
</body>
</html>