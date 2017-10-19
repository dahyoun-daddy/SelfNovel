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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
 
<!-- BootStrap CDN -->
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script type="text/javascript">

// <!-- window.history.back()    page 합칠때 바꾸기-->
function javascript(){
	 
    window.location.href="http://localhost:8080/controller/resume/do_search.do";
}

</script>

<script type="text/javascript">

$(function(){
    //**********************************************
    //content_
    $('#content_').keyup(function (e){
        var content = $(this).val();
        $(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
        $('#counter').html(content.length );
    });    

    //**********************************************
    //save
	$("#btnSubmit").click(function(e){		
		//var tfrm = $("#tfrm");
		var tfrm = document.tfrm;
		//=========================================================
		//rsmVo.setRsm_id(rsm_id);
		//rsmVo.setImg_id(img_id);
		//stitle
		if($('#stitle').val()==""){
			$('#stitle').focus();
			alert("제목을 입력하세요!");
			return;
		}
		
		//=========================================================
		//제목 최대길이 검사
		if($("#stitle").val().length > 50){
			$("#stitle").focus();
			alert("제목은 최대 50글자입니다.(공백포함)");		
			return;
		}
		
		//=========================================================
		//scontent
		if($('#scontent').val()==""){
			$("#scontent").focus();
			alert("내용을 입력하세요!");
			return;
		}

	    //=========================================================
	    var isNull = false;	
	    	
	    //동적 title
		$("input[name=title]").each(function() {
			// element == this
			var record = $(this).val();
			if(record==""){ 
				isNull = true;
				$(this).focus();
				alert("소제목을 입력하세요!");
				return false;
			}
			
			if(record.length > 60){
				alert("제목의 길이는 최대 60자입니다.(꽁백포함)");
				$(this).focus();
				falg = false;
				return;
			}
	    });
	    
	    if(isNull){	    	
	    	return;
	    }
	    
	    //=========================================================
	    //동적 content
	    $("textarea[name=content]").each(function(){
	    	var recorder = $(this).val();
	    	if(recorder==""){
	    		isNull = true;
	    		return false;
	    	}
		});
	    
	    if(isNull){
	    	$(this).focus();
	    	alert("소내용을 입력하세요!");
	    	return;
	    }
	
	    tfrm.submit();
	    
	});
	
	
	//=========================================================
	//- 삭제여부
	$("#formTable").on("click",".delRow",function(){// 삭제기능			
		var tfrm = document.tfrm;
	  	var count=0;
	    $("input[name=title]").each(function(){
	    	count++;
	    });
	    
	    if(count > 1){
	    	$(this).closest("#testTr").remove();
	    }else{
	    	return;	
	    }
	});
});
</script>

<script type="text/javascript">

$(document).ready(function(){

    
    $("#formTable").on("click","#itemAdd",function(){
        //alert("aa");
      
        
        var contents = '';
        contents+= '<tr id="testTr">                                                                                                                         ';
        contents+= '	<td colspan="2">			                                                                                                         ';
        contents+= '		<div class="container">   						                                                                                 ';
        contents+= '  				<div style="width:100%; display:inline-block;">                                                                          ';
        contents+= '  					<!-- 내용 테이블 -->                                                                                                    ';
        contents+= '				<table class="table table-bordered table-condensed" border="1px" align="center">							             ';
        contents+= '					<tr>                                                                                                                 ';
        contents+= '						<td>                                                                                                             ';
        contents+= '							<div style="width: 95%; float:left;">                                                                        ';
        contents+= '								<input type="text" name="title" id="title" style="height : 30px;" placeholder="제목을 입력해주세요." required> ';
        contents+= '							</div>                                                                                                       ';
        contents+= '							<div style="width: 5%; float:right;">                                                                        ';
        contents+= '								<button type="button" name="delRow" class="delRow btn-danger" style="width:100%;height:30px;">           ';
        contents+= '									<span>                                                                                               ';
        contents+= '										<i class="glyphicon glyphicon-trash"></i>                                                        ';
        contents+= '									</span>                                                                                              ';
        contents+= '								</button>																								';
        contents+= '							</div>								 			                                                             ';
        contents+= '				 		</td>                                                                                                            ';
        contents+= '					</tr>                                                                                                                ';
        contents+= '					<tr height="150px;">                                                                                                 ';
        contents+= '						<td>                                                                                                             ';
        contents+= '							<textarea name="content" id="content" placeholder="내용을 입력해주세요." required></textarea>                         ';        
        contents+= '						</td>                                                                                                            ';
        contents+= '					</tr>							                                                                                     ';
        contents+= '					<tr>                                                                                                                 ';
        contents+= '						<td align="right">                                                                                               ';
        contents+= '							<div class="btn-group" role="group" style="float:left;">                                                     ';
        contents+= '								<button type="button" class="btn btn-default" id="moveDown">                                             ';
        contents+= '									<span>                                                                                               ';
        contents+= '										<i class="glyphicon glyphicon-arrow-down"></i>                                                   ';
        contents+= '									</span>                                                                                              ';
        contents+= '								</button>                                                                                                ';
        contents+= '								<button type="button" class="btn btn-default" id="moveUp">                                               ';
        contents+= '									<span>                                                                                               ';
        contents+= '										<i class="glyphicon glyphicon-arrow-up"></i>                                                     ';
        contents+= '									</span>                                                                                              ';
        contents+= '								</button>                                                                                                ';
        contents+= '							</div>                                                                                                       ';
        contents+= '							<div style="float:right;">                                                                                   ';
        contents+= '								글자수 :                                                                                                   ';
        contents+= '								<span id="counter" name="counter"> 0자                                                                    ';
        contents+= '								</span>                                                                                                  ';
        contents+= '							</div>                                                                                                       ';
        contents+= '						</td>                                                                                                            ';
        contents+= '					</tr>                                                                                                                ';
        contents+= '				</table><!-- 내용테이블 -->                                                                                                 ';
        contents+= '			</div> <!-- span1 -->                                                                                                        ';
        contents+= '		</div><!-- 컨테이너 -->                                                                                                             ';
        contents+= '	</td>                                                                                                                                ';
        contents+= '</tr>                                                                                                                                 '; 
        
        $('#AddOption').append(contents); // 추가기능
       
    });
    
    $("#formTable").on("keyup","textarea[name='content']",function (){
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
    $("#formTable").on("click","#moveUp",function(){
    	var $tr = $(this).closest("#testTr");
    	$tr.prev().before($tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기    	
    });

    $("#formTable").on("click","#moveDown",function(){
    	var $tr = $(this).closest("#testTr");
    	$tr.next().after($tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기    	
    });
  //순서변경을 위한 함수 end
    //*********************************************************************//
    
    
 	//2017-10-10 개인용 팝업테스트
 	$('#popupTest').on("click",function(){
 	    //alert('openPopup');
 	    var url    ="pptUpload.do";
 	    var title  = "testpop";
 	    var status = "toolbar=yes,directories=yes,scrollbars=yes,resizable=yes,status=yes,menubar=no,width=500, height=150, top=0,left=0"; 
 	    window.open(url, title,status); //window.open(url,title,status); window.open 함수에 url을 앞에와 같이 
  	});
});
</script>
<style type="text/css">
	input {
		width : 100%;
	}	
		
	textarea {
		width : 100%;
		resize: none;
		height: 200px;
	}
	
	.td-head{
		width:10%; 
		background-color: D6E6F5;
		text-align:center;		
	}
	.td-content{
		width:90%;
	}
	
	.btn-label {position: relative;left: -12px;display: inline-block;padding: 6px 12px;background: rgba(0,0,0,0.15);border-radius: 3px 0 0 3px;}
	.btn-labeled {padding-top: 0;padding-bottom: 0;}
</style>

<title>자기소개서 작성</title>
</head>
<body>
	<h2>자기소개서 작성</h2>
	<hr/>

	<form name="tfrm" action="do_save.do" method="POST">
	<input type="hidden" name="rsm_ord_yn" value="1" />	
	<div>
		<table class="table table-bordered table-condensed" border="1px" align="center">		
		    <tr>
				<td class="td-head">
					<label>
						카테고리
					</label>
				</td>
				<td class="td-content">
					<select name="rsm_div" id="rsm_div" style="width:150px;">
						<c:forEach var="codeVo" items="${codeList}">
							<option value="${codeVo.dtl_cd_id}">${codeVo.dtl_cd_nm}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="td-head">
					<label>
						제목
					</label>
				</td>
				<td class="td-content">
					<input type="text" name="stitle" id="stitle" required/>
				</td>
			</tr>
			<tr>
				<td class="td-head">
					<label>
						ppt첨부
					</label>
				</td>
				<td class="td-content">					
					<button type="button" id="popupTest" class="btn btn-default btn-labeled">
						<span class="btn-label" style="height: 35px;">
							<i class="glyphicon glyphicon-upload"></i>
						</span>
						파일첨부									
					</button> 
					<input type="hidden" id="img_id" name="img_id">
				</td>
			</tr>
			<tr>
				<td class="td-head">
					<label>
						내용
					</label>
				</td>
				<td class="td-content">
					<textarea id="scontent" name="scontent" required></textarea>					
				</td>
			</tr>
		</table>
		<!-- formTable -->
		<table class="table table-bordered table-condensed" border="1px" align="center" id="formTable">
			<tbody id="AddOption" >	
			<tr id="testTr">
				<td colspan="2">			
					<div class="container">   						
		   				<div style="width:100%; display:inline-block;">
		   					<!-- 내용 테이블 -->
							<table class="table table-bordered table-condensed" border="1px" align="center">							
								<tr>
									<td>
										<div style="width: 95%; float:left;">
											<input type="text" name="title" id="title" style="height : 30px;" placeholder="제목을 입력해주세요." required>
										</div>
										<div style="width: 5%; float:right;">
											<button type="button" name="delRow" class="delRow btn-danger" style="width:100%;height:30px;">
												<span>
													<i class="glyphicon glyphicon-trash"></i>
												</span>
											</button>																												
										</div>								 			
							 		</td>
								</tr>
								<tr height="150px;">
									<td>
										<textarea  name="content" id="content" placeholder="내용을 입력해주세요." required></textarea>									
									</td>
								</tr>							
								<tr>
									<td align="right">
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
											<span id="counter" name="counter"> 0자
											</span>
										</div>
									</td>
								</tr>
							</table><!-- 내용테이블 -->
						</div> <!-- span1 -->
					</div><!-- 컨테이너 -->
				</td>
			</tr>	 
		</tbody>
		<tfoot> 
			<tr>							
				<td align="center" style="border : hidden;">
					<a id="itemAdd"><img src="<c:url value='/resources/img/plus-button.png' />" width=50 height=50/></a>								 						
				</td>
			</tr>			
			<tr>							
				<td colspan="2">							
					<div align="center" style="border: hidden;">
						<button type="button" id="btnCancel" class="btn btn-default btn-labeled">
							<span class="btn-label" style="height: 35px;">
								<i class="glyphicon glyphicon-remove"></i>
							</span>
							작성 취소
						</button>
						<button type="button" id="btnSubmit" class="btn btn-success btn-labeled">
							<span class="btn-label" style="height: 35px;">
								<i class="glyphicon glyphicon-ok"></i>
							</span>
							작성 완료
						</button>
					</div>	
				</td>
			</tr>
		</tfoot>							
		</table><!-- 바깥테이블 -->
	</div><!-- 바깥테이블 div -->
	
	
	</form>

</body>
</html>