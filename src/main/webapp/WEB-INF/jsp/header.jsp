<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
	<title>SOMusic</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="../../style/header.css" type="text/css" /> <!-- 주의: 경로 맞는지 확인 -->
</head>

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<script>	
	function search() {
		if (Searchform.keyword.value == "") {
			alert("검색어를 입력해주세요.");
			Searchform.keyword.focus();
			return false;
		} 
		Searchform.submit();
	}
</script>

<body bgcolor="white">
	<table width="100%">
		<tr>
			<td class="txLeft">
				<a href="<c:url value='/main'/>"><img id="logo" src="<c:url value="../../images/배너(임시).png"/>"></a>
			</td>
			<td></td>
			
			<td class="txCenter"> <!-- 검색창 위치 -->
				<form name="Searchform" method="GET" action="<c:url value='/main/product/search'/>">
					<table align="left">
						<tr>
							<td>
								<span class="input"> 
									<input size="40" name="keyword" type="text" placeholder="원하는 키워드로 검색해보세요." />
								</span>
							</td>
							<td>
								<input class="btn" type="button" onClick="search()" value="검색">
							</td>
						</tr>
					</table>
				</form>
			</td>
			
			<td>
				<a href="<c:url value='/product/register' />">상품 등록</a>
			</td>
			
			<td>
				<a href="<c:url value='/gp/register' />">상품 등록</a>
			</td>
			
			<td class="txRight"> <!-- 회원가입, 로그인 등 메뉴 위치 -->
				<c:if test="${empty userSession.account}">
					<a href="<c:url value="/user/register"/>"> 
						<input class="btn" type="button" onClick="" value="회원가입"></a>
						
					<a href="<c:url value="/user/loginForm"/>"> 
						<input class="btn" type="button" onClick="" value="로그인"></a>
				</c:if> 
				
				<c:if test="${!empty userSession.account}">
					<a><span>${userSession.account.userName}</span>님, 환영합니다!</a>
					<a href="<c:url value="/user/logout"/>"> 
						<input class="btn" type="button" onClick="" value="로그아웃"></a>
					<a href="<c:url value="/user/my"/>"> 
						<input class="btn" type="button" onClick="" value="마이페이지"></a>
				</c:if>
			</td>
		</tr>
	</table>
</body>
</html>
	<%-- <%@ include file="IncludeQuickHeader.jsp" %> --%>