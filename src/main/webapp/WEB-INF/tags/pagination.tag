<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="bottom" required="true"%>
<div class="pagination"
	<c:if test="${bottom}"><c:out value="id=bottom-pagination" /></c:if>>
	<c:forEach var="entry" items="${pageBean.pagination}" varStatus="loop">
		<c:set var="flag" value="false" />
		<c:if test="${entry.key != 'Next' && entry.key != 'Prev'}">
			<c:if test="${entry.key eq condition.page}">
				<c:set var="flag" value="true" />
			</c:if>
		</c:if>
		<a <c:if test="${flag}"><c:out value="class=active" /></c:if>
			href='${entry.value}'>${entry.key}</a>
	</c:forEach>
</div>