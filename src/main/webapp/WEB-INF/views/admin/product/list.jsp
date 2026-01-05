<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product List</title>
<style>
.pager {
	margin: 0 6px;
	text-decoration: none;
	color: #0066cc;
}

.pager.disabled {
	color: #999;
	cursor: not-allowed;
	pointer-events: none; /* không click được */
}
</style>

</head>
<body>
	<h2>Product List</h2>

	<c:if test="${not empty sessionScope.FLASH_ERR}">
		<p style="color: red">${sessionScope.FLASH_ERR}</p>
		<c:remove var="FLASH_ERR" scope="session" />
	</c:if>

	<c:if test="${not empty success}">
		<p style="color: green">${success}</p>
	</c:if>
	
	<c:url var="adminProductsBaseUrl" value="/admin/products" />
	<!-- filter -->
	<form method="get" action="${adminProductsBaseUrl}">
		<select name="categoryId" onchange="this.form.submit()">
			<option value="">-- All --</option>
			<c:forEach var="c" items="${categories}">
				<option value="${c.id}" ${c.id == filter.categoryId ? "selected" : ""}>${c.name}</option>
			</c:forEach>
		</select> <label>Name keyword:</label>
		<input type="text" name="keyword" value="${filter.keyword}" />

		<!-- giữ trạng thái sort/dir/size khi user bấm Filter -->
		<input type="hidden" name="size" value="${page.size}" />
		<input type="hidden" name="sort" value="${sort}" />
		<input type="hidden" name="dir" value="${dir}" />

		<button type="submit">Filter</button>

	</form>



	<p>
		<a href="${adminProductsBaseUrl}/new" style="border: 1px solid blue; text-decoration: none;">
			+ New Product </a>
	</p>

	<table border="1" cellpadding="8">
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Stock</th>
			<th>Price</th>
			<th>Category</th>
			<th>Actions</th>
		</tr>

		<c:forEach var="p" items="${page.items}">
			<tr>
				<td>${p.id}</td>
				<td>${p.name}</td>
				<td>${p.stock}</td>
				<td>${p.price}</td>
				<td>${p.categoryName}</td>
				<td>
					<a href="${adminProductsBaseUrl}/${p.id}/edit"> Edit </a>

					<form action="${adminProductsBaseUrl}/${p.id}/delete" method="post" style="display: inline">
						<button type="submit" onclick="return confirm('Delete this product?')">Delete</button>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

	<!-- Pagination links (giữ query params) -->

	<c:if test="${page.totalPages > 1}">
		<c:forEach var="i" begin="1" end="${page.totalPages}">

			<c:url var="pageUrl" value="/admin/products">
				<c:param name="page" value="${i}" />
				<c:param name="size" value="${page.size}" />
				<c:param name="sort" value="${sort}" />
				<c:param name="dir" value="${dir}" />

				<c:if test="${filter.categoryId != null}">
					<c:param name="categoryId" value="${filter.categoryId}" />
				</c:if>

				<c:if test="${filter.keyword != null && not empty filter.keyword}">
					<c:param name="keyword" value="${filter.keyword}" />
				</c:if>
			</c:url>

			<c:choose>
				<c:when test="${i == page.page}">
					<strong>${i}</strong>
				</c:when>
				<c:otherwise>
					<a href="${pageUrl}">${i}</a>
				</c:otherwise>
			</c:choose>

		</c:forEach>
	</c:if>




</body>
</html>
