<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Category List</title>
</head>
<body>

	<h2>Category List</h2>
	<c:if test="${not empty success }">
		<p style="color: green; font-style: italic;">${success}</p>
	</c:if>
	<c:if test="${not empty error }">
		<p style="color: red; font-style: italic;">${error}</p>
	</c:if>
	<c:url var="baseUrl" value="/admin/categories" />

	<p>
		<a href="${baseUrl}/new"> + New Category </a>
	</p>

	<table border="1" cellpadding="8">
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Actions</th>
		</tr>

		<c:forEach var="c" items="${categories}">
			<tr>
				<td>${c.id}</td>
				<td>${c.name}</td>
				<td>
					<a href="${baseUrl}/${c.id}/edit"> Edit </a>

					<form action="${baseUrl}/${c.id}/delete" method="post" style="display: inline;">
						<button type="submit" onclick="return confirm('Delete this category?')">Delete</button>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>
