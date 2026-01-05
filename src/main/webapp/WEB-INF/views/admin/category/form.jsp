<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Category Form</title>
</head>
<body>

<h2>
  <c:choose>
    <c:when test="${mode == 'edit'}">Edit Category</c:when>
    <c:otherwise>Create Category</c:otherwise>
  </c:choose>
</h2>

<c:if test="${not empty error}">
  <p style="color:red; font-style: italic;">${error}</p>
</c:if>

<c:url var="baseUrl" value="/admin/categories" />

<c:choose>
  <c:when test="${mode == 'edit'}">
    <form method="post" action="${baseUrl}/${id}">
      <p>ID: <b>${id}</b></p>
      <p>
        Name:
        <input name="name" value="${name}" />
      </p>
      <button type="submit">Update</button>
    </form>
  </c:when>

  <c:otherwise>
    <form method="post" action="${baseUrl}">
      <p>
        Name:
        <input name="name" />
      </p>
      <button type="submit">Create</button>
    </form>
  </c:otherwise>
</c:choose>

<p>
  <a href="${baseUrl}">Back to list</a>
</p>

</body>
</html>
