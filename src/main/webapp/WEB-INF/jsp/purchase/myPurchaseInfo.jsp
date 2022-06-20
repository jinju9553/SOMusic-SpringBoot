<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="targetUrl"><c:url value="/purchase/info/${purchaseInfoReq.purchaseId}" /></c:set>

<html>
<meta charset="UTF-8">
<title>내가 구매한 상품</title>
<head> 
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
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
		
	</style>
</head>

<body>
<div align="center">
<script type="text/javascript">
	$(document).ready(function() {
		$(".hidden").hide();
		
		$('.starRev span').click(function(){
			$(this).parent().children('span').removeClass('on');
			$(this).addClass('on').prevAll('span').addClass('on');
			return false;
		});
		
		var status = ${purchaseInfoReq.product.status};
		switch (status) { //0: 승인 전 & 1: 승인됨, 입금 대기 & 2: 입금 완료 & 3:배송 시작 & 4: 거래 완료
		  case 0:
			$(".statusAnchor").text('구매폼 승인 전');
			break;
		  case 1:
		    $(".statusAnchor").text('입금 대기');
		    break;
		  case 2:
			$(".statusAnchor").text('입금 확인');
		    break;
		  case 3:
			$(".statusAnchor").text('배송 시작');
		    break;
		  case 4:
			$(".statusAnchor").text('거래 완료');
			break;
		  default:
			$(".statusAnchor").text('참여 정보 없음');
		}
		
		var method = ${purchaseInfoReq.shippingMethod};
		switch (method) { //0: 직거래만 & 1: 택배만 & 2: 둘 다 가능 & 3: 기타
		  case 0:
			$(".methodAnchor").text('직거래');
		    break;
		  case 1:
			$(".methodAnchor").text('택배');
		    break;
		  case 2:
			$(".methodAnchor").text('둘 다 가능');
			break;
		  case 3: 
			$(".methodAnchor").text('기타');
			break;
		  default:
			$(".methodAnchor").text('정보 없음');
		}
		
		if (method == 0) { //직거래에서는 필요 X
			$(".methodAnchor").parent().parent().next().hide();
			$(".shippingMenu").hide();
		}
	});
	
	function confirmAccount() {
		alert("판매자의 계좌번호는 ${purchaseInfoReq.product.bank} ${purchaseInfoReq.product.account} 입니다.");
	}
</script>

<form:form modelAttribute="purchaseInfoReq" action="${targetUrl}" method="post">
	  <form:errors cssClass="error"/> <br><br>
	  <input type="hidden" id="productId" name="productId" value="${purchaseInfoReq.product.productId}"/>
  <table class="n13">
  	<!-- 상품 정보 -->
  	<tr>
  		<td> <font class="color_purple" size="8"><b>상품 상세 내역</b></font> </td>
  	</tr>
  	
  	<tr> <!-- div 또는 <hr> -->
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>

  	<tr>
  		<td rowspan="6"> <img id="noImage" src="<c:url value='../../images/purchase/noImage.png'/>"> </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 상품 이름: ${purchaseInfoReq.product.productName} </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 상품 금액: <fmt:formatNumber
                value="${purchaseInfoReq.totalAmount}" pattern="###,##0" /> 원
                <form:input type="hidden" path="totalAmount"/></td>
  		<td colspan="2" align="left">
  			<button class="btn" onClick="confirmAccount()">판매자 계좌 조회</button> 
  		</td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 거래 지역: </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 거래 상태: <a class="statusAnchor"></a></td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 상품 상태: ${purchaseInfoReq.product.condition} </td>
  	</tr>
  	
  	<!-- 세부 항목 1 -->
    <tr>
      <td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>아티스트 정보</b></font> </td>
      <td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>판매자 정보</b></font> </td>
    </tr>   
    <tr>
      <td>- 아티스트: ${purchaseInfoReq.product.artistName} </td>
      <td>- 판매자: ${purchaseInfoReq.product.sellerId} </td>
    </tr>  
    <tr>
      <td>- 발매 연도: (날짜)</td>
      <td>- 판매자 별점: ${userSession.account.rate}</td>
    </tr>
    
    <!-- 세부 항목 2 -->
    <tr>
  		<td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>상품 정보</b></font> </td>
  	</tr>
  	
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
      	<td> ${purchaseInfoReq.product.description} </td>
    </tr>  
  	
  	<tr>
  		<td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>내 거래 정보</b></font> </td>
  	</tr>
  	
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
    	<td>폼 작성일자: ${purchaseInfoReq.regDate}</td>
    </tr> 
  	
  	<tr>
    	<td>거래 방식: <a class="methodAnchor"></a>
    	<form:input type="hidden" path="shippingMethod"/></td>
    </tr> 
    
    <tr>
  		<td style="padding-bottom: 5%;">
	  		배송비: 
	  		<c:if test="${!empty purchaseInfoReq.product.shippingCost}">
	  			<fmt:formatNumber value="${purchaseInfoReq.product.shippingCost}" pattern="###,##0" /> 원 (상품 가격에 포함)
	  		</c:if>
	  		<c:if test="${empty purchaseInfoReq.product.shippingCost}"> 없음 </c:if>
  		</td>
  	</tr>
  	
  	<tr class="shippingMenu">
		<td>수령인</td>
        <td>
        <c:choose>
        	<c:when test="${purchaseInfoReq.product.status < 3}">
        		<form:input path="consumerName"/> 
        		<form:errors path="consumerName"/>
        	</c:when>
        	<c:otherwise>
        		<a> ${purchaseInfoReq.consumerName} </a>
        		<form:input type="hidden" path="consumerName"/>
        	</c:otherwise>
        </c:choose>
        
        </td>
  	</tr> 
  	
  	<tr class="shippingMenu">
		<td>연락처</td>
        <td>
        <c:choose>
        	<c:when test="${purchaseInfoReq.product.status < 3}">
        		<form:input path="phone"/>
        		<form:errors path="phone"/>
        	</c:when>
        	<c:otherwise>
        		<a> ${purchaseInfoReq.phone} </a>
        		<form:input type="hidden" path="phone"/>
        	</c:otherwise>
        </c:choose>
        </td>
  	</tr> 
  	
  	<tr class="shippingMenu">
      <td>우편번호</td>
      <td>
      <c:choose>
        <c:when test="${purchaseInfoReq.product.status < 3}">
        	<form:input path="zipcode"/> 
        	<form:errors path="zipcode"/>
        </c:when>
        <c:otherwise>
        	<a> ${purchaseInfoReq.zipcode} </a>
        	<form:input type="hidden" path="zipcode"/>
        </c:otherwise>
      </c:choose>
      </td>
    </tr>
    
  	<tr class="shippingMenu">
      <td>주소</td>
      <td>
      <c:choose>
        <c:when test="${purchaseInfoReq.product.status < 3}">
        	<form:input path="address"/> 
        	<form:errors path="address"/>
        </c:when>
        <c:otherwise>
        	<a> ${purchaseInfoReq.address} </a>
        	<form:input type="hidden" path="address"/>
        </c:otherwise>
      </c:choose>
      </td>
    </tr>
    
    <tr class="shippingMenu">
      <td>배송시 요청사항</td>
      <td>
      <c:choose>
        <c:when test="${purchaseInfoReq.product.status < 3}">
        	<form:input path="shippingRequest"/> 
        	<form:errors path="shippingRequest"/>
        </c:when>
        <c:otherwise>
        	<a> ${purchaseInfoReq.shippingRequest} </a>
        	<form:input type="hidden" path="shippingRequest"/>
        </c:otherwise>
      </c:choose>
      </td>
    </tr>
    
    <tr class="shippingMenu">
    	<c:if test="${purchaseInfoReq.product.status < 3}">
    		<td colspan="2" align="right"> <button class="btn">수정 내역 저장</button> </td>
    	</c:if>
  	</tr>
  	
  	<tr>
    	<td style="padding-top: 5%;" colspan="2" align="right">
    	<span style="color: red;"><b>*배송이 시작된 이후에는 수령인 및 배송지를 수정할 수 없습니다.</b>
    	</span></td>
  	</tr>
    
    <c:if test="${purchaseInfoReq.product.status eq 4}">
	    <tr> <!-- 거래 종료 이후 활성화되는 메뉴 -->
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
		  <td style="padding-top: 5%; colspan="2" align="left"> <button>등록</button> </td>
	    </tr> 
    </c:if> 
  </table>
</form:form>
</div>
</body>
</html>

<%-- <%@ include file="IncludeBottom.jsp"%> --%>
