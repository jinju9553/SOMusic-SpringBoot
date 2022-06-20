<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="targetUrl"><c:url value="/join/info/${joinInfoReq.joinId}" /></c:set>

<html>
<meta charset="UTF-8">
<head> 
	<!-- 나중에 별도의 파일로 빼기 -->
	<style type="text/css">
		.starR1{
		    background: url('http://miuu227.godohosting.com/images/icon/ico_review.png') no-repeat -52px 0;
		    background-size: auto 100%;
		    width: 15px;
		    height: 30px;
		    float:left;
		    text-indent: -9999px;
		    cursor: pointer;
		}
		.starR2{
		    background: url('http://miuu227.godohosting.com/images/icon/ico_review.png') no-repeat right 0;
		    background-size: auto 100%;
		    width: 15px;
		    height: 30px;
		    float:left;
		    text-indent: -9999px;
		    cursor: pointer;
		}
		.starR1.on{background-position:0 0;}
		.starR2.on{background-position:-15px 0;}
		
		.img {
			width: 330px;
			height: 300px;
		}
	</style>
</head>

<body>
<div align="center">
<script type="text/javascript">
	$(document).ready(function() {
		//1.초기 이미지 세팅
		var imgSrc = "${joinInfoReq.groupPurchase.image}";
		  if (imgSrc == null) {
			  $("#image").attr("src", "<c:url value='../images/purchase/noImage.png'/>")
		  }
		  
		//2.별점 기능
		$('.starRev span').click(function(){
			$(this).parent().children('span').removeClass('on');
			$(this).addClass('on').prevAll('span').addClass('on');
			return false;
		});
		
		//3.status 값에 따른 표기
		var status = ${joinInfoReq.status};
		switch (status) { //1: 입금 대기 & 2: 입금 확인, 배송 대기중 & 3:배송 시작 & 4: 거래 완료
		  case 1:
		    $(".statusAnchor").text('입금 대기');
		    $(".payAnchor").text('입금 대기');
		    break;
		  case 2:
			$(".statusAnchor").text('입금 확인');
			$(".payAnchor").text('입금 완료');
		    break;
		  case 3:
			$(".statusAnchor").text('배송 시작');
			$(".payAnchor").text('입금 완료');
		    break;
		  case 4:
			$(".statusAnchor").text('거래 완료');
			$(".payAnchor").text('입금 완료');
			break;
		  default:
			$(".statusAnchor").text('참여 정보 없음');
		  	$(".payAnchor").text('참여 정보 없음');
		}
		
		//4.shippingMethod 값에 따른 텍스트 (*EL index에 변수 넣을 수 없음, foreach 불가)
		var shippingMethod =  ${joinInfoReq.shippingMethod};
		switch (shippingMethod) {
			case 1:
				$("#shippingText").text('${shippingText[0]}');
				break;
			case 2:
				$("#shippingText").text('${shippingText[1]}');
				break;
			case 3:
				$("#shippingText").text('${shippingText[2]}');
				break;
		}
		$("#shippingMethod1").next().text('${shippingText[0]}');
		$("#shippingMethod2").next().text('${shippingText[1]}');
		$("#shippingMethod3").next().text('${shippingText[2]}');
	});
</script>

<form:form modelAttribute="joinInfoReq" action="${targetUrl}" method="post">
  	<form:errors cssClass="error" /> <br><br>
  	<form:input type="hidden" path="groupPurchase" />
  	<form:input type="hidden" path="consumerBank"/>
  	<form:input type="hidden" path="status"/>
  <table class="n13">
  	<!-- 상품 정보 -->
  	<tr>
  		<td> <font class="color_purple" size="8"><b>공동구매 상세 내역</b></font> </td>
  	</tr>
  	
  	<tr> <!-- div 또는 <hr> -->
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
  		<td colspan="3" align="right"> 공동구매 등록 날짜: ${joinInfoReq.groupPurchase.startDate} </td>
  	</tr>
  
  	<tr>
  		<td rowspan="5"> <img id="image" class="img" src="<c:url value='${joinInfoReq.groupPurchase.image}'/>"> </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 공동구매 이름: ${joinInfoReq.groupPurchase.title} </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 공동구매 등록자: ${joinInfoReq.groupPurchase.sellerId} </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 진행 상태: <a class="statusAnchor"></a></td>
  	</tr>
  	<tr>
  		<td> 폼 작성 날짜: ${joinInfoReq.regDate} </td>
  	</tr>
  	
  	<!-- 세부 항목 1 -->
    <tr>
      <td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>공동구매 정보</b></font> </td>
      <td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>등록자 정보</b></font> </td>
    </tr>   
    <tr>
      <td>- 마감 일자: ${joinInfoReq.groupPurchase.endDate}</td>
      <td>- 등록자: ${joinInfoReq.groupPurchase.sellerId}</td>
    </tr>   
    <tr>
      <td>- <a href="/join/${joinInfoReq.groupPurchase.gpId}">공동구매 폼 바로가기></a></td>
      <td>- 등록자 별점: ${userSession.account.rate}</td>
    </tr>
    
    <!-- 세부 항목 2 -->
    <tr>
  		<td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>기본 정보</b></font> </td>
  	</tr>
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
		<td>입금자명</td>
        <td>
        <c:choose>
        	<c:when test="${joinInfoReq.status < 2}">
        		<form:input path="accountHolder"/>
        		<form:errors path="accountHolder"/>
        	</c:when>
        	<c:otherwise>
        		<a> ${joinInfoReq.accountHolder} </a>
        		<form:input type="hidden" path="accountHolder"/>
        	</c:otherwise>
        </c:choose>
        </td>
  	</tr>  
  	
  	<tr>
		<td>입금액</td>
		<td><fmt:formatNumber
                value="${joinInfoReq.totalAmount}" pattern="###,##0" /> 원
                <form:input type="hidden" path="totalAmount"/></td>
  	</tr> 
  	
  	<tr> <!-- status 2부터는 입금자명 수정 불가, 환불은 상시 가능 -->
		<td style="padding-bottom: 5%;">결제 상태</td>
		<td style="padding-bottom: 5%;"><a class="payAnchor"></a></td>
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
  		<td>환불 예금주명</td>
        <td><form:input path="refundHolder"/> 
        <form:errors path="refundHolder"/></td>
  	</tr>
  	
  	<tr>
  		<td colspan="2" align="right"> <button class="btn">수정 내역 저장</button> </td>
  	</tr>
  	
  	<tr>
  		<td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>주문 정보</b></font> </td>
  	</tr>  	
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>

  	<tr>
		<td>상품 금액</td>
        <td><fmt:formatNumber
                value="${joinInfoReq.groupPurchase.price}" pattern="###,##0" /> 원 × ${joinInfoReq.quantity} 개
                <form:input type="hidden" path="groupPurchase.price"/>
                <form:input type="hidden" path="quantity"/></td>
  	</tr> 
  	<tr>
		<td>배송 방법</td>
		<c:if test="${joinInfoReq.status >= 3}">
			<td id="shippingText">텍스트</td>
		</c:if>
  	</tr> 
  	<tr> 
    	<c:if test="${joinInfoReq.status < 3}">
        	<td colspan="2" align="center" style="padding-bottom: 5%;">
				<form:radiobuttons path="shippingMethod" items="${shippingOption}" /></td>
		</c:if>
		<form:input type="hidden" path="shippingMethod"/>
  	</tr> 
  	<tr>
  		<c:if test="${joinInfoReq.status < 3}">
  			<td colspan="2" align="right" style="padding-bottom: 5%;"> <button class="btn">수정 내역 저장</button> </td>
  		</c:if>
  	</tr>
  	<tr>
  		<td>배송비</td>
		<td><fmt:formatNumber
                value="${joinInfoReq.shippingCost}" pattern="###,##0" /> 원
                <form:input type="hidden" path="shippingCost"/></td>
  	</tr>  	
  	<tr>
		<td>총 주문 금액</td>
		<td><fmt:formatNumber
                value="${joinInfoReq.totalAmount}" pattern="###,##0" /> 원
                <form:input type="hidden" path="totalAmount"/></td>
  	</tr>
    
    <tr> <!-- status 3부터는 수정 불가 -->
  		<td style="padding-top: 5%;" colspan="2"> <font class="color_purple" size="4"><b>배송 정보</b></font> </td>
  	</tr>
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr class="shippingMenu">
		<td>수령인</td>
        <td>
        <c:choose>
        	<c:when test="${joinInfoReq.status < 3}">
        		<form:input path="consumerName"/> 
        		<form:errors path="consumerName"/>
        	</c:when>
        	<c:otherwise>
        		<a> ${joinInfoReq.consumerName} </a>
        		<form:input type="hidden" path="consumerName"/>
        	</c:otherwise>
        </c:choose>
        </td>
  	</tr> 
  	
  	<tr class="shippingMenu">
		<td>연락처</td>
        <td>
        <c:choose>
        	<c:when test="${joinInfoReq.status < 3}">
        		<form:input path="phone"/>
        		<form:errors path="phone"/>
        	</c:when>
        	<c:otherwise>
        		<a> ${joinInfoReq.phone} </a>
        		<form:input type="hidden" path="phone"/>
        	</c:otherwise>
        </c:choose>
        </td>
  	</tr> 
  	
  	<tr class="shippingMenu">
      <td>우편번호</td>
      <td>
      <c:choose>
        	<c:when test="${joinInfoReq.status < 3}">
        		<form:input path="zipcode"/> 
        		<form:errors path="zipcode"/>
        	</c:when>
        	<c:otherwise>
        		<a> ${joinInfoReq.zipcode} </a>
        		<form:input type="hidden" path="zipcode"/>
        	</c:otherwise>
      </c:choose>
      </td>
    </tr>
    
  	<tr class="shippingMenu">
      <td>주소</td>
      <td>
      <c:choose>
        	<c:when test="${joinInfoReq.status < 3}">
        		<form:input path="address"/> 
        		<form:errors path="address"/>
        	</c:when>
        	<c:otherwise>
        		<a> ${joinInfoReq.address} </a>
        		<form:input type="hidden" path="address"/>
        	</c:otherwise>
      </c:choose>
      </td>
    </tr>
    
    <tr class="shippingMenu">
      <td>배송시 요청사항</td>
      <td>
      <c:choose>
        	<c:when test="${joinInfoReq.status < 3}">
        		<form:input path="shippingRequest"/> 
      			<form:errors path="shippingRequest"/>
        	</c:when>
        	<c:otherwise>
        		<a> ${joinInfoReq.shippingRequest} </a>
        		<form:input type="hidden" path="shippingRequest"/>
        	</c:otherwise>
      </c:choose>    
      </td>
    </tr>
    
    <tr>
    	<c:if test="${joinInfoReq.status < 3}">
  			<td colspan="2" align="right"> <button class="btn">수정 내역 저장</button> </td>
  		</c:if>
  	</tr>
  	
  	<tr>
    	<td style="padding-top: 5%;" colspan="3" align="right">
    	<span style="color: red;"><b>*배송이 시작된 이후에는 수령인 및 배송지를 수정할 수 없습니다.</b>
    	</span></td>
  	</tr>
    
    <c:if test="${joinInfoReq.status eq 4}">
	    <tr> <!-- 거래 종료(status: 4) 이후 활성화되는 메뉴 -->
	      <td style="padding-top: 5%;">이 거래가 만족스러우셨다면, 판매자에게 별점을 부여해주세요.</td>
	      <td style="padding-top: 5%; padding-left: 3%;" align="right">
			<div class="starRev">
			  <span class="starR1 on"></span>
			  <span class="starR2"></span>
			  <span class="starR1"></span>
			  <span class="starR2"></span>
			  <span class="starR1"></span>
			  <span class="starR2"></span>
			  <span class="starR1"></span>
			  <span class="starR2"></span>
			  <span class="starR1"></span>
			  <span class="starR2"></span>
			</div>
		  </td>
	  	  <td style="padding-top: 5%; colspan="2" align="left"> <button class="btn">등록</button> </td>
	    </tr> 
    </c:if>
  </table>
</form:form>
</div>
</body>
</html>

<%-- <%@ include file="IncludeBottom.jsp"%> --%>
