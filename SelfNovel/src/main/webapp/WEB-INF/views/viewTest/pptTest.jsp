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
<!-- jquery. ie쓰면 이 버전 못쓰고 1.대 써야함 -->
<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery-3.2.1.js"></script>
<!-- 부트스트랩 -->
<link href="<%=contextPath %>/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=contextPath %>/resources/css/bootstrap-theme.min.css" rel="stylesheet">
<script src="<%=contextPath %>/resources/js/bootstrap.min.js"></script>
<script type="text/javascript">

	function do_fileSave(){
		var frm=document.frm;
		var flag = true;

		$("input[type='file']").each(function(){
			//$(this).val()==null 은 먹히지 않는다.
			if($(this).val()==''){
				alert('파일 업로드를 완료하세요.');
				flag = false;
				return false; //continue: return true //break: return false
			}
		});	

		//자꾸 강제로 서브밋이 되어버리는 것 같다. 
		//button태그를 그대로 form안에 주면 발생하는 일이라고 하는데, 
		//1. input type="button"을 주거나(이 경우 디자인이 좀 바뀐다고 한다)
		//2. type=button을 추가하거나
		//3. 버튼 태그를 폼 밖으로 빼야한다고.
		if(flag==true){
			frm.submit();
		}else{
			return;
		}				
	}
</script>
<title>Insert title here</title>
</head>
<body>

		<div class ="container">
			<div class="col-lg-12"></div>		
			<div class="col-lg-12"></div>
			<div class="panel panel-default"></div>
			<div class="panel-heading text-center">파일 업로드</div>
			<form name="frm" method="post" action="pptUpload.do" class="form-horisontal" enctype="multipart/form-data">
				<div class="form-group">
					<label class="col-lg-4 control-label">File</label>
					<div class="col-lg-8">
						<input type="file" name="file" id="file" class="form-control input-sm"/>
						<input type="text" name="contextPath" id="contextPath" class="form-control input-sm" value="<%=contextPath %>"/>
					</div>
					<button type="button" class="btn btn-success" onclick="do_fileSave()">업로드</button>
				</div>
			</form>
		</div>
		
		
		<!--  
				<div class="container">
			<div class="form-group">
				<label class="col-lg-4 control-label">File</label>
				<div class="col-lg-8">
				<c:forEach var="imgVO" items="${imgList}">
					<img alt="" src="<%=contextPath %>/resources/${imgVO}">		
				</c:forEach>
				</div>
			</div>			
		</div>	
		-->
		
		
	<div align="center">

			<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
			  <!-- Indicators -->
			  <ol class="carousel-indicators">
			    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
			    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
			    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
			  </ol>
			
			  <!-- Wrapper for slides -->
			  <div class="carousel-inner" role="listbox">
			    <div class="item active">
			      <img src="http://localhost:8080//controller/resources/images/ppt_image0.png" alt="...">
			      <div class="carousel-caption">
			        ...
			      </div>
			    </div>
			    
			    <!-- images -->
			    <c:forEach var="imgVO" items="${imgList}">
				    <div class="item">
				      <img alt="" src="<%=contextPath %>/resources/${imgVO}">
				      <div class="carousel-caption">
				        ...
				      </div>
				    </div>		    	
				</c:forEach>

			  </div>
			
			  <!-- Controls -->
			  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
			    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
			    <span class="sr-only">Previous</span>
			  </a>
			  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
			    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			    <span class="sr-only">Next</span>
			  </a>
			</div>
		</div>





<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
  </ol>

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
    <div class="item active">
      <img src="..." alt="...">
      <div class="carousel-caption">
        ...
      </div>
    </div>
    <div class="item">
      <img src="..." alt="...">
      <div class="carousel-caption">
        ...
      </div>
    </div>
    ...
  </div>

  <!-- Controls -->
  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
</body>
</html>