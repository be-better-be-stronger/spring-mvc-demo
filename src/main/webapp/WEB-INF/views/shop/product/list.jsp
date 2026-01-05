<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Products</title>
</head>
<body>

	<h2>Product List</h2>

	<c:if test="${not empty msg }">${msg }</c:if>

	<c:if test="${not empty sessionScope.FLASH_ERR}">
		<div class="alert alert-danger">${sessionScope.FLASH_ERR}</div>
		<c:remove var="FLASH_ERR" scope="session" />
	</c:if>


	<!-- =========================
     FILTER FORM
     ========================= -->
	<form method="get" action="${pageContext.request.contextPath}/products">

		<!-- Category -->
		<select name="categoryId" onchange="this.form.submit()">
			<option value="">-- All categories --</option>
			<c:forEach var="c" items="${categories}">
				<option value="${c.id}" ${c.id == filter.categoryId ? "selected" : ""}>${c.name}</option>
			</c:forEach>
		</select>

		<!-- Keyword -->
		<input type="text" name="keyword" placeholder="Search..." value="${fn:escapeXml(filter.keyword)}" />

		<!-- Sort -->
		<select name="sort">
			<option value="name" ${pr.sort() == 'name' ? 'selected' : ''}>Name</option>
			<option value="price" ${pr.sort() == 'price' ? 'selected' : ''}>Price</option>
		</select>

		<!-- Direction -->
		<select name="dir">
			<option value="asc" ${pr.dir() == 'asc' ? 'selected' : ''}>ASC</option>
			<option value="desc" ${pr.dir() == 'desc' ? 'selected' : ''}>DESC</option>
		</select>

		<!-- Size -->
		<select name="size">
			<c:forEach var="s" items="${[6,12,24,48]}">
				<option value="${s}" ${pr.size() == s ? 'selected' : ''}>${s}</option>
			</c:forEach>
		</select>

		<button type="submit">Filter</button>
	</form>

	<hr />

	<!-- =========================
     LIST
     ========================= -->
	<c:choose>
		<c:when test="${page.totalItems == 0}">
			<p>No products found.</p>
		</c:when>

		<c:otherwise>
			<table border="1" cellpadding="8" cellspacing="0">
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Price</th>
					<th>Action</th>
				</tr>

				<c:forEach var="p" items="${page.items}">
					<tr>
						<td>${p.id}</td>
						<td>
							<a style="text-decoration: none;" href="${pageContext.request.contextPath}/products/${p.id}">
								${p.name} </a>
						</td>
						<td>${p.price}</td>
						<td>
							<form method="post" action="${pageContext.request.contextPath}/cart/add">
								<input type="hidden" name="productId" value="${p.id}" />
								<input type="number" name="qty" value="1" min="1" />
								<button type="submit">Add to cart</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>

	<!-- =========================
     PAGINATION
     ========================= -->
	<c:if test="${page.totalPages > 1}">
		<c:forEach var="i" begin="1" end="${page.totalPages}">

			<c:url var="pageUrl" value="/products">
				<c:param name="page" value="${i}" />
				<c:param name="size" value="${page.size}" />
				<c:param name="sort" value="${pr.sort()}" />
				<c:param name="dir" value="${pr.dir()}" />

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
