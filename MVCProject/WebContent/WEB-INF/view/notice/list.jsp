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
<title>Insert title here</title>
</head>
<body>
	<form action="list">
<!-- 		<table>
			<tr>
				<td>
					<input type="radio" value="title" name="field"/>타이틀
					<input type="radio" value="writer_id" name="field"/>글쓴이				
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" name="query"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="조회"/>
				</td>
			</tr>
		</table> -->
		<select name="f">
			<option ${(param.f=="title")?"selected":""} value="title">제목</option>
			<option ${(param.f=="writer_id")?"selected":""} value="writer_id">작성자</option>
		</select>
		<label>검색어</label>
		<input type="text" name="q" value="${param.q}"/>
		<input type="submit" value="검색"/>
	</form>
	<table border=1>
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수</td>
		</tr>
	<%-- 주석처리
	<%List<Notice>list = (List<Notice>)request.getAttribute("noticelist"); 	//날라오는 형이 object형이라서 List<Notice>형으로 형변환을 해줘야 한다. 언박싱작업
		for(Notice nt: list){ 
			pageContext.setAttribute("n", nt);
	%>
	--%>
	<c:forEach var="n" items = "${noticelist}" varStatus="t">
		<tr>
			<td>${n.id}</td>
			<td><a href="detail?id=${n.id}&p=${param.p}&f=${param.f}&q=${param.q}">${n.title}</a></td>
			<td>${n.writerID}</td>
			<td>
				<fmt:formatDate pattern="yyyy.MM.dd." value="${n.regDate}"/>
			</td>
			<td>
				<fmt:formatNumber type="number" pattern="###,###" value="${n.hit}"></fmt:formatNumber>
			</td>			
		</tr>
	</c:forEach>
	<%-- 주석처리
	<%}%>
	--%>
	</table>
	<!-- startnum 변수 선언 및 값 할당 -->
	<c:set var="page" value="${empty param?1:param.p}"></c:set>
	<c:set var="startNum" value="${page-(page-1)%5}"></c:set>		<!-- 시작숫자 처리 awesome! -->
	<c:set var="lastNum" value="${fn:substringBefore(Math.ceil(count/10), '.')}"></c:set>
	<!-- 현재 페이지 -->
	<div>
		<h3>현재 페이지</h3>
		<div><span>${empty param.p?1:param.p}</span>/ ${lastNum} pages</div>
	</div>
	<!-- page 이동 -->
	


	<!-- prev -->
	<c:if test="${startNum > 1}">
		<a href="?p=${startNum-1}&f=${param.f}&q=${param.q}">Prev</a>
	</c:if>
	<c:if test="${startNum <= 1}">
		<a href="#" onclick="alert('first page!');">Prev</a>
	</c:if>
	<ul>
		<c:forEach var="i" begin="0" end="4">
		<c:if test="${param.p == (startNum+i)}">
			<c:set var="style" value="font-weight:bold; color:green;"></c:set>
		</c:if>
		<c:if test="${param.p != (startNum+i)}">
			<c:set var="style" value=""></c:set>
		</c:if>
		
		<c:if test="${(startNum+i) <= lastNum}">
			<li>
				<a style="${style}" href="?p=${startNum+i}&f=${param.f }&q=${param.q}">${startNum+i}</a>
			</li>
		</c:if>
		</c:forEach>
	</ul>
	<!-- next -->
	<c:if test="${startNum+5 <= lastNum}">
		<a href="?p=${startNum+5}&f=${param.f}&q=${param.q}">Next</a>
	</c:if>
	<c:if test="${startNum+5 > lastNum}">
		<a href="#" onclick="alert('lost page!');">Next</a>
	</c:if>
</body>
</html>