<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">

	$(function() {
		$("input[name=levelUpTest]").click(function(){
			//2017-09-22
			//상태변경 쿼리 테스트입니다. 버튼을 클릭하면 해당 row의 정보를 긁어와 던지도록 만들었으니 나중에 view만들때도 재활용하면 될것같습니다.
			var record = $(this).parents("tr");
			console.log(record);
			
			var exp_id = $(record).find('td').eq(2).text();
			var rsm_id = $(record).find('input[name=rsm_id]').val();
			
			console.log(exp_id);
			console.log(rsm_id);
			
			$("#exp_id").val(exp_id);
			$("#rsm_id").val(rsm_id);
			
			var testfrm = document.testfrm;
			testfrm.submit();
		});
	});

</script>
</head>
<body>
		<form action="levelUpTest.do" method="post" name="testfrm">
			<input type="hidden" id="exp_id" name="exp_id"/>
			<input type="hidden" id="rsm_id" name="rsm_id"/>
		</form>


		<table id="userTable" class="table table-bordered table-hover table-condensed">
			<thead>
				<tr>
					<th class="text-center">번호</th>
					<th class="text-center">제목</th>
					<th class="text-center">전문가</th>
					<th class="text-center">유저</th>
					<th class="text-center">상태</th>
					<th class="text-center">상태텍스트</th>		
					<th class="text-center">작성일</th>
					<th class="text-center">등급업 테스트</th>
				</tr>							
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${ordersList.size()>0}">
					<c:forEach var="orders" items="${ordersList}" begin="0">
						<tr>
							<input type="hidden" name="rsm_id" value="${orders.rsm_id}"/>
							<td class="text-center"><c:out value="${orders.no}"/></td>
							<td class="text-right"><c:out value="${orders.rsm_title}"/></td>
							<td class="text-right"><c:out value="${orders.exp_id}"/></td>
							<td class="text-center"><c:out value="${orders.u_id}"/></td>
							<td class="text-left"><c:out value="${orders.ord_state}"/></td>
							<td class="text-right"><c:out value="${orders.ord_state_nm}"/></td>
							<td class="text-right"><c:out value="${orders.ord_reg_dt}"/></td>
							<td class="text-right"><input type="button" name="levelUpTest" value="레벨업!"/></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					리스트가 없습니다.
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>	
</body>
</html>