<%--
  Created by IntelliJ IDEA.
  User: tymofiivoitenko
  Date: 03.02.21
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Info</title>
</head>
<body>

<jsp:include page="_navbar.jsp"></jsp:include>

<h3>Hello: ${loginedUser.userName}</h3>

User Name: <b>${loginedUser.userName}</b>
<br>


</body>
</html>