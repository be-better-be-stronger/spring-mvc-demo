<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h2>Login</h2>

<c:if test="${not empty msg}">
  <div>${msg}</div>
</c:if>

<c:if test="${not empty error}">
  <div>${error}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/login">
    <input type="hidden" name="next" value="${next}" />

    <div>
        Email:
        <input type="email" name="email" value="${email}" required />
    </div>

    <div>
        Password:
        <input type="password" name="password" required />
    </div>

    <button type="submit">Login</button>
</form>
