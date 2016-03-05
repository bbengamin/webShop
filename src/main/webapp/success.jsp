<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<jsp:include page="/WEB-INF/jspf/header.jsp" />
<%@ taglib tagdir="/WEB-INF/tags" prefix="cp"%>
<!-- Main -->
<div id="main" class="clearfix">
	<!-- Content -->
	<div id="content">
		<span id="register-title">Thank you</span>
		<div class="success-info">Your order №${info } is accepted for processing.</div>
		<a class='success-next' href="route" >Continue</a>
	</div>
	<!-- End Content -->
	<!-- Sidebar -->
	<div class="cl">&nbsp;</div>
</div>
<!-- End Main -->
</div>
<!-- End Shell -->
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
