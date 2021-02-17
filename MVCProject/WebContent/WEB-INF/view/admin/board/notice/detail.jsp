<%@page import="com.jdbc.app.entity.Notice"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NoticeDetail</title>
</head>
<body>
	<table border=1>
		<tr>
		<!-- 타이틀 -->
			<td>제목</td>
			<td colspan="3">${nt.title}</td>
		</tr>
		<tr>
		<!-- regdate -->
			<td>작성일</td>
			<td colspan="3">
				<fmt:formatDate pattern="yyyy.MM.dd. hh.mm" value="${nt.regDate}"/>
			</td>
		</tr>
		<tr>
		<!-- writerid -->
			<td>작성자</td>
			<td>${nt.writerID}</td>
			<!-- hit -->
			<td>조회수</td>
			<td>
				<fmt:formatNumber type="number" pattern="###,###" value="${nt.hit}"></fmt:formatNumber>
			</td>	
		</tr>
		<tr>
		<!-- files -->
			<td>첨부파일</td>
			<td colspan="3">
				<c:forTokens var="fileName" items="${nt.files}" delims="," varStatus="t">
					<c:set var="style" value=""></c:set>
					<c:if test="${fn:endsWith(fileName, '.zip')}">
						<c:set var="style" value="font-weight:bold; color:green;"></c:set>
					</c:if>
					<a download href="../../../upload/${fileName}" style="${style}">${fn:toUpperCase(fileName)}</a>
					<c:if test="${t.last==false}">/</c:if>
				</c:forTokens>
			</td>
		</tr>
		<tr>
		<!-- content -->
			<td colspan="4">${nt.content}</td>
		</tr>
	</table>
	<!-- 목록 버튼 -->
	<input type="button" onclick="location.href='list?f=${param.f}&q=${param.q}'" value="목록으로"/>
	<input type="button" onclick="location.href='list?id=${id}'" value="삭제"/>
	<button onclick="location.href=''">수정</button>
</body>
</html>