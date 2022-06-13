<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="targetUrl"><c:url value="/join/${joinReq.groupPurchase.gpId}" /></c:set>

<title>공구 참여</title>
<div align="center">
<form:form modelAttribute="joinReq" action="${targetUrl}" method="post">
  <form:errors cssClass="error" /> <br><br>
  
  <script>
	  $(document).ready(function() {
		  var imgSrc = "${joinReq.groupPurchase.image}";
		  if (imgSrc == null) {
			  $("#image").attr("src", "<c:url value='../images/purchase/noImage.png'/>")
		  }
		  
		  var p = Number($("#price").text());
		  
		  $(".quantityUp").click(function(){ 
			  var q = $(".quantity").val();
			  if(q >= 100) 
				  alert("최대 주문 수량은 100개입니다.");
			  else {
				  q = Number(q) + 1;
				  $(".quantity").val(q);
				  $(".quantity").text(q);
				  $(".totalAmount").text(p * q);
				  calculateSum(); //만약 배송비가 설정되지 않았다면 상품 총액만 계산
			  }
		  });
		  
		  $(".quantityDown").click(function(){
			  var q = $(".quantity").val();
			  if(q <= 1) 
				  alert("최소 주문 수량은 1개입니다.");
			  else {
				  q = Number(q) - 1;
				  $(".quantity").val(Number(q) - 1);
				  $(".quantity").text(Number(q) - 1);
				  $(".totalAmount").text(p * q);
				  calculateSum(); //만약 배송비가 설정되지 않았다면 상품 총액만 계산
			  }
		  });
		  	  
		//radio buttoun 옆의 label 변경
		$("#shippingMethod1").next().text('준등기 (+${shippingCost[0]}원)');
		$("#shippingMethod2").next().text('택배 (+${shippingCost[1]}원)');
		$("#shippingMethod3").next().text('택배(제주산간) (+${shippingCost[2]}원)');
	  });
  
	  function setAddress(event) {
	  	if(event.target.value == '0') {
	  		console.log("주문자와 동일한 주소");
	  	}
	  	else if(event.target.value == '1') {
	  		console.log("새 주소 입력을 위해 폼 초기화");
	  		$(".shipping").children().val(" ");
	  	}
	  }
	  
	  function radioClick (event) {
		$("#shippingCost").show();
		$("#shippingCost").children().removeClass("redFont");
		$("#totalShippingAmount").children().removeClass("redFont");
		var s = Number(event.target.value);
		switch(s) {
			case 1:
				$("#shippingCost").children().text(${shippingCost[0]}); //s값 인식 X
				break;
			case 2:
				$("#shippingCost").children().text(${shippingCost[1]});
				break;
			case 3:
				$("#shippingCost").children().text(${shippingCost[2]});
				break;
		}
		calculateSum();
	}
	  
	function calculateSum() {
		var currentCost = Number($("#shippingCost").children().text());
		var currentAmount = Number($("#totalOne").text());
		$("#totalShippingAmount").show(); 
		$("#totalShippingAmount").children().text(currentCost + currentAmount + " 원");
	}
  </script>
  
  <style>
  		.img {
			width: 330px;
			height: 300px;
		}
		
		#noInterest:hover {
			color: #FF6464;
			border-color: #ffeded;
		}
		
		#noInterest {
			left: 48.5%;
	    	top: 61.5%;
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
  
  <table class="n13">
  	<!-- 공구 정보 -->
  	<tr>
  		<td> <font class="color_purple" size="8"><b>공동구매 참여하기</b></font> </td>
  	</tr>
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr> <!-- rowspan : 이 칸 옆에 row(<tr>)가 몇 개까지 들어갈 수 있는지 -->
  		<td rowspan="5" align = "center"> <img id="image" class="img" src="<c:url value="${joinReq.groupPurchase.image}"/>"> </td>
  		<td> <button id="noInterest" type="button" onclick="interest();">❤</button> </td>
  	</tr>
  	
  	<tr>
  		<td style="padding-bottom: 50; padding-top: 50;"> 공동구매 제목: ${joinReq.groupPurchase.title}  </td>
  	</tr>
  	<tr> 
  		<td style="padding-bottom: 50;"> 개당 가격: <a id="price">${joinReq.groupPurchase.price}</a> 원</td>
  	</tr>
  	<tr>
  	  	<td style="padding-bottom: 50;"> 주문 수량: 
  			<span>
				<form:input path="quantity" style="width: 20%;" class="quantity" type="text"></form:input> 
				<a href="#"> 
					<img style="position: absolute;" src="//img.echosting.cafe24.com/skin/base_ko_KR/product/btn_count_up.gif" alt="수량증가" class="quantityUp">
				</a>
				<a href="#">
					<img style="top: 53%; position: absolute;" src="//img.echosting.cafe24.com/skin/base_ko_KR/product/btn_count_down.gif" alt="수량감소" class="quantityDown">
				</a>
			</span>
  		</td>
  	</tr>
  	
  	<tr>
  		<td style="padding-bottom: 50;"> 총 상품금액 : <a class="totalAmount">${joinReq.groupPurchase.price}</a> 원 
  		(<a class="quantity">${joinReq.quantity}</a>개)</td>
  	</tr>
  	
  	<!-- 세부 항목 1 -->
  	<tr>
      <td style="padding-top: 5%"> <font class="color_purple" size="4"><b>주문 정보</b></font> </td>
    </tr>
    <tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
		<td>배송 방법</td>
  	</tr>
  	<tr>
  		<td colspan="2" align="center" style="padding-bottom: 5%;">
			<form:radiobuttons class="radio" path="shippingMethod" items="${shippingOption}" onClick="radioClick(event)" />
  	</tr>
  	
  	<tr>
		<td>총 상품 금액</td>
		<td><a id="totalOne" class="totalAmount">${joinReq.groupPurchase.price}</a> 원</td>
  	</tr>
  	
  	<tr>
		<td>배송비</td>
		<td id="shippingCost"><a class="redFont">*배송 방법을 선택해주세요</a></td>
  	</tr>
  	
  	<tr>
		<td>배송비 포함 총액</td>
		<td id="totalShippingAmount"><a class="redFont">*배송 방법을 선택해주세요</a></td>
  	</tr>
  	
  	<!-- 세부 항목 2 -->
  	<tr>
      <td style="padding-top: 5%"> <font class="color_purple" size="4"><b>입금 정보</b></font> </td>
    </tr>
    <tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
    
  	<tr>
		<td>공구 은행 정보</td>
		<td>${joinReq.groupPurchase.bank} ${joinReq.groupPurchase.account} </td>
  	</tr>
  	
  	<tr>
		<td>입금자명</td>
        <td><form:input path="accountHolder" placeholder="입금자명"/>
        <form:errors path="accountHolder"/></td>
  	</tr>
  	
  	<tr>
  		<td>입금은행</td>
        <td><form:input path="consumerBank" placeholder="입금은행"/> 
        <form:errors path="consumerBank"/></td>
  	</tr>
  	
  	<tr>
  		<td>환불계좌 은행</td>
        <td><form:input path="refundBank" placeholder="환불계좌 은행"/> 
        <form:errors path="refundBank"/></td>
  	</tr>
  	
  	<tr>
  		<td>환불계좌 번호</td>
        <td><form:input path="refundAccount" placeholder="환불계좌 번호"/> 
        <form:errors path="refundAccount"/></td>
  	</tr>
  	
  	<tr>
  		<td>환불계좌 예금주명</td>
        <td><form:input path="refundHolder" placeholder="환불계좌 예금주명"/> 
        <form:errors path="refundHolder"/></td>
  	</tr>
  	
  	<!-- 세부 항목 3 -->
    <tr>
      <td style="padding-top: 5%"> <font class="color_purple" size="4"><b>구매자 정보</b></font> </td>
    </tr>
    <tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
    <tr>
      <td>배송지 선택</td>
      <td>
      	<input type='radio' name='shippingOption' value='0' onclick='setAddress(event)'/> 주문자와 동일
      	<input type='radio' name='shippingOption' value='1' onclick='setAddress(event)'/> 신규 배송지
		<%-- <form:radiobuttons items="${shippingOption}" path="shippingOption"/> --%>
      </td>
    </tr>
    <tr>
      <td>이름</td>
      <td class="shipping"><form:input path="consumerName" placeholder="주문자 이름"/> 
        <form:errors path="consumerName"/></td>
    </tr>
    <tr>
      <td>휴대폰번호</td>
      <td class="shipping"><form:input path="phone" placeholder="휴대폰번호"/>
        <form:errors path="phone"/></td>
    </tr>
    <tr>
      <td>우편번호</td>
      <td class="shipping"><form:input path="zipcode" placeholder="우편번호"/> 
        <form:errors path="zipcode"/></td>
    </tr>
    <tr>
      <td>주소</td>
      <td class="shipping"><form:input path="address" placeholder="주소"/> 
        <form:errors path="address"/></td>
    </tr>
    <tr>
      <td>배송시 요청사항</td>
      <td class="shipping"><form:input path="shippingRequest" placeholder="배송시 요청사항(선택)"/> 
        <form:errors path="shippingRequest"/></td>
    </tr>
  </table>
  
  <p style="padding-top: 5%">
	<button class="hButton"> 등록하기 </button>
  </p>
</form:form>
</div>

<%-- <%@ include file="IncludeBottom.jsp"%> --%>
