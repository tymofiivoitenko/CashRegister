<%--
  Created by IntelliJ IDEA.
  User: tymofiivoitenko
  Date: 03.02.21
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="/" class="navbar-brand">Cash Register</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/admin">Admin</a></li>
            <li><a href="${pageContext.request.contextPath}/all-receipts">Receipts</a></li>
            <li><a href="${pageContext.request.contextPath}/reports">Reports</a></li>
            <li><a href="${pageContext.request.contextPath}/products">Products</a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right">

            <span style="color:red">[ ${loginedUser.userName} ]</span>

            <!-- If user hasn't pass authentication yet, then show Login button  -->
            <!-- Otherwise, show Logout button -->
            <c:choose>
                <c:when test="${empty loginedUser.userName}">

                    <li><a href="${pageContext.request.contextPath}/login">
                        <span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${loginedUser.role == 'CASHIER'}">
                            <form class="navbar-form navbar-left" action="${pageContext.request.contextPath}/vigil"
                                  method="post">
                                <c:choose>
                                    <c:when test="${empty vigil}">
                                        <button type="submit" class="btn btn-success"><span
                                                class="glyphicon glyphicon-play"></span>
                                            Start Vigil
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="btn btn-danger"><span
                                                class="glyphicon glyphicon-stop"></span>
                                            Stop Vigil
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </c:when>
                    </c:choose>
                    <li><a href="${pageContext.request.contextPath}/logout">
                        <span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </c:otherwise>
            </c:choose>

        </ul>
    </div>
</nav>


