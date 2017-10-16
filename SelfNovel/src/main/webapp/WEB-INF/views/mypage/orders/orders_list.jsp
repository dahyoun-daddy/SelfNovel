<%@page import="com.sn.common.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%
	request.setCharacterEncoding("utf-8");
%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	int bottomCount = 10;
	String PAGE_SIZE = "10";
	String PAGE_NUM = "1";
	int totalCnt = 0; //총글수

	PAGE_SIZE = StringUtil.nvl(request.getAttribute("PAGE_SIZE").toString(), "10");
	PAGE_NUM = StringUtil.nvl(request.getAttribute("PAGE_NUM").toString(), "1");

	int oPage_size = Integer.parseInt(PAGE_SIZE);
	int oPage_num = Integer.parseInt(PAGE_NUM);

	totalCnt = Integer.parseInt(StringUtil.nvl(request.getAttribute("TOTALCNT").toString(), "0"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/timeline.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
	//paiging 이동
	function do_search_page(url, PAGE_NUM) {
		console.log(url + "\t" + PAGE_NUM);
		var testfrm = document.testfrm;
		testfrm.PAGE_NUM.value = PAGE_NUM;
		testfrm.action = url;
		testfrm.submit();
	}

	function doSearch() {
		var testfrm = document.testfrm;
		testfrm.PAGE_NUM.value = "1";
		testfrm.action = "do_search.do";
		testfrm.submit();
	}
	
	function do_detail(rsm_id, u_id){
		$.ajax({
            url: "/controller/expert/do_detail.do",
            data: {u_id: u_id,
            	   rsm_id: rsm_id
            	  },
            type: 'POST',
            dataType: "json",
            success: function(results){
            	var html = "<br><table style='width: 100%;'><tr><td style='color: black; background-color: #D8572A; text-align:center;'><h1>원본 내용</h1></td></tr></table>";
            	var flag = 0;
            	for(var i in results){
            		if(results[i].itm_prd_id == null){
            			html += "<br><table style='width: 100%;'><tr><td style='color: black; width: 100%;'><input style='width: 100%;' type='text' readonly value='"+results[i].itm_title+"'></td></tr>";
            			html += "<tr><td style='color: black; width: 100%; height: 10%;'><textarea readonly style='width: 100%; resize: none;'>"+results[i].itm_content+"</textarea></td></tr></table>";
            		} else {
            			if(flag == 0){
            				html += "</table><br><table style='width: 100%;'><tr><td style='color: black; width: 100%; background-color: #F7B538; text-align: center;'><h1>첨삭 내용</h1></td></tr><table>";
            				flag++;
            			}
            			html += "<br><table style='width: 100%; background-color:#F7B538;'><tr><td style='color: black; width: 100%;'><input style='width: 100%;' type='text' readonly value='"+results[i].itm_title+"'></td></tr>";
            			html += "<tr><td style='color: black; width: 100%; height: 10%;'><textarea readonly style='width: 100%; resize: none;'>"+results[i].itm_content+"</textarea></td></tr></table>";
            		}
            	}
            	$("#detailModalBody").append(html);
            	$('#detailModal').modal({backdrop: 'static', keyboard: false});	
            }
        });
	}

	function cancelBtn(){
		$("#detailModalBody").html('');
		$('#detailModal').modal('hide');
	}
	$(function() {

		/********************************************************/
		/* 상태변경 버튼 이벤트 */
		/********************************************************/
		//2017-09-22
		//상태변경 쿼리 테스트입니다. 버튼을 클릭하면 해당 row의 정보를 긁어와 던지도록 만들었으니 나중에 view만들때도 재활용하면 될것같습니다.
		function setFormValue(element) {
			var record = $(element).parents("#ordersInfoTr");
			var exp_id = $(record).find('input[name=exp_id]').val();		
			var rsm_id = $(record).find('input[name=rsm_id]').val();

			$("#EXP_ID").val(exp_id);
			$("#RSM_ID").val(rsm_id);

			var testfrm = document.testfrm;
			testfrm.submit();
		}

		//수락의 경우
		$("input[name=signTest]").click(function() {
			$("#WORK_DIV").val("do_nextState");
			setFormValue(this);
		});
		//거절의 경우
		$("input[name=rejectTest]").click(function() {
			$("#WORK_DIV").val("do_reject");
			setFormValue(this);
		});
		//삭제의 경우
		$("input[name=deleteTest]").click(function() {
			$("#WORK_DIV").val("do_delete");
			setFormValue(this);
		});

		/********************************************************/
		/* 삭제 이벤트 */
		/********************************************************/
		//선택삭제
		$("#doDeleteList").click(function() {
			//체크박스 리스트 갖고온다.
			var userArray = new Array();
			$(":checkbox[name='chkList']:checked").each(function(idx, row) {
				// element == this
				var record = $(row).parents("tr");
				var id = $(record).find('input[name=rsm_id]').val();

				console.log(id);
			});
		});

		/********************************************************/
		/* allCheck */
		/********************************************************/
		$('#allCheck').on('change', function() {
			$("input[name='chkList']").prop('checked', this.checked);
		});
		
		/********************************************************/
		/* 첨삭 이벤트 */
		/********************************************************/
		//원본조회//일반회원
		$('input[name=doSearchOrigin]').on('click', function() {
			var record = $(this).parents("#ordersInfoTr");
			var exp_id = $(record).find('input[name=exp_id]').val();		
			var rsm_id = $(record).find('input[name=rsm_id]').val();
			
			do_detail(rsm_id,exp_id);
		});
		
		//첨삭조회//일반회원
		$('#doSearchEdit').on('click', function() {
		});
		
		//첨삭하기//전문가
		$('#doEdit').on('click', function() {
		});
	});
</script>
</head>
<body>
	<form action="workdiv.do" method="get" name="testfrm">
		<input type="hidden" id="WORK_DIV" name="WORK_DIV" />
		<input type="hidden" id="EXP_ID" name="EXP_ID" />
		<input type="hidden" id="RSM_ID" name="RSM_ID" />
		<input type="hidden" name="PAGE_NUM" id="PAGE_NUM" value="<%=PAGE_NUM%>">
		<input type="hidden" name="file_nm" id="file_nm">
	</form>


	<c:choose>
		<c:when test="${u_level eq '1'}">
		<!-- 일반 회원인 경우 -->
			<table id="userTable"
				class="table table-bordered table-hover table-condensed">
				<thead>
					<tr>
						<th class="text-center"><input id="allCheck" type="checkbox" /></th>
						<th class="text-center">번호</th>
						<th class="text-center">제목</th>
						<th class="text-center">전문가</th>
						<th class="text-center">작성일</th>
						<th class="text-center">원본</th>
						<th class="text-center">첨삭</th>
						<th class="text-center">상태</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${ordersList.size()>0}">
							<c:forEach var="orders" items="${ordersList}" begin="0">
								<tr id="ordersInfoTr">
									<input type="hidden" name="rsm_id" value="${orders.rsm_id}" />
									<input type="hidden" name="exp_id" value="${orders.exp_id}" />
									<td class="text-center"><input type="checkbox" name="chkList" /></td>
									<td class="text-center"><c:out value="${orders.no}" /></td>
									<td class="text-right"><c:out value="${orders.rsm_title}" /></td>
									<td class="text-right"><c:out value="${orders.exp_id}" /></td>
									<td class="text-center"><c:out value="${orders.ord_reg_dt}" /></td>
									<td class="text-right"><input type="button" name="doSearchOrigin" value="원본조회"></td>
									<td class="text-right">
										<c:choose>
											<c:when test="${orders.ord_state eq 40 || orders.ord_state eq 50 }">
												<input type="button" name="doSearchEdit" value="첨삭조회">
											</c:when>
											<c:otherwise>
												<input type="button" name="doSearchEdit" value="첨삭조회" disabled='disabled'>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="text-right">
										<c:choose>
												<c:when test="${orders.ord_state eq 10}">
													<input type='button' value='거절' disabled='disabled' />
												</c:when>
												<c:when test="${orders.ord_state eq 20}">
													<input type='button' value='수락 대기중' disabled='disabled' />
												</c:when>
												<c:when test="${orders.ord_state eq 30}">
													<input type='button' value='첨삭 대기중' disabled='disabled' />
												</c:when>
												<c:when test="${orders.ord_state eq 40}">
													<input type='button' name='signTest' value='확정'/>
												</c:when>
												<c:when test="${orders.ord_state eq 50}">
													<input type='button' value='의뢰 완료' disabled='disabled' />
												</c:when>
											</c:choose>
										</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							리스트가 없습니다.
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<input type='button' id='doDeleteList' value='선택 삭제' />	 
			 
			<div class="form-inline text-center ">
				<%=StringUtil.renderPaging(totalCnt, oPage_num, oPage_size, bottomCount, "pagelist.do", "do_search_page")%>
			</div>		

			<!-- 원본조회 모달 -->
			<div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-hidden="true">
				<div class="vertical-alignment-helper">
					<div class="modal-dialog vertical-align-center">
						<div class="modal-content panel-info %>" >
							<div class="modal-header panel-heading">
								<h4 class="modal-title">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="cancelBtn();">
					          		×
					        		</button>
									<b>첨삭 이력 조회</b>
								</h4>
							</div>
							<div class="modal-body" id="detailModalBody" align="center">
								
							</div>
							<!-- Modal Footer -->
				            <div class="modal-footer">
					            	<button class="btn btn-danger pull-right" onclick="cancelBtn();">
					                    	닫기
					                </button>
				            </div>
						</div>
					</div>
				</div>
			</div>
		</c:when>
		<c:when test="${u_level eq '2'}">
		<!-- 전문가 회원인 경우 -->
			<table id="userTable"
				class="table table-bordered table-hover table-condensed">
				<thead>
					<tr>
						<th class="text-center"><input id="allCheck" type="checkbox" /></th>
						<th class="text-center">번호</th>
						<th class="text-center">제목</th>
						<th class="text-center">의뢰인</th>
						<th class="text-center">작성일</th>
						<th class="text-center">첨삭</th>
						<th class="text-center">상태</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${ordersList.size()>0}">
							<c:forEach var="orders" items="${ordersList}" begin="0">
								<tr id="ordersInfoTr">
									<input type="hidden" name="rsm_id" value="${orders.rsm_id}" />
									<input type="hidden" name="exp_id" value="${orders.exp_id}" />
									<td class="text-center"><input type="checkbox" name="chkList" /></td>
									<td class="text-center"><c:out value="${orders.no}" /></td>
									<td class="text-right"><c:out value="${orders.rsm_title}" /></td>
									<td class="text-center"><c:out value="${orders.u_id}" /></td>
									<td class="text-right"><c:out value="${orders.ord_reg_dt}" /></td>
									<td class="text-right"><input type="button" id="doEdit" value="첨삭하기"></td>
									<td class="text-right">
										<c:choose>
												<c:when test="${orders.ord_state eq 10}">
													<input type='button' name='rejectTest' value='거절' disabled='disabled' />
												</c:when>
												<c:when test="${orders.ord_state eq 20}">
													<input type='button' name='signTest' value='수락' />
													<input type='button' name='rejectTest' value='거절' />
												</c:when>
												<c:when test="${orders.ord_state eq 30}">
													<input type='button' name='signTest' value='첨삭 완료' />
												</c:when>
												<c:when test="${orders.ord_state eq 40}">
													<input type='button' name='signTest' value='첨삭 완료' disabled='disabled' />
												</c:when>
												<c:when test="${orders.ord_state eq 50}">
													<input type='button' name='signTest' value='의뢰 완료' disabled='disabled' />
												</c:when>
											</c:choose>
										</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							리스트가 없습니다.
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<input type='button' id='doDeleteList' value='선택 삭제' />	 
			 
			<div class="form-inline text-center ">
				<%=StringUtil.renderPaging(totalCnt, oPage_num, oPage_size, bottomCount, "pagelist.do", "do_search_page")%>
			</div>				
		</c:when>		
	</c:choose>
</body>
</html>