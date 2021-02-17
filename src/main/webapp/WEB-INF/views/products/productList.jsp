<%--
  Created by IntelliJ IDEA.
  User: tymofiivoitenko
  Date: 02.02.21
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <title>Products</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/views/_navbar.jsp"></jsp:include>

<div class="container">

    <h3 class="text-center">List of Products</h3>
    <hr>

    <!-- Trigger the modal with a button -->
    <div class="container text-left">
        <a type="button" class="btn btn-success" id="addProductBtn">Add New Product</a>
    </div>

    <br>
    <!-- Table with all products from database-->
    <table cellpadding="3" cellspacing="2" border="2" class="text-center table table-striped table-hover">
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
                <td><a href="#editProduct_modal" data-toggle="modal"
                       data-product-id="${product.id}"
                       data-product-name="${product.name}"
                       data-product-price="$${product.price}"
                       data-product-quantity="${product.quantity}"
                       data-product-unit="${product.unit}">Edit</a> &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href=${pageContext.request.getRequestURI()}/delete?id=<c:out value='${product.id}'/>>Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>


    <ul class="pager">
        <c:choose>
            <c:when test="${page == 1}">
                <li class="previous"><a>Previous</a></li>
            </c:when>
            <c:otherwise>
                <li class="previous"><a href="${path}?pageSize=${pageSize}&page=${page-1}">Previous</a></li>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${page == maxPage}">
                <li class="next"><a>Next</a></li>
            </c:when>
            <c:otherwise>
                <li class="next"><a href="${path}?pageSize=${pageSize}&page=${page+1}">Next</a></li>
            </c:otherwise>
        </c:choose>
    </ul>


    <!-- Add products Modal -->
    <div class=" modal fade" id="addProductModal" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Add Product</h4>
                </div>

                <form class="form-horizontal" action="${pageContext.request.contextPath}/products/new" method="POST">
                    <div class="modal-body">

                        <jsp:include page="/WEB-INF/views/products/_submitProductForm.jsp"></jsp:include>

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">Add</button>
                        </div>
                    </div>

                </form>
            </div>
        </div>

    </div>

    <!-- Edit products Modal -->
    <div class="modal fade" id="editProduct_modal" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <form class="form-horizontal" action="${pageContext.request.contextPath}/products/edit" method="POST">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title" id="editProductTitle" name="productId">Edit Product #</h4>
                    </div>

                    <div class="modal-body">

                        <jsp:include page="/WEB-INF/views/products/_submitProductForm.jsp"></jsp:include>

                        <input type="hidden" name="productId">

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">Edit</button>
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

    $('#editProduct_modal').on('show.bs.modal', function (e) {
        var productId = $(e.relatedTarget).data('product-id');
        var productName = $(e.relatedTarget).data('product-name');
        var productPrice = $(e.relatedTarget).data('product-price');
        var productQuantity = $(e.relatedTarget).data('product-quantity');
        var productUnit = $(e.relatedTarget).data('product-unit');

        $(e.currentTarget).find('h4[id="editProductTitle"]').append(productId);
        $(e.currentTarget).find('input[name="productName"]').val(productName);
        $(e.currentTarget).find('input[name="productPrice"]').val(productPrice);
        $(e.currentTarget).find('input[name="productQuantity"]').val(productQuantity);
        $(e.currentTarget).find('select[id="product-unit-select"]').val(productUnit);
        $(e.currentTarget).find('input[name="productId"]').val(productId);
    });
</script>

</body>
</html>
