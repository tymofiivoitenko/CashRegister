package dao.product;

import connection.ConnectionPool;
import model.Product;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// This dao class provides CRUD database operations for the
// table "Product" in the database
public class MysqlProductDaoImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(MysqlProductDaoImpl.class);

    private static final String SELECT_PRODUCT_BY_ID = "SELECT id, name, email, country FROM Product WHERE id =?;";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM Product;";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM Product WHERE id = ?;";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE Product SET productName = ?,quantityInStock= ? where id = ?;";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO Product (productName, price ,quantityInStock) VALUES "
            + " (?, ?, ?);";

    public MysqlProductDaoImpl() {
    }

    public void insertProduct(Product product) {
        LOGGER.info("Inserting product " + product);

        // try-with-resource statement will auto close the connection.
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantityInStock());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Product> selectAllProducts() {
        LOGGER.info("Select all products");

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Product> products = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("productName");
                double price = rs.getDouble("price");
                int quantityInStock = rs.getInt("quantityInStock");

                products.add(new Product(id, name, price, quantityInStock));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    public boolean updateProduct(Product product) {
        LOGGER.info("Update product: " + product);

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL);
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getQuantityInStock());
            statement.setInt(3, product.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduct(int id) {
        LOGGER.info("Delete product by id: " + id);

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL);
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
