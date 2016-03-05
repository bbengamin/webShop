<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<div id="register">
	<form id="login-form" action="login" method="post">
		<input type='hidden' name="action" value='${param.action}'>
		<input type="email" placeholder="Enter email"
			value="${requestScope.form.email}" name="email" /> <label
			class="error" id="firstNameError">${requestScope.errors.email }</label>
		<input type="password" placeholder="Enter password" name="password" />
		<label class="error" id="firstNameError">${requestScope.errors.password }</label>
		<label class="error" id="firstNameError">${requestScope.errors.login_error }</label>
		<input type="submit" id="login-submit" value="Log in" />
	</form>
</div>