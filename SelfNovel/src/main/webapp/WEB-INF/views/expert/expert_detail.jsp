<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/timeline.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		sessionStorage.setItem("itemSeq", 1);
		// define variables
		var items = document.querySelectorAll(".timeline li");
		
		// check if an element is in viewport
		function isElementInViewport(el) {
		  var rect = el.getBoundingClientRect();
		  return (
		    rect.top >= 0 &&
		    rect.left >= 0 &&
		    rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
		    rect.right <= (window.innerWidth || document.documentElement.clientWidth)
		  );
		}
		
		function callbackFunc() {
		  for (var i = 0; i < items.length; i++) {
		    if (isElementInViewport(items[i])) {
		      items[i].classList.add("in-view");
		    }
		  }
		}
		
		// listen for events
		window.addEventListener("load", callbackFunc);
		window.addEventListener("resize", callbackFunc);
		window.addEventListener("scroll", callbackFunc);
	});

	function do_detail(rsm_id, u_id){
		$.ajax({
            url: "do_detail.do",
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
		$('#orderModal').modal('hide');
		$("#addedItem").html('');
		sessionStorage.setItem("itemSeq",1);
		$("#rsm_title").val('');
		$("#rsm_content").val('');
		$("#itm_title1").val('');
		$("#itm_content1").val('');
	}
	
	function open_order(){
		$('#orderModal').modal({backdrop: 'static', keyboard: false});
	}
	
	function addItem(){
		sessionStorage.setItem("itemSeq",sessionStorage.getItem("itemSeq") + 1);
		var html="";
			html += "<div id='item"+ sessionStorage.getItem("itemSeq")+"'>";
			html += "<br/>";
			html +=	"	<table style='width: 100%;' align='center'>                                                	   ";
			html +=	"	<tr>                                                                                           ";
			html +=	"		<td style='color: black; width: 80%;'>                                                     ";
			html +=	"			<input type='text' style='width:100%;' id='itm_title"+ sessionStorage.getItem("itemSeq") +"'/>";
			html +=	"		</td>                                                                                      ";
			html +=	"		<td rowspan='2' style='color: black; width: 20%;'>                                         ";
			html +=	"			<div align='center'>                                                                   ";
			html +=	"				<input style='width: 30%;' type='button' value='&uarr;' onclick='moveUp("+sessionStorage.getItem("itemSeq")+")'/><br>           ";
			html +=	"				<input style='width: 30%;' type='button' value='&darr;' onclick='moveDown("+sessionStorage.getItem("itemSeq")+")'/><br>           ";
			html +=	"				<input style='width: 30%;' type='button' value='-' onclick='removeItem("+sessionStorage.getItem("itemSeq")+")'/>                    ";
			html +=	"			</div>                                                                                 ";
			html +=	"		</td>                                                                                      ";
			html +=	"	</tr>                                                                                          ";
			html +=	"	<tr>                                                                                           ";
			html +=	"		<td style='color: black;'>                                                                 ";
			html +=	"			<textarea style='resize:none; width:100%; height: 300px;' id='itm_content"+ sessionStorage.getItem("itemSeq") +"'></textarea>";
			html +=	"		</td>                                                                                      ";
			html +=	"	</tr>                                                                                          ";
			html +=	"	</table>                                                                                          ";
			html += "</div>";
		$("#addedItem").append(html);
	}
	
	function removeItem(seq){
		$("#item"+seq).remove();
	}
	
	function moveUp(seq){
		if(seq != 1){
			for(var i=seq-1; i>0; i--){
				if($("#itm_title"+i).val() != null){
					var temp_title = $("#itm_title"+seq).val();
					var temp_content = $("#itm_content"+seq).val();
					$("#itm_title"+seq).val($("#itm_title"+i).val());
					$("#itm_content"+seq).val($("#itm_content"+i).val());
					$("#itm_title"+i).val(temp_title);
					$("#itm_content"+i).val(temp_content);
					break;
				}
			}	
		}
	}
	
	function moveDown(seq){
		if(seq != sessionStorage.getItem("itemSeq")){
			for(var i=seq; i<sessionStorage.getItem("itemSeq")+1; i++){
				if($("#itm_title"+(seq+i)).val() != null){
					var temp_title = $("#itm_title"+(seq+i)).val();
					var temp_content = $("#itm_content"+(seq+i)).val();
					$("#itm_title"+(seq+i)).val($("#itm_title"+seq).val());
					$("#itm_content"+(seq+i)).val($("#itm_content"+seq).val());
					$("#itm_title"+seq).val(temp_title);
					$("#itm_content"+seq).val(temp_content);
					break;
				}
			}	
		}
	}
	
	function do_order(){
		var u_id = '<%=session.getAttribute("u_id")%>';
		var exp_id = '${exp_id}';
		var itm_titles="";
		var itm_contents="";
		
		for(var i=1; i<sessionStorage.getItem("itemSeq")+1; i++){
			if($("#itm_title"+i).val() != null){
				itm_titles += $("#itm_title"+i).val() + "\\";
				itm_contents += $("#itm_content"+i).val() + "\\";
			}
		}
		alert("itm_titles: " + itm_titles);
		sessionStorage.setItem("itemSeq",1);
		
		$.ajax({
            url: "do_order.do",
            data: {rsm_title: $("#rsm_title").val(),
            	   rsm_content: $("#rsm_content").val(),
            	   itm_titles: itm_titles,
            	   itm_contents: itm_contents,
            	   u_id: u_id,
            	   exp_id: exp_id
            	  },
            type: 'POST',
            success: function(result){
            	if(result == "fail"){
            		alert("의뢰하기 실패");
            	} else{
            		orderFrm.submit();
            	}
            }
        });
	}
</script>
<title>Insert title here</title>
</head>
<body>
<form id="orderFrm" action="../mypage/orders/pagelist.do" method="get"></form>
<section class="intro">
  <div class="container">
  	<div align="center">
	    <table>
	    	<tr>
	    		<td rowspan="4"><img src="/controller/resources/exp_profiles/${exp_profile}" width="200px" height="200px"></td>
	    		<td><h1>닉네임: ${u_name}</h1></td>
	    	</tr>
	    	<tr>
	    		<td><h1>제목: ${exp_title }</h1></td>
	    	</tr>
	    	<tr>
	    		<td><h1>전문분야: ${dtl_cd_nm }</h1></td>
	    	</tr>
	    	<tr>
	    		<td><h1>가격: ${exp_price }</h1></td>
	    	</tr>
	    </table>
    </div>
  </div>
  <div align="right">
  	<input class="btn btn-success" type="button" value="의뢰하기" onclick="open_order()"/>
  </div>
</section>

<section class="timeline">
  <ul>
  <c:choose>
  	<c:when test="${RsmList.size()>0}" >
		<c:forEach var="rsmVO" items="${RsmList}">
			<li>
		      <div>
		        <time>${rsmVO.rsm_reg_dt }</time><br>
		                제목: <a href="javascript:do_detail('${rsmVO.rsm_id }','${exp_id }')" style="color: white;">${rsmVO.rsm_title }</a><br>
		                분야: ${rsmVO.rsm_div }
		      </div>
		    </li>
		</c:forEach>
		</c:when>
	</c:choose>
  </ul>
</section>

	<!-- 디테일 뷰 모달 -->
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
	
	<!-- 의뢰하기 모달 -->
	<div class="modal fade" id="orderModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content panel-info %>" >
					<div class="modal-header panel-heading">
						<h4 class="modal-title">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="cancelBtn();">
			          		×
			        		</button>
							<b>의뢰하기</b>
						</h4>
					</div>
					<div class="modal-body" id="orderModalBody" align="center">
						<table style='width: 100%;'>
							<tr>
								<td style='color: black; background-color: #D8572A; text-align:center;'>
									<h1>요구사항 입력</h1>
								</td>
							</tr>
						</table>
						<br/>
						<table style="width: 100%;" align="center">
							<tr>
								<td style="color: black; width: 80%;">
									<input style="width: 100%;" type="text" id="rsm_title" />
								</td>
							</tr>
							<tr>
								<td style="color: black;">
									<textarea style="resize:none; width:100%; height: 300px;" id="rsm_content"></textarea>
								</td>
							</tr>
						</table>
					
					<table style='width: 100%;'>
							<tr>
								<td style='color: black; background-color: #F7B538; text-align:center;'>
									<h1>자소서 내용 입력</h1>
								</td>
							</tr>
						</table>
						<br/>
						<div id="item1">
								<table style="width: 100%;" align="center">
									<tr>
										<td style="color: black; width: 80%;">
											<input style="width: 100%;" type="text" id="itm_title1" />
										</td>
										<td rowspan="2" style="color: black; width: 20%;">
											<div align="center">
												<input style="width: 30%;" type="button" value="&darr;" onclick="moveDown(1)"/><br>
											</div>
										</td>
									</tr>
									<tr>
										<td style="color: black;">
											<textarea style="resize:none; width:100%; height: 300px;" id="itm_content1"></textarea>
										</td>
									</tr>
								</table>
							</div>
						<div id="addedItem">
							
						</div>
						<div align="center">
							<br/>
							<a href="javascript:addItem()" ><img src="<c:url value='/resources/img/plus-button.png' />" width=50 height=50/></a>
						</div>
					</div>
					<!-- Modal Footer -->
		            <div class="modal-footer">
		            	<div align="right">
		            		<button class="btn btn-success" onclick="do_order();">
			                    	의뢰하기
			                </button>
			            	<button class="btn btn-danger" onclick="cancelBtn();">
			                    	취소
			                </button>
		                </div>
		            </div>
		            </div>
				</div>
			</div>
	</div>
</body>
</html>