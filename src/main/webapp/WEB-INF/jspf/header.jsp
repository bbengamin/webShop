<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Shop Around</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/login-popup.css" type="text/css"
	media="all" />
<script src="js/jquery.js" type="text/javascript"></script>
</head>
<body>

	<!-- Shell -->
	<div class="shell">
		<!-- Header -->
		<div id="header-wrap">
			<div id="header">
				<!-- Log in -->
				<cp:login isLogin="${isLogin}" />

				<!-- End Log in -->
				<h1 id="logo">
					<a href="/webShop"></a>
				</h1>
				<!-- Cart -->
				<a href="cart">
					<div id="cart">
						<div id="cart_content">
							<span class="cart-link"></span>
							<div class="cl">&nbsp;</div>
							<c:choose>
								<c:when
									test="${sessionScope.cart.getTotalCountOfProducts() > 0}">
									<span>Count: <strong id='cart-count'>${sessionScope.cart.getTotalCountOfProducts() }</strong></span> &nbsp;&nbsp; <span><strong
										id="cart-total"><fmt:formatNumber
												value="${sessionScope.cart.total }" currencySymbol=" "
												type="currency" /></strong></span>
								</c:when>
								<c:otherwise>
									<span>Count: <strong id='cart-count'>0</strong></span> &nbsp;&nbsp; <span><strong
										id="cart-total"><fmt:formatNumber currencySymbol=" "
												value="0" type="currency" /></strong></span>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</a>
				<cp:localization />
				<!-- End Cart -->
				<div id="header-popup"></div>
			</div>
		</div>
		<!-- End Header -->