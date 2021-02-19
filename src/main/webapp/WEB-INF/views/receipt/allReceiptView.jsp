<%--
  Created by IntelliJ IDEA.
  User: tymofiivoitenko
  Date: 12.02.21
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>All Receipt</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/views/_navbar.jsp"></jsp:include>

<div class="container">

    <h3 class="text-center">All receipts available for - ${loginedUser.userName}</h3>
    <hr>

    <br>

    <c:choose>
        <c:when test="${not empty cashBox }">
            <div class="button-group text-right">
                <form action="${pageContext.request.contextPath}/receipt?&action=createReceipt" method="post">
                    <button type="submit" class="btn btn-success">Add new receipt</button>
                </form>
            </div>
        </c:when>
    </c:choose>

    <form class="form-horizontal" method="POST">
        <table cellpadding="3" cellspacing="2" border="2" class="text-center table table-striped table-hover">
            <tr class="text-center">
                <th class="text-center" data-defaultsort="desc">Id</th>
                <th class="text-center">Cashier</th>
                <th class="text-center">Created</th>
                <th class="text-center">Closed</th>
                <th class="text-center">Total</th>
                <th class="text-center">Status</th>
                <th class="text-center">Actions</th>
            </tr>
            <c:forEach var="receipt" items="${receipts}">
                <tr>
                    <td>${receipt.id}</td>
                    <td>${receipt.cashBox.user.userName}</td>
                    <td><fmt:parseDate value="${receipt.createdDate}" pattern="yyyy-MM-dd'T'HH:mm:ss"
                                       var="createdParsedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${createdParsedDateTime}"/></td>
                    <td><fmt:parseDate value="${receipt.closedDate}" pattern="yyyy-MM-dd'T'HH:mm:ss"
                                       var="closedParsedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${closedParsedDateTime}"/></td>
                    <td>$${receipt.totalPrice}</td>
                    <td>${receipt.status}</td>
                    <td></td>
                </tr>
            </c:forEach>
        </table>
    </form>

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

</div>

</body>
</html>
