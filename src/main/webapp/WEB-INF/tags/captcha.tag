<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ attribute name="captKey" required="true" %> 
<img alt="captcha"	src="captcha?captchaKey=${captKey}">
<input type="hidden" name="captchaKey" value="${captKey}">
<input type="text" name="captcha" placeholder="Enter captcha">
<label class="error">${sessionScope.errors.captcha }</label>
