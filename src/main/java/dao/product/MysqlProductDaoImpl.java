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
    private static final String SELECT_ALL_PRODUCTS_OLD = "SELECT * FROM Product;";
    private static final String SELECT_ALL_PRODUCTS = "" +
            "SELECT product.id, product.name, product.price, product.quantity, product_unit.symbol \n" +
            "FROM Product \n" +
            "INNER JOIN product_unit \n" +
            "    ON product_unit.id = product.unit;";

    private static final String UPDATE_PRODUCT_SQL = "Update Product \n" +
            "   SET name= ?,\n" +
            "   price = ?, \n" +
            "   quantity = ?,\n" +
            "   unit = (\n" +
            "       SELECT id\n" +
            "         FROM product_unit\n" +
            "        WHERE symbol = ?)\n" +
            "where id = ?;";

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO Product \n" +
            "   SET name = ?,\n" +
            "   price = ?,\n" +
            "   quantity = ?,\n" +
            "   unit = (\n" +
            "       SELECT id\n" +
            "         FROM product_unit\n" +
            "        WHERE symbol = ?);";

    private static final String DELETE_PRODUCT_SQL = "DELETE FROM Product WHERE id = ?;";

    public MysqlProductDaoImpl() {
    }

    public void insertProduct(Product product) {
        LOGGER.info("Inserting product " + product);

        // try-with-resource statement will auto close the connection.
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getUnit());

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
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantityInStock = rs.getInt("quantity");
                String measurementType = rs.getString("symbol");

                products.add(new Product(id, name, price, quantityInStock, measurementType));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    public boolean updateProduct(Product product) {
        LOGGER.info("Updating product: " + product);

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getQuantity());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, product.getUnit());
            statement.setInt(5, product.getId());

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
