<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h2>My Orders</h2>

<c:if test="${not empty sessionScope.FLASH_ERR}">
    <div class="alert alert-danger">
        ${sessionScope.FLASH_ERR}
    </div>
    <c:remove var="FLASH_ERR" scope="session"/>
</c:if>


<c:choose>
	<c:when test="${empty orders}">
		<p>You have no orders yet.</p>
	</c:when>
	<c:otherwise>
		<table border="1" cellpadding="6">
			<tr>
				<th>ID</th>
				<th>Status</th>
				<th>Total</th>
				<th>Created</th>
				<th>Action</th>
			</tr>
			<c:forEach var="o" items="${orders}">
				<tr>
					<td>
						<a href="${pageContext.request.contextPath}/orders/${o.id}">${o.id}</a>
					</td>
					<td>${o.status}</td>
					<td>${o.total}</td>
					<td>${o.createdAt}</td>
					<td>
						<c:if test="${o.status == 'PLACED'}">
							<form method="post" action="${pageContext.request.contextPath}/orders/${o.id}/cancel"
								onsubmit="return confirm('Cancel this order?')">
								<button type="submit">Cancel</button>
							</form>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>

