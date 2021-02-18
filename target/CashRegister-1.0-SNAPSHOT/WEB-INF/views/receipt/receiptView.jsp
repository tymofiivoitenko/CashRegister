<%--
  Created by IntelliJ IDEA.
  User: tymofiivoitenko
  Date: 12.02.21
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Receipt Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/views/_navbar.jsp"></jsp:include>

<div class="container">
    <h3 class="text-center">Receipt</h3>
    <hr>

    <form class="form-horizontal" action="${pageContext.request.contextPath}/receipt" method="POST">
        <div class="container text-left">
            <a class="btn btn-su" href="${pageContext.request.contextPath }/catalog" role="button">Continue Shopping</a>
        </div>

        <table cellpadding="2" cellspacing="2" border="1" cellspacing="2"
               class="text-center table table-striped table-hover">
            <tr>
                <th class="text-center">Option</th>
                <th class="text-center">Id</th>
                <th class="text-center">Name</th>
                <th class="text-center">Price</th>
                <th class="text-center">Quantity</th>
                <th class="text-center">Sub Total</th>
            </tr>
            <c:set var="total" value="0"></c:set>

            <c:forEach var="receiptItem" items="${sessionScope.receipt}">
                <c:set var="total" value="${total + receiptItem.product.price * receiptItem.quantity }"></c:set>
                <tr>
                    <td align="center">
                        <a href="${pageContext.request.contextPath }/receipt?action=remove&id=${receiptItem.id}">Remove</a>
                    </td>
                    <td class="id">${receiptItem.id}</td>
                    <td style="width: 55%">${receiptItem.product.name}</td>
                    <td class="price">${receiptItem.product.price}</td>
                    <td>
                        <div class="col-sm-9">
                            <input type="number" step="0.1" class="quantity" value="${receiptItem.quantity}" name="qty"/>
                        </div>
                    </td>
                    <td class="total_price">$${receiptItem.product.price * receiptItem.quantity}</td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="5" align="right">Total</td>
                <td class="grandTotal">${total }</td>
            </tr>
        </table>
        <div class="container text-right">
            <button type="submit" class="btn btn-success">Submit receipt</button>
        </div>
    </form>
</div>

<script>
    $('body').on('change', ".quantity", applyChanges);

    function applyChanges() {
        var qty = parseFloat($(this).val());
        var id = $(this).parents('tr').find(".id").text();
        window.open('${pageContext.request.contextPath }/receipt?action=applyChanges&id=' + id + '&quantity=' + qty, "_self");
    }
</script>
</body>
</html>
