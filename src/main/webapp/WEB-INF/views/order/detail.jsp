<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:if test="${not empty sessionScope.FLASH_ERR}">
    <div class="alert alert-danger">
        ${sessionScope.FLASH_ERR}
    </div>
    <c:remove var="FLASH_ERR" scope="session"/>
</c:if>


<h2>Order #${order.id}</h2>
<p>Status: ${order.status}</p>
<p>Total: ${order.total}</p>
<p>Created: ${order.createdAt}</p>

<table border="1" cellpadding="6">
  <tr><th>Product</th><th>Qty</th><th>Unit Price</th><th>Line Total</th></tr>
  <c:forEach var="it" items="${order.items}">
    <tr>
      <td>${it.productName}</td>
      <td>${it.qty}</td>
      <td>${it.unitPrice}</td>
      <td>${it.lineTotal}</td>
    </tr>
  </c:forEach>
</table>
<c:if test="${order.status == 'PLACED'}">
  <form method="post" action="${pageContext.request.contextPath}/orders/${order.id}/cancel"
        onsubmit="return confirm('Cancel this order?')">
    <button type="submit">Cancel</button>
  </form>
</c:if>

<a href="${pageContext.request.contextPath}/orders">Back</a>
