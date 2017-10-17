<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("utf-8"); %>
<%
	String u_id = null;
	String u_name = null;
	int u_level = 0;
	
	if(session.getAttribute("u_id") != null) {
		u_id = session.getAttribute("u_id").toString();
		u_name = session.getAttribute("u_name").toString();
		u_level = Integer.parseInt(session.getAttribute("u_level").toString());
	}
%>
<link href="<c:url value='/resources/css/layout.css' />" rel="stylesheet"></link>
<script type="text/javascript">
	function logout(){
		$.ajax({
	        url: "/controller/user/do_logout.do",
	        type: 'POST',
	        success: function(result){
	        	logoutFrm.submit();
	        }
        });
	}
</script>
<div>
	<table style="width: 100%;">
		<tr>
			<td style="width:15%;">
			</td>
			<td style="width: 35%;">
			<form name="mainSearchFrm" action="/controller/main/do_search.do" method="POST">
				<table align="left">
	   				<tr>
	   					<td>
	   						<input type="text" name="SEARCH_WORD_MAIN"/>
	   					</td>
	   					<td>
	   						<input type="submit" value="검색" />
	   					</td>
		   			</tr>
				</table>
			</form>
			</td>
			<td style="width: 35%;">
				<table style="display:inline-block;" align="right">
					<% if(u_id == null){ %>
			   		<tr>
			   			<td>
			   				<form action="/controller/register_user.do" method="POST">
			  					<input class="btn btn-success" type="submit" value="회원가입">
							</form>
			   			</td>
			   			<td>
			   				<form action="/controller/login_user.do" method="POST">
			  					<input class="btn btn-success"type="submit" value="로그인">
							</form>
			   			</td>
			   		</tr>
			   		<% } else if(u_level != 0){ %>
			   		<tr>
			   			<td>
			   				<label><%=u_name %>님 환영합니다.</label>&nbsp;
			   			</td>
			   			<td>
   			 			  	<form action="/controller/mypage/orders/pagelist.do" method="GET">
  								<input class="btn btn-success"type="submit" value="내 의뢰 페이지">
							</form>
   						</td>
			   			<td>
			   				<form action="/controller/update_user.do" method="POST">
			  					<input class="btn btn-success"type="submit" value="내 정보 수정">
							</form>
			   			</td>
			   			<td>
			   				<form id="logoutFrm" action="/controller/login_user.do" method="POST">
			   					<input class="btn btn-success" type="submit" value="로그아웃" onclick="logout()">
			   				</form>
			   			</td>
			   		</tr>
			   		<% } else if(u_level == 0){ %>
			   		<tr>
			   			<td>
			   				<label>관리자로 로그인 하였습니다.</label>&nbsp;
			   			</td>
			   			<td>
			   				<form action="/controller/mypage/manager/manager_user_list.do" method="GET">
			  					<input class="btn btn-success"type="submit" value="관리자 페이지">
							</form>
			   			</td>
			   			<td>
			   				<form action="/controller/login_user.do" method="POST">
			  					<input class="btn btn-success"type="submit" value="로그아웃" onclick="logout()">
							</form>
			   			</td>
			   		</tr>
			   		<% } %>
				</table>
			</td>
			<td id="right_blank">
			</td>
		</tr>
	</table>
</div>