package dao.product;

import model.Product;
import java.util.List;

public interface ProductDao {

    void insertProduct(Product product);

    List<Product> selectAllProducts();

    boolean updateProduct(Product product);

    boolean deleteProduct(int id);
}
