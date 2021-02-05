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
    <title>Products</title>
</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<jsp:include page="/views/_menu.jsp"></jsp:include>
<body>

<div class="container">

    <h3 class="text-center">List of Products</h3>
    <hr>
    <div class="container text-left">
        <a href="<%=request.getRequestURI()%>/new" class="btn btn-success">Add New Product</a>
    </div>
    <br>

    <table cellpadding="2" cellspacing="2" border="1" class="table table-striped table-bordered">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price $</th>
            <th>Quantity In Stock</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td>${product.productName}</td>
                <td>${product.price}</td>
                <td>${product.quantityInStock}</td>
                <td><a href="=request.getRequestURI()/edit?id=<c:out value='${product.id}' />">Edit</a> &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="=request.getRequestURI()/delete?id=<c:out value='${product.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
