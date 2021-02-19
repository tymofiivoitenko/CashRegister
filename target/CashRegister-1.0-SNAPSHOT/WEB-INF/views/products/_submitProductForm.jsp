<%--
  Created by IntelliJ IDEA.
  User: tymofiivoitenko
  Date: 03.02.21
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<div class="form-group">
    <label class="col-sm-3 col-form-label">Product name</label>
    <div class="col-sm-9">
        <input type="text" class="form-control" name="productName" placeholder="Enter product name">
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 col-form-label">Price</label>
    <div class="col-sm-9">
        <input type="text" class="form-control" name="productPrice" placeholder="Enter price">
    </div>
</div>


<div class="form-group col-md-6">
    <label for="inputCity">City</label>
    <input type="text" class="form-control" id="inputCity">
</div>
<div class="form-group col-md-4">
    <label for="inputState">State</label>
    <select id="inputState" class="form-control">
        <option selected>Choose...</option>
        <option>...</option>
    </select>
</div>

<div class="form-group">
    <label class="col-sm-3 col-form-label">Quantity</label>
    <div class="col-sm-9">

        <input type="text" class="form-control" name="productQuantity" placeholder="Enter quantity">


        <select id="product-unit-select" name="productUnit" class="btn btn-secondary dropdown-toggle">
            <option value="kg">kg</option>
            <option value="L">L</option>
            <option value="piece">piece</option>
            <option value="packs">packs</option>
        </select>
    </div>

</div>


