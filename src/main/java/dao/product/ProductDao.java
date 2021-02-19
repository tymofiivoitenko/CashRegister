package dao.product;

import bean.Product;

import java.util.List;

public interface ProductDao {

    void insertProduct(Product product);

    List<Product> findAll();

    Product selectProduct(int id);

    boolean updateProduct(Product product);

    boolean deleteProduct(int id);

    int getProductsNumber();

    List<Product> findProducts(int page, int pageSize);

    boolean insertImage();
}
