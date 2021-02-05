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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<jsp:include page="/views/_menu.jsp"></jsp:include>


<div class="container">

    <h3 class="text-center">List of Products</h3>
    <hr>

    <!-- Trigger the modal with a button -->
    <div class="container text-left">
        <a type="button" class="btn btn-success" id="addProductBtn">Add New Product</a>
    </div>
    <br>

    <!-- Table with all products from database-->
    <table cellpadding="3" cellspacing="2" border="2" class="table table-striped table-hover">
        <tr class="text-center">
            <th >Id</th>
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
                <td>
                    <a href=${pageContext.request.getRequestURI()}/edit?id=<c:out value='${product.id}'/>>Edit</a> &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href=${pageContext.request.getRequestURI()}/delete?id=<c:out value='${product.id}'/>>Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <!-- Add products Modal -->
    <div class="modal fade" id="addProductModal" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Add Product</h4>
                </div>

                <form class="form-horizontal" action="${pageContext.request.contextPath}/products/new" method="POST">
                    <div class="modal-body">

                        <div class="form-group">
                            <label class="col-sm-3 col-form-label">Product name</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="productName"
                                       placeholder="Enter product name"
                                       value="${product.productName}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 col-form-label">Price</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="price" placeholder="Enter price"
                                       value="${product.price}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 col-form-label">Quantity</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="quantityInStock"
                                       placeholder="Enter quantity"
                                       value="${product.quantityInStock}">
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">Add</button>
                        </div>
                    </div>

                </form>
            </div>
        </div>

    </div>

</div>

<script>
    $(document).ready(function () {
        $("#addProductBtn").click(function () {
            $("#addProductModal").modal();
        });
    });
</script>

</body>
</html>
