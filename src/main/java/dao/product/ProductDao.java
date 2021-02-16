package dao.product;

import model.Product;
import java.util.List;

public interface ProductDao {

    void insertProduct(Product product);

    List<Product> findAll();

    Product selectProduct(int id);

    boolean updateProduct(Product product);

    boolean deleteProduct(int id);

    boolean insertImage();
}
