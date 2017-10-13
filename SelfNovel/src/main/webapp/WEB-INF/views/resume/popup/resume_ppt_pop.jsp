<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
</style>
<!-- JQuery CDN -->
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

<!-- BootStrap -->
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<!-- BootStrap CDN -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<script language="javascript">
function do_fileSave(){
	var frm = document.frm;
	console.log(frm.file);
	
	if(frm.file.value == ''){
		alert('파일을 첨부해주세요.');
		return;
	}else{
		frm.submit();
	}
}

function do_exit(){
	self.close();
}
</script>
</head>
<body>

	<c:choose>
		<c:when test="${img_id eq null}">
		<div class="jumbotron" style="hight: 100px;">
			<form name="frm" method="post" action="pptUpload.do" enctype="multipart/form-data">
				<div id="center" align="center">
					<input type="file" name="file" id="file"/>
					<br>
					<button type="button" class="btn btn-success" onclick="do_fileSave()">업로드</button>
					<button type="button" class="btn btn-success" onclick="do_exit()">취소</button>
				</div>
			</form>	
		</div>	
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
				{
					alert(${img_id});
					opener.document.getElementById("img_id").value=${img_id};
					opener.document.getElementById("popupTest").value="파일 첨부됨";
					self.close();
				}
			</script>
		</c:otherwise>
	</c:choose>
</body>
</html>