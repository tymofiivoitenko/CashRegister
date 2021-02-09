<%--
  Created by IntelliJ IDEA.
  User: tymofiivoitenko
  Date: 03.02.21
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<body>

<jsp:include page="../_navbar.jsp"></jsp:include>
<br class="container">
<h3 class="text-center">Login Page</h3>
<br>
<form class="form-horizontal" action="${pageContext.request.contextPath}/login" method="POST">
    <input type="hidden" name="redirectId" value="${param.redirectId}" />
    <div class="form-group">
        <label class="control-label col-sm-5">User Name:</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" name="userName" placeholder="Enter username"
                   value="${user.userName}">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-5">Password:</label>
        <div class="col-sm-3">
            <input type="password" class="form-control" name="password" placeholder="Enter password"
                   value="${user.password}">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-5 col-sm-3">
            <p style="color: red">${errorMessage}</p>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-5 col-sm-10">
            <button type="submit" class="btn btn-default">Login</button>
        </div>
    </div>
</form>
</div>
</body>
</html>