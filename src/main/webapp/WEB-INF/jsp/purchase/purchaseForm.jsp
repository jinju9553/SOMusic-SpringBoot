<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="targetUrl"><c:url value="/purchase/${purchaseReq.product.productId}" /></c:set>

<head>
	<style>
			#noInterest:hover {
			    color: #FF6464;
			    border-color: #ffeded;
			}
		
			#noInterest {
				left: 44.5%;
	    		top: 52.5%;
				position: absolute;
			    border: 2px solid;
			    border-color: #d2d2d2;
			    width: 70px;
			    height: 70px;
			    font-size: 18px;
			    font-weight: bold;
			    color: #a0a0a0;
			    background-color: white;
			    border-radius: 10px;
			}
	</style>
</head>

<title>중고 상품 구매</title>
<div align="center">
<form:form modelAttribute="purchaseReq" action="${targetUrl}" method="post">
  <form:errors cssClass="error" /> <br><br>
  
  <script>
  	function setAddress(event) {
  		if(event.target.value == '0') {
  			console.log("주문자와 동일한 주소");
  			//다시 호출
  		}
  		else if(event.target.value == '1') {
  			console.log("새 주소 입력을 위해 폼 초기화");
  			$(".shipping").children().next().children().val(" ");
  		}
  	}
  	
  	function setShippingMethod(event) {
  		if(event.target.value == '0') {
  			console.log("직거래");
  			$("#location").show();
  			$(".shipping").hide();
  		}
  		else if(event.target.value == '1') {
  			console.log("택배");
  			$("#location").hide();
  			$(".shipping").show();
  		}
  	}
  	
  	function setPaymentOption(event) {
		console.log("선택한 버튼은 " + event.target.value);
		//value 값에 따라 해당하는 결제 수단으로 이동(or 기타 처리)
  	}
  </script>
  
  <table class="n13">
  	<!-- 상품 정보 -->
  	<tr>
  		<td> <font class="color_purple" size="8"><b>상품 구매</b></font> </td>
  	</tr>
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 200%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr> <!-- padding은 나중에 별도의 CSS 파일로 & 파일 경로 및 값은 product.name 등으로 접근 -->
  		<td rowspan="3"> <img id="noImage" src="<c:url value='../images/purchase/noImage.png'/>"> </td>
  		<td> <button id="noInterest" type="button" onclick="interest();">❤</button> </td>
  	</tr>
  	
  	<tr>
  		<td style="padding-bottom: 5%;"> 상품 이름: ${purchaseReq.product.productName} </td> 
  		<td style="padding-bottom: 5%;"> 상품 가격: ${purchaseReq.product.price} 원</td>
  	</tr>
  	
  	<tr>
  		<td style="padding-bottom: 5%;"> 택배 거래시 배송비: ${purchaseReq.product.shippingCost} 원</td> 
  	</tr>
  	
  	<!-- 세부 항목 1 -->
    <tr>
      <td style="padding-top: 5%"> <font class="color_purple" size="4"><b>구매자 정보</b></font> </td>
    </tr>
    <tr>
  		<td> <div class="color_purple" style="height: auto; width: 200%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
    
    <tr>
      <td>거래 방식</td>
      <td>
      	<input type='radio' name='shippingMethod' value='0' onclick='setShippingMethod(event)'/> 직거래
      	<input type='radio' name='shippingMethod' value='1' onclick='setShippingMethod(event)'/> 택배
		<%-- <form:radiobuttons items="${shippingOption}" path="shippingOption"/> --%>
      </td> 
    </tr>
    
    <tr class="shipping">
      <td>배송지 선택</td>
      <td>
      	<input type='radio' name='shippingOption' value='0' onclick='setAddress(event)'/> 주문자와 동일
      	<input type='radio' name='shippingOption' value='1' onclick='setAddress(event)'/> 신규 배송지
		<%-- <form:radiobuttons items="${shippingOption}" path="shippingOption"/> --%>
      </td>
    </tr>
  	
    <tr class="shipping">
      <td>이름</td>
      <td><form:input path="consumerName" value="${account.userName}"/> 
        <form:errors path="consumerName"/></td>
    </tr>
    
    <tr id="location">
      <td>직거래 장소</td>
      <td><form:input path="location"/> 
        <form:errors path="location"/></td>
    </tr>
    
    <tr>
      <td>휴대폰번호</td>
      <td><form:input path="phone" value="${account.phone}"/>
        <form:errors path="phone"/></td>
    </tr>
    
    <tr class="shipping">
      <td>우편번호</td>
      <td><form:input path="zipcode" value="${account.zipcode}"/> 
        <form:errors path="zipcode"/></td>
    </tr>
    
    <tr class="shipping">
      <td>주소</td>
      <td><form:input path="address" value="${account.address}"/> 
        <form:errors path="address"/></td>
    </tr>
    
    <tr>
      <td>기타 요청사항</td>
      <td><form:textarea path="shippingRequest" placeholder="배송시 요청 사항, 직거래 희망장소 등" cols="20" rows="3"/>
        <form:errors path="shippingRequest"/></td>
    </tr>

	<!-- 세부 항목 2 -->
    <tr>
      <td style="padding-top: 5%">
        <font class="color_purple" size="4"><b>결제수단 선택</b></font>
      </td>
    </tr>
    <tr>
  		<td> <div class="color_purple" style="height: auto; width: 200%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  </table>
  <c:forEach var="option" items="${paymentOption}"> <!-- 개인 간의 거래인데 과연 필요할지? -->
	<input type="button" value="${option}" onClick="setPaymentOption(event)">
  </c:forEach>
  
  <p style="padding-top: 5%">
	<button class="hButton"> 등록하기 </button>
  </p>
  
</form:form>
</div>

<%-- <%@ include file="IncludeBottom.jsp"%> --%>
