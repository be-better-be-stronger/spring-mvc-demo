<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Access Denied</title>
</head>
<body>

	<h2>Access Denied</h2>

	<p>You do not have permission to access this page.</p>

	<c:if test="${not empty message}">
		<p>
			<strong>Reason:</strong> ${message}
		</p>
	</c:if>

	<hr>

	<p>
		<a href="${pageContext.request.contextPath}/products">Back to Home</a>
	</p>

	<p>
		<a href="${pageContext.request.contextPath}/login?next=${next}">Login with another account</a>
	</p>

</body>
</html>
