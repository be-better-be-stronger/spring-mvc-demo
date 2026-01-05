<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>System Error</title>
</head>
<body>

	<h2>System Error</h2>

	<p>Something went wrong while processing your request.</p>

	<p>
		<strong>Error </strong> ${status}
	</p>

	<c:if test="${not empty message}">
		<p>
			<strong>Message:</strong> ${message}
		</p>
	</c:if>

	<hr>

	<p>
		<a href="${pageContext.request.contextPath}/">Back to Home</a>
	</p>

</body>
</html>
