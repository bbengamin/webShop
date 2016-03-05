<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cp"%>
<jsp:include page="/WEB-INF/jspf/header.jsp" />
<!-- Main -->
<div id="main">
	<!-- Content -->
	<div id="content">
		<!-- Category -->
		<div class="cart">
			<c:if test="${not empty pageBean.products}">
				<table class='cart-table'>
					<thead>
						<tr>
							<td colspan="2">Product</td>
							<td>Quantity</td>
							<td>Price</td>
							<td>Total</td>
						</tr>
					</thead>
					<c:forEach items="${pageBean.products}" var="item">
						<tr>
							<td style="width: 80px;"><img src="${item.product.image}"
								alt="${item.product.name}" /></td>
							<td  class='name-td'>${item.product.name}
								<span>Category -
									${pageBean.manufacturers[item.product.manufacturerId-1].name}</span>
								<span>Manufacturer -
									${pageBean.categories[item.product.categoryId-1].name}</span>
							</td>
							<td style='text-align: center;'>${item.quantity}</td>
							<td><fmt:formatNumber currencySymbol=" " value="${item.price}" type="currency" /></td>
							<td><fmt:formatNumber currencySymbol=" " value="${item.price * item.quantity}"
									type="currency" /></td>
						</tr>
					</c:forEach>
				</table>
				<div id="cart-page-total">
					Total: <span id="page-total-span"><fmt:formatNumber currencySymbol=" "
							value="${pageBean.total}" type="currency" /></span>
				</div>

				<form id="order-info" action="checkout" method='post'>
					<table style="width: 100%; color: white; font-size: 16px;">
						<tr>
							<td colspan="2"><span>Order information:</span>
							<td>
						</tr>
						<tr>
							<td class='order-td'><label>Your address: </label></td>
							<td>${pageBean.address}</td>
						</tr>
						<tr>
							<td><label>Payment method: </label></td>
							<td>${pageBean.paymentMethod}</td>
						</tr>
					</table>
					<input class='order-next' type='submit' value="Next">
				</form>
			</c:if>
			<c:if test="${empty pageBean.products}">
				<span id="empty">No such products</span>
			</c:if>
		</div>
		<!-- End Category -->
	</div>
	<!-- End Content -->
	<div class="cl">&nbsp;</div>
</div>
<!-- End Main -->
</div>
<!-- End Shell -->
<jsp:include page="/WEB-INF/jspf/footer.jspf" />