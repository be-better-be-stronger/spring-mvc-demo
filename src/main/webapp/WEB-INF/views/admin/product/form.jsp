<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Form</title>
</head>
<body>

	<h2>
		<c:choose>
			<c:when test="${form.id != null}">Edit Product</c:when>
			<c:otherwise>Create Product</c:otherwise>
		</c:choose>
	</h2>

	<!-- ===== GLOBAL ERRORS ===== -->
	<form:errors path="*" element="div" cssStyle="color:red" />

	<c:choose>
		<c:when test="${form.id != null}">
			<c:url var="formAction" value="/admin/products/${form.id}" />
		</c:when>
		<c:otherwise>
			<c:url var="formAction" value="/admin/products" />
		</c:otherwise>
	</c:choose>


	<form:form modelAttribute="form" method="post" action="${formAction}">
		<!-- nếu edit thì gửi id -->
		<c:if test="${form.id != null}">
			<form:hidden path="id" />
		</c:if>

		<!-- NAME -->
		<p>
			Name:
			<form:input path="name" />
			<br />
			<form:errors path="name" cssStyle="color:red" />
		</p>

		<!-- STOCK -->
		<p>
			Stock:
			<form:input path="stock" />
			<br />
			<form:errors path="stock" cssStyle="color:red" />
		</p>

		<!-- PRICE -->
		<p>
			Price:
			<form:input path="price" />
			<br />
			<form:errors path="price" cssStyle="color:red" />
		</p>

		<!-- CATEGORY -->
		<p>
			Category:
			<form:select path="categoryId">
				<form:option value="" label="-- choose --" />
				<!-- categories là List<Category> có getId() getName() -->
				<form:options items="${categories}" itemValue="id" itemLabel="name" />
			</form:select>
			<br />
			<form:errors path="categoryId" cssStyle="color:red" />
		</p>

		<button type="submit">
			<c:choose>
				<c:when test="${form.id != null}">Update</c:when>
				<c:otherwise>Create</c:otherwise>
			</c:choose>
		</button>

	</form:form>

	<p>
		<a href="${pageContext.request.contextPath}/admin/products">Back to list</a>
	</p>

</body>
</html>