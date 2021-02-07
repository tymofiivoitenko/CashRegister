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
            <a class="navbar-brand">Cash Register</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/managerTask">Manager Task</a></li>
            <li><a href="${pageContext.request.contextPath}/employeeTask">Employee Task</a></li>
            <li><a href="${pageContext.request.contextPath}/userInfo">User Info</a></li>
            <li><a href="${pageContext.request.contextPath}/products">Products</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <span style="color:red">[ ${loginedUser.userName} ]</span>

            <!-- If user didn't pass authentication, then show Login button  -->
            <!-- Otherwise, show Logout button -->
            <c:choose>
                <c:when test="${empty loginedUser.userName}">
                    <li><a href="${pageContext.request.contextPath}/login">
                        <span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                </c:when>

                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/logout">
                        <span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>

