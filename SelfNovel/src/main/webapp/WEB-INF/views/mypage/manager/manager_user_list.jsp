<%@page import="java.util.List"%>
<%@page import="com.sn.codes.domain.CodesVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.sn.common.StringUtil"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%
  //contextPath
  String contextPath = request.getContextPath();
  contextPath = "http://localhost:8080/"+contextPath;  
%>
<%
	//넘겨받아야 하는 값:
	//page_size
	//page_num
	//totalCnt
	//userList
	
	/**************************
	* 페이징 및 검색 버튼 처리를 위한 세팅  *
	***************************/
	int    bottomCount = 10;//하단에 표시될 페이지 수
	String SEARCH_DIV = "";	//검색구분
	String SEARCH_WORD = "";	//검색어
	String PAGE_SIZE = "10";//한 페이지에 표시될 글의 수
	String PAGE_NUM = "1";	//페이지 번호
	String ORDER_DIV = "";	//정렬기준
	String totalCnt = "0";	//총글수

	//request로부터 검색어, 검색구분, 페이지 등의 값을 초기화
	SEARCH_DIV  = StringUtil.nvl(request.getAttribute("SEARCH_DIV").toString(), "");
	SEARCH_WORD = StringUtil.nvl(request.getAttribute("SEARCH_WORD").toString(), "");	
	PAGE_SIZE  = StringUtil.nvl(request.getAttribute("PAGE_SIZE").toString(), "10");
	PAGE_NUM   = StringUtil.nvl(request.getAttribute("PAGE_NUM").toString(), "1");
	ORDER_DIV   = StringUtil.nvl(request.getAttribute("ORDER_DIV").toString(), "");
	totalCnt   = StringUtil.nvl(request.getAttribute("totalCnt").toString(), "0");

	//page_size 및 page_num을 Integer값으로 넘기기 위한 캐스팅
	int oPage_size = Integer.parseInt(PAGE_SIZE);
	int oPage_num = Integer.parseInt(PAGE_NUM);
	int oTotalCnt = Integer.parseInt(totalCnt);
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 부트스트랩 -->
<link href="<%=contextPath%>/resources/css/bootstrap.css"
	rel="stylesheet">
<link href="<%=contextPath%>/resources/css/bootstrap-theme.min.css" rel="stylesheet">
<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
<script type="text/javascript" src="<%=contextPath%>/resources/js/jquery-3.2.1.js"></script>
<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
<script src="<%=contextPath%>/resources/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function() {
		/********************************************************/
		/* allCheck */
		/********************************************************/
		$('#allCheck').on('change', function() {
			$("input[name='chkList']").prop('checked', this.checked);
		});		
		
		/********************************************************/
		/* do_search */
		/********************************************************/		
		$('#do_search').on('click', function() {
			var searchFrm = document.searchFrm;	
			searchFrm.PAGE_NUM.value = '1';
			searchFrm.submit();
		});	
		
		/********************************************************/
		/* do_delete */
		/********************************************************/		
		$('#do_delete').on('click', function() {
			var userArray = new Array();
			$(":checkbox[name='chkList']:checked").each(function(idx,row){
				// element == this
				var record = $(row).parents("#userInfo");
				var id = $(record).find('td').eq(2).text();
				
				var userVO = new Object();
				userVO.u_id = id;
				userArray.push(userVO);
				
				//배열로 전송
				console.log(id);
			});	
			
			if(userArray.length<=0){
				alert("선택한 열이 없습니다.");
				return;
			}
			
			var userInfo = JSON.stringify(userArray);
			//배열 형태로 서버쪽 전송을 위한 설정
			//$.ajaxSettings.traditional = true;
			$.ajax({
				type: "POST",
				url: "do_delete.do",
				dataType:"JSON",
				data: {
					"data": userInfo,
					},
				success: function(data){
					if(data<0)
						alert('다건삭제 실패');
					else{
						alert(data+'건 삭제를 성공했습니다.');
					}
				},
				complete: function(){
					location.reload();
				},
				error: function(){
					alert("오류가 발생하였습니다. 관리자에게 문의해주십시오.");
				}	
			});// -- ajax closed
		});//--end do_delete	
		
		/********************************************************/
		/* do_save */
		/********************************************************/
		$('#do_save').on('click', function() {
			
		});
	});


	function do_search_page(url, PAGE_NUM) {
		console.log(url + "\t" + PAGE_NUM);
		var searchFrm = document.searchFrm;
		searchFrm.PAGE_NUM.value = PAGE_NUM;
		searchFrm.action = url;
		searchFrm.submit();
	}
</script>
</head>
<body>
	<h2>회원 리스트</h2>
	<hr/>
	<form action="manager_user_list.do" name="searchFrm">
		<input type="hidden" name="PAGE_SIZE" value="<%=PAGE_SIZE%>"/>
		<input type="hidden" name="PAGE_NUM" value="<%=PAGE_NUM%>"/>
		<input type="hidden" name="totalCnt" value="<%=totalCnt%>"/>
		<table border="1px" >
			<tr>
				<td>검색</td>
				<td>
					<input type="text" id="SEARCH_WORD" name="SEARCH_WORD" value="<%=SEARCH_WORD%>"/>
					<input type="button" id="do_search" value="검색"/>
				</td>
			</tr>
		</table>
	
	<h3>총 회원수: <c:out value="${totalCnt}명"/></h3>
	<hr/>
	
		선택 멤버를 <input type="button" id="do_delete" value="강제 탈퇴"/>
		<select name="ORDER_DIV">
			<c:forEach var="codeVO" items="${codeList}">
				<c:choose>
					<c:when test="${codeVO.dtl_cd_id eq ORDER_DIV}">
						<option value="${codeVO.dtl_cd_id}" selected="selected">${codeVO.dtl_cd_nm}</option>
					</c:when>
					<c:otherwise>
						<option value="${codeVO.dtl_cd_id}">${codeVO.dtl_cd_nm}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
	</form>	
		<table border="1px" >
			<tr>
				<th><input type="checkbox"  id="allCheck"></th>
				<th>글번호</th>
				<th>아이디</th>
				<th>가입일</th>
				<th>등급</th>
				<th>게시글 수</th>
				<th>첨삭 수</th>
			</tr>
			<c:choose>
				<c:when test="${userList.size()>0}">
					<c:forEach var="userVO" items="${userList}">
						<tr id="userInfo">
							<td><input type="checkbox" name="chkList"></td>
							<td>${userVO.no}</td>
							<td>${userVO.u_id}</td>
							<td>${userVO.u_reg_dt}</td>
							<td>${userVO.u_level}</td>
							<td>${userVO.u_write_cnt}</td>
							<td>${userVO.u_mod_cnt}</td>
						</tr>
					</c:forEach>
				</c:when>	
				<c:otherwise>
					<tr>
						<td colspan="7">리스트가 없습니다.</td>
					</tr>					
				</c:otherwise>
			</c:choose>			
		</table>
		
	<div class="form-inline text-center ">
		<%=StringUtil.renderPaging(oTotalCnt, oPage_num, oPage_size, 10, "manager_user_list.do", "do_search_page") %>
	</div>		
	<form action="">
		<input type="button" id="do_save" value="관리자 추가" data-toggle="modal" data-target="#addManager">
	</form>

	<!-- Modal -->
	<!-- 
		2017-09-19 추가
		개인용 excel업로드 서블릿을 사용한다. 양식은 테이블 아무거나 체크하고 엑셀다운로드를 클릭해서 다운받으면 된다.
		service에서 do_upsert를 호출한다.
	 -->
	<div class="modal fade" id="addManager" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
	      </div>
	      <div class="modal-body" align="center">
			 <form name='do_save' action='do_save.do' method='post'>
			 	<label>아이디</label>
			 	<input type="text" name="u_id"/><br/>
			 	<label>비밀번호</label>
				<input type="text" name='u_password'><br/>
				<label>이름</label>
				<input type="text" name='u_name'><br/>
				<button type="button" class="btn btn-default" data-dismiss="modal">등록</button>
			 </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>	
	
</body>
</html>