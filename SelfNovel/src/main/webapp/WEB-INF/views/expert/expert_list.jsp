<%@page import="com.sn.common.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
  //contextPath
  String contextPath = request.getContextPath();
  contextPath = "http://localhost:8080/"+contextPath;  
%>
<%
	String searchDiv = "";
	String searchWord = "";
	String searchCategory = "";
	String page_size = "16";
	String page_num = "1";
	int totalNo = 0;
	
	searchDiv = StringUtil.nvl(request.getParameter("searchDiv"), "");
	searchWord = StringUtil.nvl(request.getParameter("searchWord"), "");
	searchCategory = StringUtil.nvl(request.getParameter("searchCategory"), "");
	page_size = StringUtil.nvl(request.getParameter("page_size"), "16");
	page_num = StringUtil.nvl(request.getParameter("page_num"), "1");
	
	int oPage_size = Integer.parseInt(page_size);
	int oPage_num = Integer.parseInt(page_num);
	
	if(request.getAttribute("totalNo") != null){
		totalNo = Integer.parseInt(request.getAttribute("totalNo").toString());
	} else {
		totalNo = 0;
	}
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link href="<c:url value='/resources/css/layout.css' />" rel="stylesheet"></link>
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var searchCategoryNum_t = '${searchCategoryNum}';
		var searchWord = '<%=request.getAttribute("searchWord")%>'+'';
		var arr = searchCategoryNum_t.split("|");
		var i=1;
		
		if(searchWord != null && searchWord != ""){
			$("#searchWord_1").val('${searchWord}'+"");
		}
		$('input:checkbox[id="searchCategory_1"]').each(function() {
		     if(this.value == arr[i]){
		            this.checked = true;
		            i++;
		      }
		 })
		
		$("#searchDiv_1").change(function(){
	           do_search();
		})
		
		$("#searchDiv_1").val('${searchDiv}');
	});
	
	function do_search(){
		var searchCategory = "";
		var searchCategoryNum = "|";
		
		$('input:checkbox[id="searchCategory_1"]').each(function() {
		      if(this.checked){
		            searchCategory += "exp_ctg = " + this.value + " OR ";
		            searchCategoryNum += this.value + "|";
		      }
		 });
		if(searchCategory != ""){
			searchCategory = "AND (" + searchCategory.substr(0,searchCategory.length-4) + ")";
		}
		
		$("#searchDiv").val($("#searchDiv_1").val());
		$("#searchWord").val($("#searchWord_1").val());
		$("#searchCategory").val(searchCategory);
		$("#searchCategoryNum").val(searchCategoryNum);
		searchFrm.submit();
	}
	
	function do_search_page(url, PAGE_NUM) {
		var searchCategory = "";
		var searchCategoryNum = "|";
		
		$('input:checkbox[id="searchCategory_1"]').each(function() {
		      if(this.checked){
		            searchCategory += "exp_ctg = " + this.value + " OR ";
		            searchCategoryNum += this.value + "|";
		      }
		 });
		if(searchCategory != ""){
			searchCategory = "AND (" + searchCategory.substr(0,searchCategory.length-4) + ")";
		}
		
		$("#searchDiv").val($("#searchDiv_1").val());
		$("#searchWord").val($("#searchWord_1").val());
		$("#searchCategory").val(searchCategory);
		$("#searchCategoryNum").val(searchCategoryNum);
		$("#page_num").val(PAGE_NUM);
		searchFrm.submit();
	}
</script>
<title>:::전문가 조회:::</title>
</head>
<body>
	<h2>전문가 조회</h2>
	<hr/>
	<form action="do_search.do" id="searchFrm" name="searchFrm" method="post" class="form-inline">
		<input type="hidden" id="page_num" name="page_num" value="">
		<input type="hidden" id="searchDiv" name="searchDiv" value="">
		<input type="hidden" id="searchWord" name="searchWord" value="">
		<input type="hidden" id="searchCategory" name="searchCategory" value="">
		<input type="hidden" id="searchCategoryNum" name="searchCategoryNum" value="">
		<table class="table table-bordered table-hover table-condensed" border="1px" 
			   cellpadding="2" cellspacing="2" align="center" width="960px;">
			<tr>
				<td>
					<h5 align="center">전문가 랭킹</h5>
				</td>	
			</tr>
			<hr/>
			<tr>
				<td>
				<div>
					<% int i=0; String str; %>
						<c:choose>
            				<c:when test="${rank_list.size()>0}" >
                				<c:forEach var="expertVO" items="${rank_list}">
									<table class="table table-bordered table-hover table-condensed" style="width:33%;" border="1px" 
						   				   cellpadding="2" cellspacing="2" align="left">
										<tr>
											<td colspan="2" align="center">
													<% i++; str = "/resources/img/rank"+String.valueOf(i)+".png"; %>
													<img src="<c:url value='<%=str %>' />" />
												<a href="do_detail_list.do?exp_id=${expertVO.exp_id }">
													<c:set var="d" value="${expertVO.exp_profile }"/>
													<c:set var="e" value="${fn:substring(d,0,7) }"/>
													<c:choose>
														<c:when test="${e eq 'http://'}">
															<img style="position:relative; top:0; left:0;" src="${expertVO.exp_profile}" width="200px" height="200px">	
														</c:when>
														
														<c:otherwise>
															<img style="position:relative; top:0; left:0;" src="/controller/resources/exp_profiles/${expertVO.exp_profile}" width="200px" height="200px">															
														</c:otherwise>
													</c:choose>													
												</a>
											</td>
										</tr>
										<tr>
											<td style="text-align: center;">제목</td>
											<td>
												<div align="center"><c:out value="${expertVO.exp_title}"/></div>
											</td>
										</tr>
										<tr>
											<td style="text-align: center;">전문분야</td>
											<td align="center">
												<c:out value="${expertVO.dtl_cd_nm}"/>
											</td>
										</tr>
									</table>
						</c:forEach>
					</c:when>
				</c:choose>
				</div>
				</td>
			</tr>
		</table>
		<table class="table table-bordered table-hover table-condensed" border="1px" 
			   cellpadding="2" cellspacing="2" align="center" width="960px;">
			<tr>
				<td colspan="2" style="text-align: center;">분야</td>
			</tr>
			<tr>				
				<td colspan="2">
					<div align="center">
					<ul>											
					<c:forEach var="codeVo" items="${codeList}" varStatus="status">						
						<div align="left" style="width: 250px; float: left">
							<label>
								<input type="checkbox" id="searchCategory_1" value="${status.index}">
								${codeVo.dtl_cd_nm }
							</label>
						</div>						
						<%-- <c:set var="remain" value="${status.count % 5 }"></c:set>
						<c:if test="${remain eq 0 }">
							<br>
						</c:if>			 --%>																						
					</c:forEach>
					</ul>					
					</div>
				</td>								
			</tr>
			<tr>
				<td style="text-align: center;">검색</td>
				<td>
					<input id="searchWord_1" type="text" onkeydown="if(event.keyCode == 13) document.getElementById('searchBtn').click()" />
					<input id="searchBtn" class="btn btn-success" type="button" value="검색" onclick="do_search()"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">총 <c:out value="${list.size()}"/>건이 검색되었습니다.</td>
			</tr>
		</table> 
		<hr/>
		<div align="right">
			<select id="searchDiv_1" class="form-control input-sm" style="text-align: right;">
				<option value="">정렬기준</option>
				<option value="1">거래완료 건수</option>
				<option value="2">최신순</option>
				<option value="3">가격순</option>
			</select>
		</div>
		<br/>
		<table class="table table-bordered table-hover table-condensed" style="width:100%;" border="1px" 
			   cellpadding="2" cellspacing="2" align="center">
			<tr>
				<td>
					<div>
						<c:choose>
            				<c:when test="${list.size()>0}" >
            					<div class="container-fluid">
            					<div class="row">
                				<c:forEach var="expertVO" items="${list}">
                				<!-- 
								<table class="table table-bordered table-hover table-condensed" style="width:25%;" border="1px" 
						   				   cellpadding="2" cellspacing="2" align="left">
										<tr>
											<td colspan="2" align="center">
												<a href="do_detail_list.do?exp_id=${expertVO.exp_id }">
													<c:set var="d" value="${expertVO.exp_profile }"/>
													<c:set var="e" value="${fn:substring(d,0,7) }"/>
													<c:choose>
														<c:when test="${e eq 'http://'}">
															<img style="position:relative; top:0; left:0;" src="${expertVO.exp_profile}" width="200px" height="200px">	
														</c:when>
														<c:otherwise>
															<img style="position:relative; top:0; left:0;" src="/controller/resources/exp_profiles/${expertVO.exp_profile}" width="200px" height="200px">															
														</c:otherwise>
													</c:choose>
												</a>
											</td>
										</tr>
										<tr>
											<td style="text-align: center;">제목</td>
											<td>
												<c:out value="${expertVO.exp_title}"/>
											</td>
										</tr>
										<tr>
											<td style="text-align: center;">전문분야</td>
											<td align="center">
												<c:out value="${expertVO.dtl_cd_nm}"/>
											</td>
										</tr>
										<tr>
											<td style="text-align: center;">가격</td>
											<td style="text-align: right">
												₩ <fmt:formatNumber value="${expertVO.exp_price}" pattern="#,###"/>
											</td>
										</tr>
									</table>
									 -->

									  <div class="col-md-6 col-lg-3">
									    <div class="thumbnail" style='position:relative; height:400px;'>
										    <c:set var="d" value="${expertVO.exp_profile }"/>
											<c:set var="e" value="${fn:substring(d,0,7) }"/>
											<c:choose>
												<c:when test="${e eq 'https:/'}">
													<img style="position:relative; top:0; left:0;" src="${expertVO.exp_profile}" width="200px" height="200px">	
												</c:when>
												<c:otherwise>
													<img style="position:relative; top:0; left:0;" src="/controller/resources/exp_profiles/${expertVO.exp_profile}" width="200px" height="200px">															
												</c:otherwise>
											</c:choose>
									      <div class="caption">
									        <h3><c:out value="${expertVO.exp_title}"/></h3>
									       	<p><c:out value="${expertVO.dtl_cd_nm}"/></p>							       	

									       	
									       	<div style="position:absolute; right:10px; bottom:50px;">	
									       		<p>₩ <fmt:formatNumber value="${expertVO.exp_price}" pattern="#,###"/></p>
									       	</div>
									        <div style="position:absolute; right:10px; bottom:0px;">
									        	<p><a href="do_detail_list.do?exp_id=${expertVO.exp_id }" class="btn btn-primary" role="button"> 첨삭 조회 </a></p>
									      	</div>
									      </div>
									    </div>
									  </div>	
						</c:forEach>
						</div>
						</div>
						</c:when>
						</c:choose>
					</div>
				</td>
			</tr>
		</table>
	</form>
	<div class="form-inline text-center ">
		<%=StringUtil.renderPaging(totalNo, oPage_num, oPage_size, 10, "do_search.do", "do_search_page") %>
	</div>
</body>
</html>