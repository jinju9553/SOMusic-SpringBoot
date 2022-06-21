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
	  $(document).ready(function() {
		  var imgSrc = "${purchaseReq.product.image}";
		  if (imgSrc == null) {
			  $("#image").attr("src", "<c:url value='../images/purchase/noImage.png'/>")
		  }
	  });
  
  	function setAddress() {
  		console.log("주문자와 동일"); //radio 클릭을 반복하면 event.target이 사라짐
  		$("#consumerName").val("${userSession.account.userName}");
  		$("#phone").val("${userSession.account.phone}");
  		$("#zipcode").val("${userSession.account.zipcode}");
  		$("#address").val("${userSession.account.address}");
  	}
  	
  	function clearAddress() {
  		console.log("새 주소 입력을 위해 폼 초기화");
		$(".shipping").children().next().children().val(" ");
  	}
  	
  	$(document).ready(function() {
  		var temp = $('input:radio[name="shippingMethod"]:checked').val();
  		console.log(temp);
  		if(temp == '0') { //init
  			console.log("직거래");
  			$("#location").show();
  			$(".hide").hide();
  		}
  		else if(temp == '1') {
  			console.log("택배");
  			$("#location").hide();
  			$(".hide").show();
  		}
  	});
  	
  	function setShippingMethod(event) {
  		if(event.target.value == '0') {
  			console.log("직거래");
  			$("#location").show();
  			$(".hide").hide();
  		}
  		else if(event.target.value == '1') {
  			console.log("택배");
  			$("#location").hide();
  			$(".hide").show();
  		}
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
  	
  	<tr>
  		<td rowspan="4"> <img id="image" class="img" src="<c:url value="${purchaseReq.product.image}"/>"> </td>
  	</tr>
  	
  	<tr>
  		<td style="padding-bottom: 5%;"> 상품 이름: ${purchaseReq.product.productName} </td> 
  	</tr>
  	
  	<tr>
  		<td style="padding-bottom: 5%;"> 상품 가격: ${purchaseReq.product.price} 원</td>
  	</tr>
  	
  	<tr>
  		<td style="padding-bottom: 5%;"> 택배 거래시 배송비: ${purchaseReq.product.shippingCost} 원</td> 
  	</tr>
  	
  	<c:if test="${userSession ne null}">
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
      	<form:radiobutton path='shippingMethod' name="shippingMethod" value='0' onclick='setShippingMethod(event)'/> 직거래
      	<form:radiobutton path='shippingMethod' name="shippingMethod" value='1' onclick='setShippingMethod(event)'/> 택배
      </td> 
    </tr>
    
    <tr class="shipping hide">
      <td>배송지 선택</td>
      <td>
      	<input type='radio' name='shippingOption' onclick='setAddress()' checked="checked"/> 주문자와 동일
      	<input type='radio' name='shippingOption' onclick='clearAddress()'/> 신규 배송지
      </td>
    </tr>
  	
    <tr class="shipping hide">
      <td>이름</td>
      <td><form:input path="consumerName" value="${account.userName}" id="consumerName"/> 
        <form:errors path="consumerName"/></td>
    </tr>
    
    <tr id="location">
      <td>직거래 장소</td>
      <td><form:input path="location"/> 
        <form:errors path="location"/></td>
    </tr>
    
    <tr class="shipping">
      <td>휴대폰번호</td>
      <td><form:input path="phone" value="${account.phone}" id="phone"/>
        <form:errors path="phone"/></td>
    </tr>
    
    <tr class="shipping hide">
      <td>우편번호</td>
      <td><form:input path="zipcode" value="${account.zipcode}" id="zipcode"/> 
        <form:errors path="zipcode"/></td>
    </tr>
    
    <tr class="shipping hide">
      <td>주소</td>
      <td><form:input path="address" value="${account.address}" id="address"/> 
        <form:errors path="address"/></td>
    </tr>
    
    <tr>
      <td>기타 요청사항</td>
      <td><form:textarea path="shippingRequest" placeholder="배송시 요청 사항, 직거래 희망장소 등" cols="20" rows="3"/>
        <form:errors path="shippingRequest"/></td>
    </tr>
    </c:if>
  </table>
  
  <c:if test="${userSession eq null}">
    <p>로그인이 필요한 컨텐츠입니다. <br> 로그인 후 이용해주세요!</p>
    <a href="<c:url value="/user/loginForm"/>"> 
		<input class="btn" type="button" onClick="" value="로그인"></a>
  </c:if>
  
  <c:if test="${userSession ne null}">
	  <p style="padding-top: 5%;">
		<button class="hButton"> 등록하기 </button>
	  </p>
  </c:if>
  
</form:form>
</div>

<%-- <%@ include file="IncludeBottom.jsp"%> --%>
