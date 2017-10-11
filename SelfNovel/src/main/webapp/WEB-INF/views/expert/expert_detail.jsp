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
            	var html = "<br><table style='width: 100%;'><tr><td style='color: black;  background-color: yellow;'>원본 내용</td></tr></table>";
            	var flag = 0;
            	for(var i in results){
            		if(results[i].itm_prd_id == null){
            			html += "<br><table style='width: 100%;'><tr><td style='color: black; width: 100%;'><input style='width: 100%;' type='text' readonly value='"+results[i].itm_title+"'></td></tr>";
            			html += "<tr><td style='color: black; width: 100%; height: 10%;'><textarea style='width: 100%;'>"+results[i].itm_content+"</textarea></td></tr></table>";
            		} else {
            			if(flag == 0){
            				html += "</table><br><table><tr><td style='color: black; width: 100%; background-color: blue;'>첨삭 내용</td></tr><table>";
            				flag++;
            			}
            			html += "<br><table style='width: 100%;'><tr><td style='color: black; width: 100%;'><input style='width: 100%;' type='text' readonly value='"+results[i].itm_title+"'></td></tr>";
            			html += "<tr><td style='color: black; width: 100%; height: 10%;'><textarea style='width: 100%;'>"+results[i].itm_content+"</textarea></td></tr></table>";
            		}
            	}
            	$("#detailModalBody").append(html);
            	$('#detailModal').modal({backdrop: 'static', keyboard: false});	
            }
        });
	}
</script>
<title>Insert title here</title>
</head>
<body>

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
</section>

<section class="timeline">
  <ul>
  <c:choose>
  	<c:when test="${RsmList.size()>0}" >
		<c:forEach var="rsmVO" items="${RsmList}">
			<li>
		      <div>
		        <time>${rsmVO.rsm_reg_dt }</time><br>
		        <a href="javascript:do_detail('${rsmVO.rsm_id }','${exp_id }')">${rsmVO.rsm_title }</a><br>
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
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="cancelAuth();">
			          		×
			        		</button>
							<b>첨삭 이력 조회</b>
						</h4>
					</div>
					<div class="modal-body" id="detailModalBody" align="center">
						
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>