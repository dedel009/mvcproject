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
					<a href="${fileName}" style="${style}">${fn:toUpperCase(fileName)}</a>
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
	<br>
	<c:set var="count" value="${comCount}"></c:set>
	<form action="detail" method="post">
		<input type="hidden" name="pageID" value="${param.id }" />	
		<table border=1>
			<tr>
				<th>
				<!-- 로그인 한 사람 -->
				<input type="hidden" name="writer_id" value="Ironman">
				</th>
				<td>
				<!-- commnet -->
					<input type="text" name="comment">
				</td>
				<td>
					<input type="submit" value="댓글등록" />
				</td>
			</tr>
		</table>
	</form>
	<c:if test="${count==0}">
		<h3>등록된 댓글이 없습니다.</h3>
	</c:if>
	<c:if test="${count!=0}">
			<table border=1>
				<c:forEach  var="clist" items="${clist}">
				<tr>
					<th>작성자</th>
					<td>글번호</td>
					<td>글쓴날</td>				
					<td>내용</td>
				</tr>
				<tr>
					<th>
					<!-- 로그인 한 사람 writer_id-->
					${clist.writerID}
					</th>
					<td>
					<!-- 글번호 mid -->
					${clist.mid }
					</td>
					<td>
					<!-- regdate -->
					<fmt:formatDate value="${clist.regDate}" pattern="yyyy.MM.dd hh.mm"/>
		
					</td>				
					<td>
					<!-- commnet -->
					${clist.comment }
					</td>
				</tr>	
				</c:forEach>	
			</table>	

	</c:if>
</body>
</html>