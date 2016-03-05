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
					<c:forEach items="${pageBean.products}" var="entry">
						<tr id="row-${entry.key.id}">
							<td style="width: 80px;"><img src="${entry.key.image}"
								alt="${entry.key.name}" /></td>
							<td class='name-td'>${entry.key.name}
							<span>Category -
									${pageBean.manufacturers[entry.key.manufacturerId-1].name}</span>
								<span>Manufacturer -
									${pageBean.categories[entry.key.categoryId-1].name}</span>
							</td>
							<td class='count-td'><span class="changeCount minus"
								onclick="minus('${entry.key.id }')"></span> <input
								onchange="manual('${entry.key.id }')"
								name="quantity-${entry.key.id }" size='3' class='cart-count'
								value="${entry.value}"> <span
								onclick="plus('${entry.key.id }')" class="changeCount plus"></span>
								<span onclick="removeProduct('${entry.key.id }')"
								class="removeProduct"></span></td>
							<td><fmt:formatNumber currencySymbol=" " value="${entry.key.price}"
									type="currency" /></td>
							<td id="total-${entry.key.id }"><fmt:formatNumber
									value="${entry.key.price * entry.value}" currencySymbol=" " type="currency" /></td>
						</tr>
					</c:forEach>
				</table>
				<div id="cart-page-total">
					Total: <span id="page-total-span">${pageBean.cartTotal}</span>
				</div>

				<a class='checkout-btn' href="newOrder">Сheckout</a>
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
<script type="text/javascript">
	function removeProduct(product_id) {
		$
				.ajax({
					url : 'cartManager',
					type : 'post',
					data : 'action=remove&product_id=' + product_id,
					dataType : 'json',
					success : function(json) {
						console.log(json);
						if (json['count'] > 0) {
							$('#cart-total').text(json['total']);
							$('#cart-count').text(json['count']);
							$('#page-total-span').text(json['total']);
							$('#row-' + product_id).detach();
						} else {
							$('#content').children().detach();
							$("#content")
									.html(
											'<div class="cart"><span id="empty">No such products</span></div>');
							$('#cart-total').text(json['total']);
							$('#cart-count').text(json['count']);
						}
					}
				});
	}
	function plus(product_id) {
		var quantity = parseInt($('input[name="quantity-' + product_id + '"]')
				.val());
		updateCount(product_id, quantity + 1);
	}
	function minus(product_id) {
		var quantity = parseInt($('input[name="quantity-' + product_id + '"]')
				.val());
		if (quantity > 1) {
			updateCount(product_id, quantity - 1);
		}
	}
	function manual(product_id) {
		var quantity = parseInt($('input[name="quantity-' + product_id + '"]')
				.val());
		if (quantity > 1) {
			updateCount(product_id, quantity);
		} else {
			updateCount(product_id, 1);
		}
	}
	function updateCount(product_id, quantity) {
		$.ajax({
			url : 'cartManager',
			type : 'post',
			data : 'action=update&product_id=' + product_id + "&quantity="
					+ quantity,
			dataType : 'json',
			success : function(json) {
				console.log(json);
				$('#cart-total').text(json['total']);
				$('#cart-count').text(json['count']);
				$('#page-total-span').text(json['total']);
				$('#total-' + product_id).text(json['productTotal']);
				$('input[name="quantity-' + product_id + '"]').val(
						json['productQuantity']);
			}
		});
	}
</script>
<!-- End Shell -->
<jsp:include page="/WEB-INF/jspf/footer.jspf" />