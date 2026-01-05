<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Detail</title>
</head>
<body>

	<c:if test="${not empty sessionScope.FLASH_ERR}">
		<div class="alert alert-danger">${sessionScope.FLASH_ERR}</div>
		<c:remove var="FLASH_ERR" scope="session" />
	</c:if>


	<p>
		<a href="${pageContext.request.contextPath}${back}">← Back</a>
	</p>

	<h2>${fn:escapeXml(p.name)}</h2>

	<p>
		<b>ID:</b> ${p.id}
	</p>
	<p>
		<b>Category:</b> ${fn:escapeXml(p.categoryName)}
	</p>
	<p>
		<b>Price:</b> ${p.price}
	</p>
	<p>
		<b>Stock:</b> ${p.stock}
	</p>

</body>
</html>
