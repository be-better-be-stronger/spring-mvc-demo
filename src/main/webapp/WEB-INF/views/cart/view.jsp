<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<h2>My Cart</h2>

<c:if test="${not empty sessionScope.FLASH_ERR}">
    <div class="alert alert-danger">
        ${sessionScope.FLASH_ERR}
    </div>
    <c:remove var="FLASH_ERR" scope="session"/>
</c:if>


<c:choose>
  <c:when test="${empty cart.items}">
    <p>Cart is empty.</p>
    <p><a href="${pageContext.request.contextPath}/products">Continue shopping</a></p>
  </c:when>

  <c:otherwise>
    <table border="1" cellpadding="8" cellspacing="0">
      <thead>
        <tr>
          <th>Product</th>
          <th>Unit</th>
          <th>Qty</th>
          <th>Line total</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="it" items="${cart.items}">
          <tr>
            <td>${it.productName}</td>
            <td><fmt:formatNumber value="${it.unitPrice}" type="number" minFractionDigits="2" /></td>

            <td>
              <form method="post" action="${pageContext.request.contextPath}/cart/update">
                <input type="hidden" name="productId" value="${it.productId}" />
                <input type="number" name="qty" value="${it.qty}" min="0" />
                <button type="submit">Update</button>
              </form>
              <small>(qty=0 sẽ xóa)</small>
            </td>

            <td>
              <fmt:formatNumber value="${it.lineTotal}" type="number" minFractionDigits="2" />
            </td>

            <td>
              <form method="post" action="${pageContext.request.contextPath}/cart/remove">
                <input type="hidden" name="productId" value="${it.productId}" />
                <button type="submit">Remove</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

    <p>
      Total quantity: <b>${cart.totalQty}</b>
      &nbsp;|&nbsp;
      Grand total: <b><fmt:formatNumber value="${cart.grandTotal}" type="number" minFractionDigits="2" /></b>
    </p>

    <form method="post" action="${pageContext.request.contextPath}/cart/clear">
      <button type="submit">Clear cart</button>
    </form>
    
    <a href="${pageContext.request.contextPath}/orders/checkout">Checkout</a>

  </c:otherwise>
</c:choose>
