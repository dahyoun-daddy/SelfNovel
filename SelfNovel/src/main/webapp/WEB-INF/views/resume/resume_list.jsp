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

	//request로부터 default값 세팅
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

<!-- BootStrap CDN -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script type="text/javascript">
	//paiging 이동
	function do_search_page(url, page_num){
	    console.log(url +"\t"+ page_num);
	    var frm = document.frm;
	    frm.page_num.value = page_num;
	    frm.action = url;
	    frm.submit();
	}
</script>

<title>Insert title here</title>
</head>
<body>
	<h2>자기소개서</h2>
	<hr/>
	<form action="#" name="frm" method="post" class="form-inline">
		<input type="hidden"  name="page_num" id="page_num" value="<%=page_num %>">
		<table class="table table-bordered table-hover table-condensed" border="1px" 
			   cellpadding="2" cellspacing="2" align="center" width="600px;">
			<tr>
				<td style="text-align: center;">분야</td>
				<td>
					<input type="checkbox">유통·무역</input>
					<input type="checkbox">영업고객상담</input>
					<input type="checkbox">IT인터넷</input>
					<input type="checkbox">건설</input><br/>
					<input type="checkbox">생산·제조</input>
					<input type="checkbox">경영사무</input>
					<input type="checkbox">전문직</input>
					<input type="checkbox">서비스</input><br/>
					<input type="checkbox">디자인</input>
					<input type="checkbox">미디어</input>
					<input type="checkbox">의료</input>
					<input type="checkbox">교육</input>
					<input type="checkbox">특수계층·공금</input>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">검색</td>
				<td>
					<input type="text" />
					<input type="submit" value="검색"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">총 n건이 검색되었습니다.</td>
			</tr>
		</table> 
		<hr/>
		<div align="right">
			<select class="form-control input-sm">
				<option value="">정렬기준</option>
				<option value="최신순">최신순</option>
				<option value="조회순">조회순</option>
				<option value="추천순">추천순</option>
			</select>
		</div>
		<table class="table table-bordered table-hover table-condensed" border="1px" 
			   cellpadding="2" cellspacing="2" align="center" width="600px;">
			<thead>
				<tr>
					<th style="text-align: center;" colspan="6">공지사항</th>
				</tr>
				<tr>
					<th colspan="6">..........</th>
				</tr>
				<th style="text-align: center;">글번호</th>
				<th style="text-align: center;">작성자</th>
				<th style="text-align: center;">제목</th>
				<th style="text-align: center;">작성일</th>
				<th style="text-align: center;">조회순</th>
				<th style="text-align: center;">추천순</th>
			</thead>
			<tbody>
			<c:choose>
            <c:when test="${list.size()>0}" >
                <c:forEach var="rsmVo" items="${list}">
		                <tr>	                	
		                    <td class="text-center"><c:out value="${rsmVo.no}"/></td>
		                    <td class="text-left"><c:out value="${rsmVo.u_name}"/></td>
		                    <td class="text-left"><c:out value="${rsmVo.rsm_title}"/></td>
		                    <td class="text-center"><c:out value="${rsmVo.rsm_reg_dt}"/></td>
		                    <td class="text-right"><c:out value="${rsmVo.rsm_count}"/></td>
		                    <td class="text-right"><c:out value="${rsmVo.rsm_recommend}"/></td>
		                </tr>       
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr >
                    <td colspan="99">등록된 게시글이 없습니다.</td>
                </tr>    
            </c:otherwise>
        </c:choose>
			</tbody>
		</table>
	</form>
	<div>
		<div style="text-align:right;">
			<button type="button" id="btn_write">글쓰기</button>
		</div>
	</div>
	
	<div class="form-inline text-center ">
		<%=StringUtil.renderPaging(totalCnt, oPage_num, oPage_size, bottomCount, "do_search.do", "do_search_page") %>
	</div> 

</body>
</html>