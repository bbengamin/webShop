<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="localization">

	<select id="localization-select" onchange="changeLocale()">
		<c:forEach var="locale" items="${localization}" varStatus="loop">
			<c:choose>
				<c:when test="${pageContext.request.locale.language == locale.key}">
					<option selected value="${locale.key}">${locale.value}</option>
				</c:when>
				<c:otherwise>
					<option value="${locale.key}">${locale.value}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
</div>
<script>
	function changeLocale() {
		var newLocale = $('#localization-select').find('option:selected').val();
		var location = window.location.href;
		if (getParameterByName("lang")) {
			window.location = replaceUrlParam(location, "lang", newLocale);
		} else {
			if (location.indexOf("?") > 0) {
				window.location = location + "&lang=" + newLocale;
			} else {
				window.location = location + "?lang=" + newLocale;
			}
		}
	}
	function getParameterByName(name) {
		name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
				.exec(location.search);
		return results === null ? "" : decodeURIComponent(results[1].replace(
				/\+/g, " "));
	}
	function replaceUrlParam(url, paramName, paramValue) {
		var pattern = new RegExp('\\b(' + paramName + '=).*?(&|$)')
		if (url.search(pattern) >= 0) {
			return url.replace(pattern, '$1' + paramValue + '$2');
		}
		return url + (url.indexOf('?') > 0 ? '&' : '?') + paramName + '='
				+ paramValue
	}
</script>