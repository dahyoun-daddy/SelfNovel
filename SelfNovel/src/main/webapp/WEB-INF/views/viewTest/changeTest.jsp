<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script type="text/javascript">

function moveUp(el){
	var $tr = $(el).parent().parent(); // 클릭한 버튼이 속한 tr 요소
	$tr.prev().before($tr); // 현재 tr 의 이전 tr 앞에 선택한 tr 넣기
}

function moveDown(el){
	var $tr = $(el).parent().parent(); // 클릭한 버튼이 속한 tr 요소
	$tr.next().after($tr); // 현재 tr 의 다음 tr 뒤에 선택한 tr 넣기
}
</script>
</head>
<body>


	<table id>
		<tr>
			<td>제목1</td>			
			<td>내용1	</td>
			<td><input type="button" value="위" onclick="moveUp(this)"></td>	
			<td><input type="button" value="아래" onclick="moveDown(this)"></td>			
		</tr>
		<tr>
			<td>제목2	</td>
			<td>내용2	</td>
			<td><input type="button" value="위" onclick="moveUp(this)"></td>	
			<td><input type="button" value="아래" onclick="moveDown(this)"></td>						
		</tr>
		<tr>
			<td>제목3	</td>
			<td>내용3	</td>
			<td><input type="button" value="위" onclick="moveUp(this)"></td>	
			<td><input type="button" value="아래" onclick="moveDown(this)"></td>						
		</tr>		
	</table>

</body>
</html>