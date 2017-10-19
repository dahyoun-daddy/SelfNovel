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
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
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
	});
	
	function do_searchOriginal(rsm_id){
	    var url = 'do_detailOriginal?rsm_id='+rsm_id;
	    var popupX = ((window.screen.width / 2) - (200 / 2)) / 2;
	    window.open(url,'originalPopup','left='+popupX+', width=500, height=800, status=no');
	}
	
	function do_searchRevision(rsm_id){
	    var url = 'do_detailRevision?rsm_id='+rsm_id;
	    var popupX = ((window.screen.width / 2) + (2000 / 2)) / 2;
	    window.open(url,'revisionPopup','left='+popupX+', width=500, height=800, status=no');
	}
	
	function do_complete(rsm_id, exp_id){
		if (confirm("첨삭을 완료하시겠습니까?") == true){
			$.ajax({
	            url: "do_updateUseYN",
	            data: {rsm_id: rsm_id,
	            	   exp_id: exp_id
	            	  },
	            type: 'POST',
	            success: function(result){
	            	if(result == "fail"){
	            		alert("첨삭하지 않으면 완료할 수 없습니다.");
	            		return;
	            	} else{
	            		$("#WORK_DIV").val("do_nextState");
	            		$("#EXP_ID").val('<%=session.getAttribute("u_id")%>');
	        			$("#RSM_ID").val(rsm_id);
	        			testFrm.submit();
	            	}
	            }
	        });
		}
	}
	
	function do_compl(rsm_id, exp_id){
		if (confirm("확정하시겠습니까?") == true){
			$.ajax({
	            url: "/controller/expert/do_updateTrade.do",
	            data: {exp_id: exp_id},
	            type: 'POST',
	            success: function(result){
	            	if(result == "fail"){
	            		alert("확정 실패");
	            		return;
	            	} else{
	            		do_submit(rsm_id,'do_nextState', exp_id);
	            	}
	            }
	        });
		}
	}
	
	function do_accept(rsm_id){
		do_submit(rsm_id,'do_nextState', '<%=session.getAttribute("u_id")%>');
	}
	
	function do_deny(rsm_id){
		do_submit(rsm_id,'do_reject', '<%=session.getAttribute("u_id")%>');
	}

	function do_submit(rsm_id, work_div, exp_id){
		$("#WORK_DIV").val(work_div);
		$("#EXP_ID").val(exp_id);
		$("#RSM_ID").val(rsm_id);
		testFrm.submit();
	}
	
	function do_deleteAll(){
		if (confirm("삭제하시겠습니까?") == true){
			var chkList = "";
			$('input:checkbox[name="chkList"]').each(function() {
			      if(this.checked){
			    	  chkList += this.value + "\\";
			      }
			 });
			
			$.ajax({
	            url: "do_deleteAll.do",
	            data: {chkList: chkList},
	            type: 'POST',
	            success: function(result){
	            	if(result == "fail"){
	            		alert("거래중인 항목은 삭제할 수 없습니다.");
	            		return;
	            	} else{
	            		location.reload();
	            	}
	            }
	        });
		}
	}
</script>
</head>
<body>
	<form action="workdiv.do" method="get" name="testfrm" id="testFrm">
		<input type="hidden" id="WORK_DIV" name="WORK_DIV" />
		<input type="hidden" id="EXP_ID" name="EXP_ID" />
		<input type="hidden" id="RSM_ID" name="RSM_ID" />
		<input type="hidden" name="PAGE_NUM" id="PAGE_NUM" value="<%=PAGE_NUM%>">
		<input type="hidden" name="file_nm" id="file_nm">
	</form>

	<c:choose>
		<c:when test="${u_level eq '1'}">
		<!-- 일반 회원인 경우 -->
			<table id="userTable" class="w3-table-all w3-card-4" style="color: black;">
				<thead>
					<tr class="w3-Indigo">
						<td style="width: 5%;"><div align="center"><input id="allCheck" type="checkbox" /></div></td>
						<td style="width: 5%;"><div align="center">번호</div></td>
						<td style="width: 30%;"><div align="center">제목</div></td>
						<td style="width: 20%;"><div align="center">전문가</div></td>
						<td style="width: 20%;"><div align="center">작성일</div></td>
						<td style="width: 5%;"><div align="center">원본</div></td>
						<td style="width: 5%;"><div align="center">첨삭</div></td>
						<td style="width: 10%;"><div align="center">상태</div></td>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${ordersList.size()>0}">
							<c:forEach var="orders" items="${ordersList}" begin="0">
								<tr id="ordersInfoTr">
									<input type="hidden" name="rsm_id" value="${orders.rsm_id}" />
									<input type="hidden" name="exp_id" value="${orders.exp_id}" />
									<td><div align="center"><input type="checkbox" id="chkList" name="chkList" value="${orders.rsm_id }"/></div></td>
									<td><div align="center"><c:out value="${orders.no}" /></div></td>
									<td><div align="center"><c:out value="${orders.rsm_title}" /></div></td>
									<td><div align="center"><c:out value="${orders.exp_id}" /></div></td>
									<td><div align="center"><c:out value="${orders.ord_reg_dt}" /></div></td>
									<td>
										<button style="width: 100%;" type="button" class="btn btn-labeled btn-success" onclick="do_searchOriginal(${orders.rsm_id})">
											<span class="btn-label">
								          		<i class="glyphicon glyphicon-text-size"></i>
								           	</span>
								           	원본조회
										</button>
									</td>
									<td class="text-right">
										<c:choose>
											<c:when test="${orders.ord_state eq 40 || orders.ord_state eq 50 }">
												<button style="width: 100%;" type="button" class="btn btn-labeled btn-primary" onclick="do_searchRevision(${orders.rsm_id})">
													<span class="btn-label">
										          		<i class="glyphicon glyphicon-ok"></i>
										           	</span>
										           	첨삭조회
												</button>
											</c:when>
											<c:when test="${orders.ord_state eq 30 }">
												<button style="width: 100%;" type="button" class="btn btn-labeled btn-info" disabled>
													<span class="btn-label">
										          		<i class="glyphicon glyphicon-pencil"></i>
										           	</span>
										           	첨삭중..
												</button>
											</c:when>
										</c:choose>
									</td>
									<td class="text-right">
										<c:choose>
												<c:when test="${orders.ord_state eq 10}">
													<div align="right"><label style="color: red;">거절됨</label></div>
												</c:when>
												<c:when test="${orders.ord_state eq 20}">
													<div align="right"><label style="color: #0054FF;">수락 대기중..</label></div>
												</c:when>
												<c:when test="${orders.ord_state eq 30}">
													<div align="right"><label style="color: #0054FF;">첨삭 대기중..</label></div>
												</c:when>
												<c:when test="${orders.ord_state eq 40}">
														<button style="width: 100%;" type="button" name="signTest" class="btn btn-labeled btn-danger" onclick="do_compl('${orders.rsm_id}','${orders.exp_id }')">
															<span class="btn-label">
													          	<i class="glyphicon glyphicon-thumbs-up"></i>
													        </span>
													          확정하기
														</button>
												</c:when>
												<c:when test="${orders.ord_state eq 50}">
													<div align="right"><label style="color: #22741C;">의뢰 완료</label></div>
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
			<br/>
				<button type="button" class="btn btn-labeled btn-danger pull-left" id="doDeleteList" onclick="do_deleteAll()">
					<span class="btn-label">
				      		<i class="glyphicon glyphicon-remove"></i>
				       	</span>
				       	선택 삭제
				</button>
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
			<table id="userTable" class="w3-table-all w3-card-4" style="color: black;">
				<thead>
					<tr class="w3-Indigo">
						<td style="width: 5%;"><div align="center"><input id="allCheck" type="checkbox" /></div></td>
						<td style="width: 5%;"><div align="center">번호</div></td>
						<td style="width: 30%;"><div align="center">제목</div></td>
						<td style="width: 20%;"><div align="center">의뢰자</div></td>
						<td style="width: 20%;"><div align="center">작성일</div></td>
						<td style="width: 5%;"><div align="center">원본</div></td>
						<td style="width: 5%;"><div align="center">첨삭</div></td>
						<td style="width: 10%;"><div align="center">상태</div></td>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${ordersList.size()>0}">
							<c:forEach var="orders" items="${ordersList}" begin="0">
								<tr id="ordersInfoTr">
									<input type="hidden" name="rsm_id" value="${orders.rsm_id}" />
									<input type="hidden" name="exp_id" value="${orders.exp_id}" />
									<td><div align="center"><input type="checkbox" name="chkList" id="chkList" value="${orders.rsm_id }"/></div></td>
									<td><div align="center"><c:out value="${orders.no}" /></div></td>
									<td><div align="center"><c:out value="${orders.rsm_title}" /></div></td>
									<td><div align="center"><c:out value="${orders.u_id}" /></div></td>
									<td><div align="center"><c:out value="${orders.ord_reg_dt}" /></div></td>
									<td>
										<div align="center">
											<c:if test="${orders.ord_state ne 10 }">
												<button style="width: 100%;" type="button" class="btn btn-labeled btn-success" onclick="do_searchOriginal(${orders.rsm_id})">
													<span class="btn-label">
										          		<i class="glyphicon glyphicon-text-size"></i>
										           	</span>
										           	원본조회
												</button>
											</c:if>
										</div>
									</td>
									<td>
										<div align="center">
											<c:if test="${orders.ord_state eq 20 }">
												<button style="width: 100%;" type="button" name="signTest" class="w3-button w3-green" onclick="do_accept(${orders.rsm_id})">
													<span class="btn-label">
											          	<i class="glyphicon glyphicon-ok"></i>
											        </span>
											           수락하기
												</button>
											</c:if>
											<c:if test="${orders.ord_state eq 40 || orders.ord_state eq 50 }">
												<button style="width: 100%;" type="button" id="revisionBtn" class="btn btn-labeled btn-primary" onclick="do_searchRevision(${orders.rsm_id})">
													<span class="btn-label">
										          		<i class="glyphicon glyphicon-ok"></i>
										           	</span>
										           	첨삭조회
												</button>
											</c:if>
											<c:if test="${orders.ord_state eq 30 }">
												<button style="width: 100%;" type="button" id="revisionBtn" class="btn btn-labeled btn-info" onclick="do_searchRevision(${orders.rsm_id})">
													<span class="btn-label">
										          		<i class="glyphicon glyphicon-pencil"></i>
										           	</span>
										           	첨삭하기
												</button>
											</c:if>
										</div>
									</td>
									<td class="text-right">
										<div align="right">
											<c:choose>
												<c:when test="${orders.ord_state eq 10}">
													<label style="color: red; font-weight: bold;">거절함</label>
												</c:when>
												<c:when test="${orders.ord_state eq 20}">
													<button style="width: 100%;" type="button" id="rejectTest" name="rejectTest" class="w3-button w3-red" onclick="do_deny(${orders.rsm_id})">
														<span class="btn-label">
											          		<i class="glyphicon glyphicon-remove"></i>
											           	</span>
											           	거절하기
													</button>
												</c:when>
												<c:when test="${orders.ord_state eq 30}">
													<button style="width: 100%;" type="button" id="revisionBtn" class="btn btn-labeled btn-warning" onclick="do_complete('${orders.rsm_id}','${orders.exp_id }')">
														<span class="btn-label">
											          		<i class="glyphicon glyphicon-ok"></i>
											           	</span>
											           	첨삭 완료
													</button>
												</c:when>
												<c:when test="${orders.ord_state eq 40}">
													<label style="color: #FFBB00; font-weight: bold;">확정 대기중..</label>
												</c:when>
												<c:when test="${orders.ord_state eq 50}">
													<label style="color: #22741C; font-weight: bold;">의뢰 완료</label>
												</c:when>
											</c:choose>
											</div>
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
			<br/>
			<button type="button" class="btn btn-labeled btn-danger pull-left" onclick="do_deleteAll()">
					<span class="btn-label">
				      		<i class="glyphicon glyphicon-remove"></i>
				       	</span>
				       	선택 삭제
				</button>
			<div class="form-inline text-center ">
				<%=StringUtil.renderPaging(totalCnt, oPage_num, oPage_size, bottomCount, "pagelist.do", "do_search_page")%>
			</div>				
		</c:when>		
	</c:choose>
</body>
</html>