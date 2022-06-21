<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<meta charset="UTF-8">

<body>
<%@ include file="header.jsp"%>
	<div class="container" style="width: 40%;">
		<div>
			<h1>오류</h1>
				<p><c:out value="${message}" default="오류가 발생했습니다. 다시 시도해주세요."/></p>
		</div>
	</div>
</body>

</html>

