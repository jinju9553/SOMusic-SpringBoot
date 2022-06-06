<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="targetUrl"><c:url value="/join/{id}" /></c:set>

<title>공구 참여</title>
<div align="center">
<form:form modelAttribute="joinReq" action="${targetUrl}" method="post">
  <form:errors cssClass="error" /> <br><br>
  
  <script>
  	function setAddress(event) {
  		if(event.target.value == '1')
  			console.log("주문자와 동일한 주소");
  		else
  			console.log("새 주소 입력을 위해 폼 초기화");
  	}

  </script>
  
  <table class="n13">
  	<!-- 공구 정보 -->
  	<tr>
  		<td> <font class="color_purple" size="8"><b>공동구매 참여하기</b></font> </td>
  	</tr>
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr> <!-- rowspan : 이 칸 옆에 row(<tr>)가 몇 개까지 들어갈 수 있는지 -->
  		<td rowspan="4" align = "center"> <img id="noImage" src="<c:url value='../images/purchase/noImage.png'/>"> </td>
  	</tr>
  	
  	<tr> <!-- padding은 나중에 별도의 CSS 파일로 & 파일 경로 및 값은 groupPurchase.name 등으로 접근 -->
  		<td style="padding-bottom: 50;"> 공동구매 제목: ${joinReq.groupPurchase.title}  </td>
  	</tr>
  	<tr>
  	  	<td style="padding-bottom: 50;"> 주문 수량 
  			<span class="quantity">
				<input style="width: 20%;" id="quantity" name="quantity_opt[]" value="1" type="text"> <!-- name 필요한지? -->
				<a href="#none"> <!-- 클릭 되면서도 url 변동이 없음 -->
					<img style="position: absolute;" src="//img.echosting.cafe24.com/skin/base_ko_KR/product/btn_count_up.gif" alt="수량증가" class="QuantityUp">
				</a>
				<a href="#none"> <!-- 전체 화면에서는 어긋남 -->
					<img style="top: 29.8%; position: absolute;" src="//img.echosting.cafe24.com/skin/base_ko_KR/product/btn_count_down.gif" alt="수량감소" class="QuantityDown">
				</a>
			</span>
  		</td> <!-- 변동에 따라서 ajax로 totalPrice를 계산할 수 있는지? -->
  	</tr>
  	
  	<tr>
  		<td style="padding-bottom: 50;"> 총 상품금액 : </td> <!-- ${totalPrice} -->
  	</tr>
  	
  	<!-- 세부 항목 1 -->
  	<tr>
      <td style="padding-top: 5%"> <font class="color_purple" size="4"><b>입금 정보</b></font> </td>
    </tr>
    <tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
    
  	<tr>
		<td>공구 은행 정보</td>
		<td>은행명 + 계좌번호</td> <!-- (${groupPurchase.bank} & ${groupPurchase.account})-->
  	</tr>
  	
  	<tr>
		<td>입금자명</td>
        <td><form:input path="AccountHolder"/>
        <form:errors path="AccountHolder"/></td>
  	</tr>
  	
  	<tr>
  		<td>입금은행</td>
        <td><form:input path="consumerName"/> 
        <form:errors path="consumerName"/></td>
  	</tr>
  	
  	<tr>
  		<td>환불계좌 은행</td>
        <td><form:input path="refundBank"/> 
        <form:errors path="refundBank"/></td>
  	</tr>
  	
  	<tr>
  		<td>환불계좌 번호</td>
        <td><form:input path="refundAccount"/> 
        <form:errors path="refundAccount"/></td>
  	</tr>
  	
  	<tr>
  		<td>예금주명</td>
        <td><form:input path="refundHolder"/> 
        <form:errors path="refundHolder"/></td>
  	</tr>
  	
  	<!-- 세부 항목 2 -->
    <tr>
      <td style="padding-top: 5%"> <font class="color_purple" size="4"><b>구매자 정보</b></font> </td>
    </tr>
    <tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
    <tr>
      <td>배송지 선택</td>
      <td>
      	<input type='radio' name='shippingOption' value='1' onclick='setAddress(event)'/> 주문자와 동일
      	<input type='radio' name='shippingOption' value='2' onclick='setAddress(event)'/> 신규 배송지
		<%-- <form:radiobuttons items="${shippingOption}" path="shippingOption"/> --%>
      </td>
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
  </table>
  <p>
	<!-- <input type="image" src="../images/button_submit.gif"> -->
  </p>
</form:form>
</div>

<%-- <%@ include file="IncludeBottom.jsp"%> --%>
