<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
		
		#noInterest:hover {
		    color: #FF6464;
		    border-color: #ffeded;
		}
		
		#noInterest {
			left: 45%;
    		top: 50%;
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
	});
	
	/*
	function interest() {
		if (ecoerId == 'null') {
			location.href="/Ecoding/user/loginform";
		} else {
			location.href="/Ecoding/project/interest?projectId=69";
		}
	}*/
</script>

<form:form modelAttribute="joinReq" action="${targetUrl}" method="post">
  <form:errors cssClass="error" /> <br><br>
  <table class="n13">
  	<!-- 상품 정보 -->
  	<tr>
  		<td> <font class="color_purple" size="8"><b>공동구매 상세 내역</b></font> </td>
  	</tr>
  	
  	<tr> <!-- div 또는 <hr> -->
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
  		<td colspan="3" align="right"> 등록 날짜: </td>
  	</tr>
  	
  	<tr> <!-- padding은 나중에 별도의 CSS 파일로 & 파일 경로 및 값은 product.name 등으로 접근 -->
  		<td rowspan="5"> <img id="noImage" src="<c:url value='../images/purchase/noImage.png'/>"> </td>
  		<td> <button id="noInterest" type="button" onclick="interest();">❤</button> </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 공동구매 이름 </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 공동구매 등록자 </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 진행 상태 </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 폼 등록 날짜 </td>
  	</tr>
  	
  	<!-- 세부 항목 1 -->
    <tr>
      <td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>공동구매 폼 보기</b></font> </td>
      <td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>등록자 정보</b></font> </td>
    </tr>   
    <tr>
      <td>- 마감 일자: (날짜)</td>
      <td>- 등록자: (등록자 이름)</td>
    </tr>   
    <tr>
      <td>- 공동구매 정보 바로가기></td>
      <td>- 등록자 프로필 바로가기></td>
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
        <td><form:input path="AccountHolder"/>
        <form:errors path="AccountHolder"/></td>
  	</tr>  
  	
  	<tr>
  		<td>입금은행</td>
        <td><form:input path="consumerName"/> 
        <form:errors path="consumerName"/></td>
  	</tr>
  	
  	<tr> <!-- 입금 완료 후에는 입금자명, 입금은행 변경불가 -->
		<td style="padding-bottom: 5%;">결제 상태</td>
		<td>(데이터)</td>
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
  		<td colspan="3" align="right"> <button class="btn">수정 내역 저장</button> </td>
  	</tr>
  	
  	<tr>
  		<td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>주문 정보</b></font> </td>
  	</tr>  	
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
		<td>상품 정보</td>
        <td>(데이터)</td>
  	</tr> 
  	<tr>
		<td>상품 금액</td>
        <td>(데이터)</td>
  	</tr> 
  	<tr>
		<td>배송 방법</td>
  	</tr> 
  	<tr> <!-- 입금 완료 이후에는 배송방법 수정 불가 -->
		<td colspan="2" align="center" style="padding-bottom: 5%;">
			<input type='radio' name='shippingOption' value='1' onclick='setShipping(event)'/> 준등기 (+1,800원)
      		<input type='radio' name='shippingOption' value='2' onclick='setShipping(event)'/> 택배 (+3,000원)
      		<input type='radio' name='shippingOption' value='3' onclick='setShipping(event)'/> 택배(제주산간) (+6,000원)
		</td>
		<td colspan="3" style="padding-bottom: 5%;"> <button class="btn">수정 내역 저장</button> </td>
  	</tr> 
  	<tr>
  		
  	</tr>  	
  	<tr>
		<td>합계</td>
		<td>(데이터)</td>
  	</tr>
  	<tr>
		<td>결제일</td>
        <td>(데이터)</td>
  	</tr>  
    
    <tr>
  		<td style="padding-top: 5%;" colspan="2"> <font class="color_purple" size="4"><b>배송 정보</b></font> </td>
  	</tr>
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
		<td>수령인</td>
        <td><form:input path="consumerName"/> 
        <form:errors path="consumerName"/></td>
  	</tr> 
  	<tr>
		<td>연락처</td>
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
    
    <tr>
  		<td colspan="3" align="right"> <button class="btn">수정 내역 저장</button> </td>
  	</tr>
    
    <tr> <!-- 거래 종료 이후 활성화되는 메뉴 -->
      <td style="padding-top: 5%;">이 거래가 만족스러우셨다면, 판매자에게 별점을 부여해주세요.</td>
      <td style="padding-top: 5%; padding-left: 5%;" align="right">
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
    
  </table>
</form:form>
</div>
</body>
</html>

<%-- <%@ include file="IncludeBottom.jsp"%> --%>
