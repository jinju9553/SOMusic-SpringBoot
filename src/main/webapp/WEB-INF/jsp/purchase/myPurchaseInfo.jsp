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
		
		#noInterest:hover {
		    color: #FF6464;
		    border-color: #ffeded;
		}
		
		#noInterest {
			left: 45.5%;
    		top: 53.5%;
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

<body>
<div align="center">
<script type="text/javascript">
	$(document).ready(function() {
		$('.starRev span').click(function(){
			$(this).parent().children('span').removeClass('on');
			$(this).addClass('on').prevAll('span').addClass('on');
			return false;
		});
		
		var status = ${product.status};
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
		alert("판매자의 계좌번호는 ${product.bank} ${product.account} 입니다.");
	}
</script>

<form:form modelAttribute="purchaseInfoReq" action="${targetUrl}" method="post">
  <form:errors cssClass="error"/> <br><br>
  <table class="n13">
  	<!-- 상품 정보 -->
  	<tr>
  		<td> <font class="color_purple" size="8"><b>상품 상세 내역</b></font> </td>
  	</tr>
  	
  	<tr> <!-- div 또는 <hr> -->
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>

  	<tr> <!-- padding은 나중에 별도의 CSS 파일로 & 파일 경로 및 값은 product.image 등으로 접근 -->
  		<td rowspan="6"> <img id="noImage" src="<c:url value='../../images/purchase/noImage.png'/>"> </td>
  		<td> <button id="noInterest" type="button" onclick="interest();">❤</button> </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 상품 이름: ${product.productName} </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 상품 금액: <fmt:formatNumber
                value="${purchaseInfoReq.totalAmount}" pattern="###,##0" /> 원</td>
  		<td> <input type="button" value="판매자 계좌 확인" onClick="confirmAccount()"> </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 거래 지역: </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 거래 상태: <a class="statusAnchor"></a></td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 상품 상태: ${product.condition} </td>
  	</tr>
  	
  	<!-- 세부 항목 1 -->
    <tr>
      <td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>아티스트 정보</b></font> </td>
      <td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>판매자 정보</b></font> </td>
    </tr>   
    <tr>
      <td>- 아티스트: ${product.artistName} </td>
      <td>- 판매자: ${product.sellerId} </td> <!-- account 쪽에서 뽑아올 것 -->
    </tr>  
    <tr>
      <td>- 장르: (장르 이름)</td>
      <td>- 판매중인 상품: n개</td> <!-- 판매중인 상품 개수를 count해서 command 객체에만 넣어주기 -->
    </tr>  
    <tr>
      <td>- 발매 연도: (날짜)</td>
      <td>- 판매자 정보 바로가기></td>
    </tr>
    
    <!-- 세부 항목 2 -->
    <tr>
  		<td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>상품 정보</b></font> </td>
  	</tr>
  	
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
      	<td> ${product.description} </td>
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
    	<td>거래 방식: <a class="methodAnchor"></a></td>
    </tr> 
    
    <tr>
  		<td style="padding-bottom: 5%;">
	  		배송비: 
	  		<c:if test="${!empty product.shippingCost}">
	  			<fmt:formatNumber value="${product.shippingCost}" pattern="###,##0" /> 원 (상품 가격에 포함)
	  		</c:if>
	  		<c:if test="${empty product.shippingCost}"> 없음 </c:if>
  		</td>
  	</tr>
  	
  	<tr class="shippingMenu">
		<td>수령인</td>
        <td>
        <c:choose>
        	<c:when test="${product.status < 3}">
        		<form:input path="consumerName"/> 
        		<form:errors path="consumerName"/>
        	</c:when>
        	<c:otherwise>
        		<a> ${purchaseInfoReq.consumerName} </a>
        	</c:otherwise>
        </c:choose>
        
        </td>
  	</tr> 
  	
  	<tr class="shippingMenu">
		<td>연락처</td>
        <td>
        <c:choose>
        	<c:when test="${product.status < 3}">
        		<form:input path="phone"/>
        		<form:errors path="phone"/>
        	</c:when>
        	<c:otherwise>
        		<a> ${purchaseInfoReq.phone} </a>
        	</c:otherwise>
        </c:choose>
        </td>
  	</tr> 
  	
  	<tr class="shippingMenu">
      <td>우편번호</td>
      <td>
      <c:choose>
        <c:when test="${product.status < 3}">
        	<form:input path="zipcode"/> 
        	<form:errors path="zipcode"/>
        </c:when>
        <c:otherwise>
        	<a> ${purchaseInfoReq.zipcode} </a>
        </c:otherwise>
      </c:choose>
      </td>
    </tr>
    
  	<tr class="shippingMenu">
      <td>주소</td>
      <td>
      <c:choose>
        <c:when test="${product.status < 3}">
        	<form:input path="address"/> 
        	<form:errors path="address"/>
        </c:when>
        <c:otherwise>
        	<a> ${purchaseInfoReq.address} </a>
        </c:otherwise>
      </c:choose>
      </td>
    </tr>
    
    <tr class="shippingMenu">
      <td>배송시 요청사항</td>
      <td>
      <c:choose>
        <c:when test="${product.status < 3}">
        	<form:input path="shippingRequest"/> 
        	<form:errors path="shippingRequest"/>
        </c:when>
        <c:otherwise>
        	<a> ${purchaseInfoReq.shippingRequest} </a>
        </c:otherwise>
      </c:choose>
      </td>
    </tr>
    
    <tr class="shippingMenu">
    	<c:if test="${product.status < 3}">
    		<td colspan="3" align="right"> <button class="btn">수정 내역 저장</button> </td>
    	</c:if>
  	</tr>
  	
  	<tr>
    	<td style="padding-top: 5%;" colspan="3" align="right">
    	<span style="color: red;"><b>*배송이 시작된 이후에는 수령인 및 배송지를 수정할 수 없습니다.</b>
    	</span></td>
  	</tr>
  	
  	<tr>
  		<td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>상품 문의</b></font> </td>
  	</tr>
  	
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
      <td>다른 사용자가 문의 댓글을 작성하는 곳.</td>
    </tr> 
    
    <c:if test="${product.status eq 4}">
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
