<%@ page import="com.sn.common.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<jsp:useBean id="toDay" class="java.util.Date" />

<%
	//contextPath
	String contextPath = request.getContextPath();
	contextPath = "http://localhost:8080/"+contextPath;
%>	
<%

	/**************************
	* 페이징 및 검색 버튼 처리를 위한 세팅  *
	***************************/
	int    bottomCount = 10;//하단에 표시될 페이지 수
	String searchDiv = "";	//검색구분
	String searchWord = "";	//검색어
	String page_size = "10";//한 페이지에 표시될 글의 수
	String page_num = "1";	//페이지 번호
	int    totalCnt = 0;	//총글수

	//request로부터 검색어, 검색구분, 페이지 등의 값을 초기화
	searchDiv  = StringUtil.nvl(request.getParameter("searchDiv"), "");
	searchWord = StringUtil.nvl(request.getParameter("searchWord"), "");	
	page_size  = StringUtil.nvl(request.getParameter("page_size"), "10");
	page_num   = StringUtil.nvl(request.getParameter("page_num"), "1");
	totalCnt   = Integer.parseInt(StringUtil.nvl(request.getAttribute("totalCnt").toString(), "0"));

	//page_size 및 page_num을 Integer값으로 넘기기 위한 캐스팅
	int oPage_size = Integer.parseInt(page_size);
	int oPage_num = Integer.parseInt(page_num);
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- JQuery CDN -->
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<!-- BootStrap CDN -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">


<script type="text/javascript">

	/**************************
	* 페이지네이션 담당 함수                     *
	***************************/
	function do_search_page(url, page_num){
	    console.log(url +"\t"+ page_num);
	    var frm = document.frm;
	    frm.page_num.value = page_num;
	    frm.action = url;
	    frm.submit();
	}
	
	/**************************
	* 검색 담당 함수                                 *
	***************************/
	function do_search(){
		var param = "";	
		$("input[name=categoryChk]:checked").each(function() {
			if(param == ""){
				param = $(this).val();
			}else{
				param = param + "&" + $(this).val();	
			}//close if
		});//close each
		
		$("#page_num").val("1");
		$("#search_category").val(param);
		
			var frm = document.frm;
		frm.action = "do_search.do";
		frm.submit();
	}
	
	/**************************
	* Jquery ready            
	***************************/
	$(document).ready(function(){	
		
		/*******************
		* 체크박스 유지 처리
		********************/
		var searchCat = $("#search_category").val();
		var chkCat = searchCat.split("&");		
		$(chkCat).each(function(index, item){
			$("input[name=categoryChk]:checkbox").each(function(cIndex, cItem){				
				if(this.value == item){
					this.checked = true;
				}
			});
		});//close chkCat_each		
		
		/*******************
		* 셀렉트박스 유지 처리
		********************/
		$("#searchDiv > option[value=" + ${searchDiv} + "]").attr("selected", true);
		
		/**************************
		* '검색'버튼 클릭시 이벤트
		***************************/
		$("#btn_search").on("click", function(){
			do_search();
		});//close btn_search_on_click
		
		/**************************
		* '검색구분' 변경시 이벤트
		***************************/
		$("#searchDiv").on("change", function(){
			do_search();
		});//close searchDiv_on_change
		
		/**************************
		* '글쓰기'버튼 클릭시 이벤트 : TODO
		***************************/
		$("#btn_write").on("click", function(){
			$(location).attr('href',"do_save.do");		
		});//close btn_write_on_click
		
	});//close document ready
	
</script>

<title>Insert title here</title>

<style type="text/css">
	.table-head-rows {
		text-align: center;
		background-color: #4f81bd;
		color: #FFF;
		font-size: large;
	}
	
	.table_body_rows {
		font-size: medium;
		margin: auto;				
	}	
</style>

</head>
<body>
	<h2>자기소개서</h2>
	<hr/>
	<form action="#" name="frm" method="post" class="form-inline">		
		<input type="hidden" name="page_num" id="page_num" value="<%=page_num %>">		
		<input type="hidden" name="search_category" id="search_category" value="${searchCat}">
		<table class="table table-bordered table-hover table-condensed" border="1px" 
			   cellpadding="2" cellspacing="2" align="center" width="600px;">
			<tr>
				<td style="text-align: center;">분야</td>
				<td>
					<c:forEach var="codeVo" items="${codeList}" varStatus="status">					
						<input type="checkbox" name="categoryChk" value="${status.index}">${codeVo.dtl_cd_nm }
					</c:forEach>				
					<!-- <input type="checkbox" name="categoryChk" value="1">서비스업
					<input type="checkbox" name="categoryChk" value="2">제조·화학
					<input type="checkbox" name="categoryChk" value="3">의료·제약·복지
					<input type="checkbox" name="categoryChk" value="4">판매·유통
					<input type="checkbox" name="categoryChk" value="5">교육업
					<input type="checkbox" name="categoryChk" value="6">건설업
					<input type="checkbox" name="categoryChk" value="7">IT·웹·통신
					<input type="checkbox" name="categoryChk" value="8">미디어·디자인
					<input type="checkbox" name="categoryChk" value="9">은행·금융업
					<input type="checkbox" name="categoryChk" value="10">기관·협회 -->
				</td>				
			</tr>
			<tr>
				<td style="text-align: center;">검색</td>
				<td>
					<input type="text" id="searchWord" name="searchWord" value="${searchWord}"/>
					<button type="button" id="btn_search">검색</button>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">총 ${totalCnt}건이 검색되었습니다.</td>
			</tr>
		</table> 
		<hr/>
		<div align="right">
			<select class="form-control input-sm" id="searchDiv" name="searchDiv">
				<option value="10">정렬기준</option>
				<option value="10">최신순</option>
				<option value="20">조회순</option>
				<option value="30">추천순</option>
			</select>
		</div>
		<table class="table table-bordered table-hover table-condensed" border="1px" 
			   cellpadding="2" cellspacing="2" align="center" width="600px;">
			<thead>
				<tr>
					<th style="text-align: center;" colspan="7">공지사항</th>
				</tr>
				<tr>
					<th colspan="7">..........</th>
				</tr>
				<tr>
					<th class="table-head-rows">글번호</th>
					<th class="table-head-rows">카테고리</th>
					<th class="table-head-rows">작성자</th>
					<th class="table-head-rows">제목</th>
					<th class="table-head-rows">작성일</th>
					<th class="table-head-rows">조회수</th>
					<th class="table-head-rows">추천수</th>
				</tr>
			</thead>
			<tbody>
				<!-- c:choose 게시물 List를 출력하는 부분입니다.-->
				<c:choose>
		            <c:when test="${list.size()>0}" >
		                <c:forEach var="rsmVo" items="${list}">
							<tr>	        
								<!-- 글 번호 -->        	
								<td class="text-center"><c:out value="${rsmVo.no}"/></td>
								<!-- 카테고리 -->
								<td class="text-center">
									<c:forEach var="codeVo" items="${codeList}" varStatus="status">
										<c:if test="${rsmVo.rsm_div eq status.index }">
											<c:out value="${codeVo.dtl_cd_nm }"></c:out>
											<%-- <c:out value="${rsmVo.rsm_div }"/> --%>
										</c:if>
									</c:forEach>
								</td>
								<!-- 작성자 -->
								<td class="text-center"><c:out value="${rsmVo.u_name}"/></td>					
								
								<!-- 제목 -->								
								<td class="text-left" >
									<a href="do_searchOne.do?rsm_id=${rsmVo.rsm_id}">
										<c:out value="${rsmVo.rsm_title}"/>
									</a>					
										
									<!-- 게시물 작성날짜 -->
									<c:set var="temp" value="${rsmVo.rsm_reg_dt }" />
									<fmt:parseDate value="${temp}" pattern="yyyy-MM-dd HH:mm:SS" var="reg_dt" />
									<fmt:formatDate value="${reg_dt }" pattern="yyyy-MM-dd" var="f_reg_dt" />
									<fmt:formatDate value="${reg_dt }" pattern="HH:mm" var="f_reg_tm"/>																	
								
									<!-- 오늘날짜 -->
									<fmt:formatDate value="${toDay}" pattern="yyyy-MM-dd" var="now"/>
														
									<!-- 작성일이 오늘날짜면 제목 뒤에 New표시 라벨 -->																			
									<c:if test="${f_reg_dt eq now}">
										<span class="label label-warning">New</span>										
									</c:if>									
								</td>
								
								<!-- 작성일 -->
								<td class="text-center">
									<!-- 작성일이 오늘이면 시간만 표시, 그 외는 날짜만 표시 -->								
									<c:choose>										
										<c:when test="${f_reg_dt eq now}">											
											<c:out value="${f_reg_tm }"></c:out>
										</c:when>
										<c:otherwise>
											<c:out value="${f_reg_dt}"/>
										</c:otherwise>
									</c:choose>
								</td>
								<!-- 조회수 -->
								<td class="text-center"><c:out value="${rsmVo.rsm_count}"/></td>
								<!-- 추천수 -->
								<td class="text-center"><c:out value="${rsmVo.rsm_recommend}"/></td>
							</tr>       
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr >
							<td colspan="99">등록된 게시글이 없습니다.</td>
		                </tr>    
		            </c:otherwise>
	        	</c:choose>	        	
	        	<!-- //c:choose -->
			</tbody>
		</table>
	</form>
	<div>
		<div style="text-align:right;">
			<!-- 로그인한 상태에서만 글쓰기 버튼이 보이도록 -->
			<c:if test="${sessionScope.u_id ne null}">
				<button type="button" id="btn_write">글쓰기</button>
			</c:if>
		</div>
	</div>
	
	<div class="form-inline text-center ">
		<%=StringUtil.renderPaging(totalCnt, oPage_num, oPage_size, bottomCount, "do_search.do", "do_search_page") %>
	</div> 

</body>
</html>