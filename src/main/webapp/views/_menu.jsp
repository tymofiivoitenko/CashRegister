<%--
  Created by IntelliJ IDEA.
  User: tymofiivoitenko
  Date: 03.02.21
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

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
            <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
            <li><a href="${pageContext.request.contextPath}/login">
                <span class="glyphicon glyphicon-log-in"></span> Login</a></li>
        </ul>
    </div>
</nav>


