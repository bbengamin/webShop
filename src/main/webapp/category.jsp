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
	<div class="filter">
		<form id="form-filter" action="route" method="get">
			<input type='hidden' name='limit' value='${condition.limit}'>
			<input type='hidden' name='sort' value='${condition.sort}'> <input
				type='hidden' name='order' value='${condition.order}'> <label>Price</label>
			<input class="filter-price" type="number" min=0 name="priceFrom"
				value="${(not empty condition.priceFrom ) ? condition.priceFrom:0}"><input
				class="filter-price" type="number" min=0 name="priceTo"
				value="${condition.priceTo}"> <br> <label>Name</label>
			<input class='filter-name' type="text" name="name"
				value="${condition.name}"> <br> <label>Categories</label>
			<div class="filter-checkbox">
				<c:forEach items="${pageBean.categories}" var="category">
					<c:set var="contains" value="false" />
					<c:forEach var="item" items="${condition.category}">
						<c:if test="${item eq category.id}">
							<c:set var="contains" value="true" />
						</c:if>
					</c:forEach>
					<input type="checkbox"
						<c:if test="${contains}"><c:out value="checked='checked'"/></c:if>
						class="category-checkbox" name="category" value="${category.id}">
					<p>${category.name}</p>
					<br>
				</c:forEach>
			</div>
			<label>Manufacturers</label>
			<div class="filter-checkbox">
				<c:forEach items="${pageBean.manufacturers}" var="manufacturer">
					<c:set var="contains" value="false" />
					<c:forEach var="item" items="${condition.manufacturer}">
						<c:if test="${item eq manufacturer.id}">
							<c:set var="contains" value="true" />
						</c:if>
					</c:forEach>
					<input type="checkbox"
						<c:if test="${contains}"><c:out value="checked='checked'"/></c:if>
						class="manufacturer-checkbox" name="manufacturer"
						value="${manufacturer.id}">
					<p>${manufacturer.name}</p>
					<br>
				</c:forEach>
			</div>
			<input id="filter-btn" type="submit" value="Filter"> <input
				id="filter-clear" type="button" value="Clear">
		</form>
	</div>
	<div id="content">
		<!-- Category -->
		<div class="category">
			<!-- <span class="category-title">Search result</span> -->
			<c:if test="${not empty pageBean.products}">
				<cp:sortsAndLimits />
				<cp:pagination bottom="false" />
				<c:forEach items="${pageBean.products}" var="product">
					<figure>
						<img class="category-img" src="${product.image}"
							alt="${product.name}" />
						<figcaption class="category-capt">${product.name}</figcaption>
						<span class='product-category'>Category -
							${pageBean.categories[product.categoryId-1].name}</span>
						<span class="product-manufacturer">Manufacturer -
							${pageBean.manufacturers[product.manufacturerId-1].name}</span>
						<div class='product-description'>
							<span>Description:</span>
							<p>${product.description}</p>
						</div>
						<div class="product-price">
							<fmt:formatNumber currencySymbol=" " value="${product.price }" type="currency" />
						</div>
						<img productId="${product.id }" class="addToCart" alt="add to cart" src="images/cart.png">
					</figure>
				</c:forEach>
				<cp:pagination bottom="true" />
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
<script>
	$('#filter-clear').click(function() {
		$('#form-filter input[name="priceFrom"]').val("");
		$('#form-filter input[name="priceTo"]').val("");
		$('#form-filter input[name="name"]').val("");
		$('#form-filter input[type="checkbox"]').attr('checked', false);
		$('#form-filter').submit();
	});
	
	$(".addToCart").click(function(){
		var product_id = $(this).attr("productId");
		$.ajax({
			url: 'cartManager',
			type: 'post',
			data: 'product_id=' + product_id + '&action=add',
			dataType: 'json',
			success: function(json) {
				$('#cart-total').text(json['total']); 					
				$('#cart-count').text(json['count']); 					
				$('html, body').animate({ scrollTop: 0 }, 'slow'); 
			}
		});
	});
</script>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />