<%@page import="com.sn.common.OrdersUtil"%>
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
		
		//2017-09-22
		//상태변경 쿼리 테스트입니다. 버튼을 클릭하면 해당 row의 정보를 긁어와 던지도록 만들었으니 나중에 view만들때도 재활용하면 될것같습니다.
		function setFormValue(element){
			var record = $(element).parents("tr");			
			var exp_id = $(record).find('td').eq(3).text();//exp_id의 index
			var rsm_id = $(record).find('input[name=rsm_id]').val();
			
			$("#EXP_ID").val(exp_id);
			$("#RSM_ID").val(rsm_id);
			
			var testfrm = document.testfrm;
			testfrm.submit();
		}
		
		//거절 이외의 경우 다음 상태로 변경
		$("input[name=levelUpTest]").click(function(){
			$("#WORK_DIV").val("do_nextState");
			setFormValue(this);
		});
		//수락의 경우
		$("input[name=signTest]").click(function(){
			$("#WORK_DIV").val("do_nextState");
			setFormValue(this);
		});
		//거절의 경우
		$("input[name=rejectTest]").click(function(){
			$("#WORK_DIV").val("do_reject");
			setFormValue(this);
		});
		//삭제의 경우
		$("input[name=deleteTest]").click(function(){
			$("#WORK_DIV").val("do_delete");
			setFormValue(this);
		});
		
		////////////////////////////////////////////////////////////
		//선택삭제
		$("#doDeleteList").click(function(){			
			//체크박스 리스트 갖고온다.
			var userArray = new Array();
			$(":checkbox[name='chkList']:checked").each(function(idx,row){
				// element == this
				var record = $(row).parents("tr");
				var id = $(record).find('input[name=rsm_id]').val();
				
				console.log(id);
			});	
		});
		
		/********************************************************/
		/* allCheck */
		/********************************************************/		
	   	 $('#allCheck').on('change', function(){
			  $("input[name='chkList']").prop('checked', this.checked);
		 });
	});

</script>
</head>
<body>
		<form action="workdiv.do" method="post" name="testfrm">
			<input type="hidden" id="WORK_DIV" name="WORK_DIV"/>
			<input type="hidden" id="EXP_ID" name="EXP_ID"/>
			<input type="hidden" id="RSM_ID" name="RSM_ID"/>
		</form>


		<table id="userTable" class="table table-bordered table-hover table-condensed">
			<thead>
				<tr>
					<th class="text-center"><input id="allCheck" type="checkbox"/></th>
					<th class="text-center">번호</th>
					<th class="text-center">제목</th>
					<th class="text-center">전문가</th>
					<th class="text-center">유저</th>
					<th class="text-center">상태</th>
					<th class="text-center">상태텍스트</th>		
					<th class="text-center">작성일</th>
					<th class="text-center">등급업 테스트</th>
					<th class="text-center">거절 테스트</th>
					<th class="text-center">삭제 테스트</th>
				</tr>							
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${ordersList.size()>0}">
					<c:forEach var="orders" items="${ordersList}" begin="0">
						<tr>
							<input type="hidden" name="rsm_id" value="${orders.rsm_id}"/>
							<td class="text-center"><input type="checkbox" name="chkList"/></td>
							<td class="text-center"><c:out value="${orders.no}"/></td>
							<td class="text-right"><c:out value="${orders.rsm_title}"/></td>
							<td class="text-right"><c:out value="${orders.exp_id}"/></td>
							<td class="text-center"><c:out value="${orders.u_id}"/></td>
							<td class="text-left"><c:out value="${orders.ord_state}"/></td>
							<td class="text-right"><c:out value="${orders.ord_state_nm}"/></td>
							<td class="text-right"><c:out value="${orders.ord_reg_dt}"/></td>
							<td class="text-right"><input type="button" name="levelUpTest" value="레벨업!"/></td>
							<td class="text-right">
								<c:choose>
									<c:when test="${orders.ord_state eq 10}">
										<input type='button' name='rejectTest' value='거절' disabled='disabled'/>
									</c:when>
									<c:when test="${orders.ord_state eq 20}">
										<input type='button' name='signTest' value='수락'/>
										<input type='button' name='rejectTest' value='거절'/>
									</c:when>
									<c:when test="${orders.ord_state eq 30}">
										<input type='button' name='signTest' value='첨삭 완료'/>
									</c:when>
									<c:when test="${orders.ord_state eq 40}">
										<input type='button' name='signTest' value='첨삭 완료' disabled='disabled'/>
									</c:when>
									<c:when test="${orders.ord_state eq 50}">
										<input type='button' name='signTest' value='의뢰 완료' disabled='disabled'/>
									</c:when>																		
								</c:choose>
							</td>
							<td class="text-right"><input type="button" name="deleteTest" value="삭제"/></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					리스트가 없습니다.
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		<input type='button' id='doDeleteList' value='선택 삭제'/>	
</body>
</html>