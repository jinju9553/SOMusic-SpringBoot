<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="targetUrl"><c:url value="/shop/newOrderSubmitted.do" /></c:set>

<title>중고 상품 구매</title>
<div align="center">
<form:form modelAttribute="purchaseReq" action="${targetUrl}" method="post">
  <form:errors cssClass="error" /> <br><br>
  
  <script>
  	function setAddress(event) {
  		if(event.target.value == '1')
  			console.log("주문자와 동일한 주소");
  		else
  			console.log("새 주소 입력을 위해 폼 초기화");
  	}
  	
  	function setPaymentOption(event) {
		console.log("선택한 버튼은 " + event.target.value);
		//value 값에 따라 해당하는 결제 수단으로 이동(or 기타 처리)
  	}
  </script>
  
  <table class="n13">
  	<!-- 상품 정보 -->
  	<tr>
  		<td> <font color="green" size="8"><b>상품 구매</b></font> </td>
  	</tr>
  	<tr>
  		<td> <div style="height: auto; width: 170%; border-top:1px solid gold; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr> <!-- padding은 나중에 별도의 CSS 파일로 & 파일 경로 및 값은 product.name 등으로 접근 -->
  		<td> <img id="noImage" src="<c:url value='../images/purchase/noImage.png'/>"> </td>
  		<td style="padding-bottom: 5%;"> 상품 이름 </td> 
  		<td style="padding-bottom: 5%;"> 상품 가격 </td> 
  	</tr>
  	
  	<!-- 세부 항목 1 -->
    <tr>
      <td style="padding-top: 5%"> <font color="green" size="4"><b>구매자 정보</b></font> </td>
    </tr>
    <tr>
  		<td> <div style="height: auto; width: 170%; border-top:1px solid gold; margin-bottom: 5%;"></div> </td>
  	</tr>
    
    <tr>
      <td>배송지 선택</td>
      <td>
      	<input type='radio' name='shippingOption' value='1' onclick='setAddress(event)'/> 주문자와 동일
      	<input type='radio' name='shippingOption' value='2' onclick='setAddress(event)'/> 신규 배송지
		<%-- <form:radiobuttons items="${shippingOption}" path="shippingOption"/> --%>
      </td> <!-- 대응하는 필드가 없는데 path 없어도 되는지? ==> 있어야 됨 -->
    </tr>
  	
    <tr>
      <td>이름</td>
      <td><form:input path="consumerName"/> 
        <form:errors path="consumerName"/></td>
    </tr>
    <tr>
      <td>휴대폰번호</td>
      <td><form:input path="phone"/>
        <form:errors path="phone"/></td>
    </tr>
    <tr>
      <td>우편번호</td>
      <td><form:input path="zipcode"/> 
        <form:errors path="zipcode"/></td>
    </tr>
    <tr>
      <td>주소</td>
      <td><form:input path="address"/> 
        <form:errors path="address"/></td>
    </tr>
    <tr>
      <td>배송시 요청사항</td>
      <td><form:input path="shippingRequest"/> 
        <form:errors path="shippingRequest"/></td>
    </tr>

	<!-- 세부 항목 2 -->
    <tr>
      <td style="padding-top: 5%">
        <font color="green" size="4"><b>결제수단 선택</b></font>
      </td>
    </tr>
    <tr>
  		<td> <div style="height: auto; width: 170%; border-top:1px solid gold; margin-bottom: 5%;"></div> </td>
  	</tr>
  </table>
  <c:forEach var="option" items="${paymentOption}">
	<%-- <button>${option}</button> --%> <!-- button 태그는 자동으로 POST 처리 -->
	<input type="button" value="${option}" onClick="setPaymentOption(event)">
  </c:forEach>
  <p>
	<!-- <input type="image" src="../images/button_submit.gif"> -->
  </p>
</form:form>
</div>

<%-- <%@ include file="IncludeBottom.jsp"%> --%>
