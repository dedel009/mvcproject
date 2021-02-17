<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>공지사항 등록</h2>
	<form method ="post" action="reg" enctype="multipart/form-data">
	<table border=1>
		<tr>
			<th>제목</th>
			<td colspan="3">
				<input type="text" name="title"/>
			</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<input type="file" name="file"/>
			</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<input type="file" name="file"/>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<textarea name="content"></textarea>
			</td>
		</tr>	
		<tr>
			<td colspan="4">
				<input type="checkbox" name="open" value="true"/>
				<label for="open">바로공개</label>
			</td>
		</tr>
	</table>
	<input type="submit" value="등록"/>
	<a href = "list">취소</a>
	</form>
</body>
</html>