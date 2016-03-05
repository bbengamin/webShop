<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="isLogin" required="true"%>
<fmt:setBundle basename="resources"/>
<c:if test="${!isLogin}">
	<c:set var="url">${pageContext.request.requestURL}</c:set>
	<c:if test="${not fn:contains(url, 'login.jsp')}">
		<div id="login-popup">
			<span id="login-title">Login</span><br />
			<form id="login-form" action="login" method="post">
				<input type="email" placeholder="Enter email"
					value="${sessionScope.email}" name="email" /> <input
					type="password" placeholder="Enter password" name="password" /> <input
					type="submit" id="login-submit" value="Log in" />
			</form>
		</div>
	</c:if>
	<div id="login">
		<c:if test="${not fn:contains(url, 'login.jsp')}">
			<div id="login-btn">
				<span id='btn-text'><a><fmt:message key="LOGIN" /></a></span>
			</div>
		</c:if>
		<div id="register-btn">
			<span id='btn-text'><a href="register"><fmt:message key="REGISTER"  /></a></span>
		</div>
	</div>
</c:if>
<c:if test="${isLogin}">
	<div id="login">
		<span id="hello-message">Hi, ${user.firstName}</span><br />
	 	<img id='avatar' alt="avatar" src="${user.image}">
	 	<a id="logout" href="logout">Logout</a>
	</div>
</c:if>