<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
	});
	
	function confirmAccount() {
		alert("계좌번호는 ${product.bank} ${product.account} 입니다.");
	}
	
	/*
	function interest() {
		if (ecoerId == 'null') {
			location.href="/Ecoding/user/loginform";
		} else {
			location.href="/Ecoding/project/interest?projectId=69";
		}
	}*/
</script>

<form:form modelAttribute="purchaseReq" action="${targetUrl}" method="post">
  <form:errors cssClass="error"/> <br><br>
  <table class="n13">
  	<!-- 상품 정보 -->
  	<tr>
  		<td> <font class="color_purple" size="8"><b>상품 상세 내역</b></font> </td>
  	</tr>
  	
  	<tr> <!-- div 또는 <hr> -->
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
  		<td colspan="3" align="right"> 등록 날짜: </td>
  	</tr>
  	
  	<tr> <!-- padding은 나중에 별도의 CSS 파일로 & 파일 경로 및 값은 product.name 등으로 접근 -->
  		<td rowspan="6"> <img id="noImage" src="<c:url value='../../images/purchase/noImage.png'/>"> </td>
  		<td> <button id="noInterest" type="button" onclick="interest();">❤</button> </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 상품 이름: ${product.productName} </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 상품 가격: ${purchaseReq.totalAmount} </td>
  		<td> <input type="button" value="판매자 계좌 확인" onClick="confirmAccount()"> </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 거래 지역: </td>
  	</tr>
  	<tr>
  		<td style="padding-bottom: 10;"> 거래 상태: ${purchaseReq.status} </td>
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
      <td>- 판매자: (판매자 이름)</td>
    </tr>  
    <tr>
      <td>- 장르: (장르 이름)</td>
      <td>- 판매중인 상품: n개</td>
    </tr>  
    <tr>
      <td>- 발매 연도: (날짜)</td>
      <td>- 판매자 프로필 바로가기></td>
    </tr>
    
    <!-- 세부 항목 2 -->
    <tr>
  		<td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>상품 정보</b></font> </td>
  	</tr>
  	
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
      	<td> ${product.description} </td> <!-- 표현식으로 받아오기 -->
    </tr>  
  	
  	<tr>
  		<td style="padding-top: 5%;"> <font class="color_purple" size="4"><b>내 거래 정보</b></font> </td>
  	</tr>
  	
  	<tr>
  		<td> <div class="color_purple" style="height: auto; width: 170%; border-top:1px solid; margin-bottom: 5%;"></div> </td>
  	</tr>
  	
  	<tr>
    	<td>- 거래 방식: 직거래 / 택배</td>
    </tr> 
    
    <tr>
  		<td>- 배송비: 별도 / 없음 </td>
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
    
  </table>
</form:form>
</div>
</body>
</html>

<%-- <%@ include file="IncludeBottom.jsp"%> --%>
