<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Create Product</title>
</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<jsp:include page="/views/_menu.jsp"></jsp:include>

<body>
<div class="container">
    <h3 class="text-center">Add Product</h3>
    <form class="form-horizontal" method="post">
        <table style="with: 50%">
            <tr>
                <td>Product Name</td>
                <td><input type="text" name="productName"/></td>
            </tr>
            <tr>
                <td>Price $</td>
                <td><input type="text" name="price"/></td>
            </tr>
            <tr>
                <td>Quantity</td>
                <td><input type="text" name="quantity"/></td>
            </tr>
        </table>
        <input type="submit" value="Submit"/></form>
</div>
</body>
</html>