<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("utf-8"); %>
<%
	String u_id = null;
	String u_name = null;
	int u_level = 0;
	
	if(request.getParameter("u_id") != null) {
		u_id = request.getParameter("u_id");
		u_name = request.getParameter("u_name");
		u_level = Integer.parseInt(request.getParameter("u_level"));
	}
%>
<script type="text/javascript">
	function logout(){
		location.reload();
	}
</script>
<div>
	<table align="left">
   		<tr>
   			<td>
   				<input type="text" />
   			</td>
   			<td>
   				<input type="button" value="검색" />
   			</td>
   		</tr>
	</table>
	<table align="right">
		<% if(u_id == null){ %>
   		<tr>
   			<td>
   				<form action="register_user.do" method="POST">
  					<input class="btn btn-success" type="submit" value="회원가입">
				</form>
   			</td>
   			<td>
   				<form action="login_user.do" method="POST">
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
   				<form action="update_user.do" method="POST">
  					<input class="btn btn-success"type="submit" value="내 정보 수정">
				</form>
   			</td>
   			<td>
   				<form action="login_user.do" method="POST">
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
   				<form action="" method="POST">
  					<input class="btn btn-success"type="submit" value="관리자 페이지">
				</form>
   			</td>
   			<td>
   				<form action="login_user.do" method="POST">
  					<input class="btn btn-success"type="submit" value="로그아웃" onclick="logout()">
				</form>
   			</td>
   		</tr>
   		<% } %>
	</table>
</div>
