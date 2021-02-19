<%--
  Created by IntelliJ IDEA.
  User: tymofiivoitenko
  Date: 02.02.21
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Catalog</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>


<body>

<jsp:include page="/WEB-INF/views/_navbar.jsp"></jsp:include>
<div class="container">

    <h3 class="text-center">List of Products</h3>
    <hr>

    <!-- Table with all products from database-->
    <table cellpadding="3" cellspacing="2" border="2"
           class="text-center table table-striped table-hover form-horizontal">
        <tr class="text-center">
            <th class="text-center" data-defaultsort="desc">Id</th>
            <th class="text-center" style="width: 60%">Product Name</th>
            <th class="text-center">Price $</th>
            <th class="text-center">Quantity</th>
            <th class="text-center">Unit</th>
            <th class="text-center">Actions</th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.quantity}</td>
                <td>${product.unit}</td>
                <td align="center">


                    <a href="${pageContext.request.contextPath }/receipt?&action=addItem&id=${product.id}">Buy</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <jsp:include page="/WEB-INF/views/products/_productsPaginationGroup.jsp"></jsp:include>
</div>

</body>
</html>
