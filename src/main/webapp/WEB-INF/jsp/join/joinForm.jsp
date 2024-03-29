<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- 공유 -->
<script>
function twitter() {
	var url = '<%=request.getRequestURL().toString().replace(request.getRequestURI(),"")%>';
	url += "/join/" + '${joinReq.groupPurchase.gpId}';
	
	var sendText = '${joinReq.groupPurchase.title}' + '\n\n';

	window.open("https://twitter.com/intent/tweet?text=" + encodeURIComponent(sendText) + "&url=" +encodeURIComponent(url));
}
</script>

<c:set var="targetUrl"><c:url value="/join/${joinReq.groupPurchase.gpId}" /></c:set>

<title>공구 참여</title>
<div align="center">
<form:form modelAttribute="joinReq" action="${targetUrl}" method="post">
  <form:errors cssClass="error" /> <br><br>
  
  <script>
	  $(document).ready(function() {
		  var imgSrc = "${joinReq.groupPurchase.image}";
		  if (imgSrc == "") {
			  $("#image").attr("src", "../images/purchase/noImage.png")
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
				  
				  calculateSum();
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
				  
				  calculateSum();
			  }
		  });
		  	  
		//radio buttoun 옆의 label 변경
		$("#shippingMethod1").next().text('준등기 (+${shippingCost[0]}원)');
		$("#shippingMethod2").next().text('택배 (+${shippingCost[1]}원)');
		$("#shippingMethod3").next().text('택배(제주산간) (+${shippingCost[2]}원)');
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
			$(".shipping").children().val(" ");
	  }
	  
	  function radioClick (event) {
		$("#shippingCost").show();
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
	
	var userSession ='<%=session.getAttribute("userSession")%>';
	
	function wish() {
		if (userSession == 'null') {
			location.href="<c:url value='/user/loginForm' />";	// 로그인 안되어있으면 로그인 페이지로
		} else {
			location.href="<c:url value='/user/my/wish/gp/add?gpId=${joinReq.groupPurchase.gpId}' />";  // 로그인 상태면 wish 등록
		}
	}
	
	function delWish() {
		location.href="<c:url value='/user/my/wish/gp/delete?gpId=${joinReq.groupPurchase.gpId}&view=join' />";
	}
	
  </script>
  
  <style>
		.wishBtn {
			top: 400px;
			left: 320px;
			position: absolute;
			border: 2px solid;
			width: 70px;
			height: 70px;
			font-size: 18px;
			font-weight: bold;
			background-color: white;
			border-radius: 10px;
		}
		
		#noWish:hover {
			color: #FF6464;
			border-color: #ffeded;
		}
		
		#noWish {
			border-color: #d2d2d2;
			color: #a0a0a0;
		}
		
		#wish {
			border-color:#ffeded;
			color:#FF6464;
		}
		#wish:hover {
			color:#a0a0a0;
			border-color:#d2d2d2;
		}
		
  </style>
  
  <table class="n13" style="position: relative;  width:730px;">
  	<!-- 공구 정보 -->
  	<tr>
  		<td> <font class="color_purple" size="7"><b>공동구매 참여하기</b></font> </td>
  	</tr>
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr> <!-- rowspan : 이 칸 옆에 row(<tr>)가 몇 개까지 들어갈 수 있는지 -->
  		<td rowspan="6" align = "center" style="position: relative;">
  			<img id="image" class="img" src="<c:url value="${joinReq.groupPurchase.image}"/>">
  		</td>
  		<td>
  			<c:if test="${wishGp ne null}">
  				<button id="wish" class="wishBtn" type="button" onclick="delWish();">❤</button>
  			</c:if>
  			<c:if test="${wishGp eq null}">
  				<button id="noWish" class="wishBtn" type="button" onclick="wish();">❤</button>
  			</c:if>
  		</td>
  		
  		<td>
  			<a onClick="twitter()"><img style="width:30px;" src="<c:url value='../../images/icon-twitter.png'/>"></a>
  		</td>
  	</tr>
  	
  	<tr>
  		<td style="padding-bottom: 50; padding-top: 50;"> 공동구매 제목: ${joinReq.groupPurchase.title}  </td>
  	</tr>
  	<tr> 
  		<td style="padding-bottom: 50;"> 개당 가격: <a id="price">${joinReq.groupPurchase.price}</a> 원</td>
  	</tr>
  	<tr>
  	  	<td style="padding-bottom: 50;"> 주문 수량: 
  			<span style="position: relative;">
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
  		<td style="padding-bottom: 50;"> 총 상품금액 :
  			<a class="totalAmount">${joinReq.groupPurchase.price}</a> 원 
  			(<a class="quantity">${joinReq.quantity}</a>개)
  		</td>  
  	</tr>
  	
  	<tr>
  		<td style="padding-bottom: 50;"> 카테고리 :
  			<a>${joinReq.groupPurchase.category}</a> 
  		</td>  
  	</tr>
  	
  	<!-- 세부 항목 1 -->
    <tr>
      <td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>시작 일자</b></font> </td>
      <td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>종료 일자</b></font> </td>
    </tr>   
    <tr>
      <td>- ${joinReq.groupPurchase.startDate}</td>
      <td>- ${joinReq.groupPurchase.endDate}</td>
    </tr>   
  	
  	<!-- 상세 설명 -->
  	<tr>
      <td style="padding-top: 5%"> <font class="color_purple" size="4"><b>상세 설명</b></font> </td>
    </tr>
    <tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	<tr>
  		<td>${joinReq.groupPurchase.description}</td>
  	</tr>
  	
  	<c:if test="${userSession ne null}">
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
		<td id="shippingCost"><a>1800</a></td>
  	</tr>
  	
  	<tr>
		<td>배송비 포함 총액</td>
		<td id="totalShippingAmount"><a>*배송 방법을 선택해주세요</a></td>
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
      	<input type='radio' name='shippingOption' onclick='setAddress()' checked="checked"/> 주문자와 동일
      	<input type='radio' name='shippingOption' onclick='clearAddress()'/> 신규 배송지
      </td>
    </tr>
    
    <tr>
      <td>이름</td>
      <td class="shipping">
      	<form:input path="consumerName" placeholder="주문자 이름" value="${account.userName}" id="consumerName"/> 
        <form:errors path="consumerName"/></td>
    </tr>
    
    <tr>
      <td>휴대폰번호</td>
      <td class="shipping">
      	<form:input path="phone" placeholder="휴대폰번호" value="${account.phone}" id="phone"/>
        <form:errors path="phone"/></td>
    </tr>
    
    <tr>
      <td>우편번호</td>
      <td class="shipping">
      	<form:input path="zipcode" placeholder="우편번호" value="${account.zipcode}" id="zipcode"/> 
        <form:errors path="zipcode"/></td>
    </tr>
    <tr>
      <td>주소</td>
      <td class="shipping">
      	<form:input path="address" placeholder="주소" value="${account.address}" id="address"/> 
        <form:errors path="address"/></td>
    </tr>
    
    <tr>
      <td>배송시 요청사항</td>
      <td class="shipping">
      	<form:input path="shippingRequest" placeholder="배송시 요청사항(선택)"/> 
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
	  <p style="padding-top: 5%">
		<button class="hButton"> 등록하기 </button>
	  </p>
  </c:if>
  
</form:form>
</div>

<%-- <%@ include file="IncludeBottom.jsp"%> --%>
