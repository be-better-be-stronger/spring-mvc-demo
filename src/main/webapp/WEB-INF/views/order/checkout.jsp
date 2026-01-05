<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h2>Checkout</h2>

<c:if test="${not empty sessionScope.FLASH_ERR}">
    <div class="alert alert-danger">
        ${sessionScope.FLASH_ERR}
    </div>
    <c:remove var="FLASH_ERR" scope="session"/>
</c:if>


<c:if test="${empty cart.items}">
  <p>Cart is empty.</p>
  <a href="${pageContext.request.contextPath}/cart">Back to cart</a>
</c:if>

<table border="1" cellpadding="6">
  <tr>
    <th>Product</th><th>Qty</th><th>Unit Price</th><th>Line total</th>
  </tr>
  <c:forEach var="it" items="${cart.items}">
    <tr>
      <td>${it.productName}</td>
      <td>${it.qty}</td>
      <td>${it.unitPrice}</td>
      <td>${it.lineTotal}</td>
    </tr>
  </c:forEach>
</table>

<h3>Total ${totalQty} items: ${cart.grandTotal }</h3>

<form method="post" action="${pageContext.request.contextPath}/orders/place">
  <button type="submit">Place Order</button>
</form>
<a href="${pageContext.request.contextPath}/cart">Back to cart</a>
