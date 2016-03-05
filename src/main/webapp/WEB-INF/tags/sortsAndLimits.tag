<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="sorts-limits">

	<div id="sorts">
		<select
			onchange="window.location = $(this).find('option:selected').val();">
			<c:forEach var="entry" items="${pageBean.sorts}" varStatus="loop">
				<option value="${entry.value}"
					<c:if test="${(entry.key.name eq condition.sort) && (entry.key.order eq condition.order)}"><c:out value="selected" /></c:if>>${entry.key.text}</option>
			</c:forEach>
		</select>
	</div>
	<div id="limits">
		<div>
			<select
				onchange="window.location = $(this).find('option:selected').val();">
				<c:forEach var="entry" items="${pageBean.limits}" varStatus="loop">
					<option value="${entry.value}"
						<c:if test="${entry.key eq condition.limit}"><c:out value="selected" /></c:if>>${entry.key}</option>
				</c:forEach>
			</select>
		</div>
	</div>
</div>