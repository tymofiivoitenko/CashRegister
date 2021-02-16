package dao.product;

import connection.DBManager;
import model.Product;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// This dao class provides CRUD database operations for the
// table "Product" in the database
public class MysqlProductDaoImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(MysqlProductDaoImpl.class);

    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM products WHERE id =?;";
    private static final String INSERT_PICTURE = "INSERT INTO product_pictures VALUES(?, ?);";
    private static final String SELECT_ALL_PRODUCTS = "SELECT products.id, products.name, products.price, " +
            "products.quantity, product_units.symbol " +
            "FROM products " +
            "INNER JOIN product_units " +
            "    ON product_units.id = products.unit;";

    private static final String UPDATE_PRODUCT_SQL = "Update products SET name= ?, price = ?, quantity = ?, unit = (" +
            "(SELECT id " +
            "FROM product_units " +
            "WHERE symbol = ?) " +
            "WHERE id = ?;";

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products SET name = ?, price = ?, quantity = ?, " +
            "unit = (" +
            "  SELECT id " +
            "  FROM product_units " +
            "  WHERE symbol = ?);";

    private static final String DELETE_PRODUCT_SQL = "DELETE FROM products WHERE id = ?;";

    public MysqlProductDaoImpl() {
    }

    public void insertProduct(Product product) {
        LOGGER.info("Inserting product " + product);

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setDouble(3, product.getQuantity());
            preparedStatement.setString(4, product.getUnit());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Product> findAll() {
        LOGGER.info("Select all products");

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Product> products = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = DBManager.getInstance().getConnection()) {

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

    @Override
    public Product selectProduct(int id) {
        LOGGER.info("Find product by id: <" + id + ">");

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            // Process the ResultSet object.
            while (rs.next()) {
                String productName = rs.getString("name");
                Double price = rs.getDouble("price");
                Double quantity = rs.getDouble("quantity");
                String unit = rs.getString("unit");

                return new Product(id, productName, price, quantity, unit);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateProduct(Product product) {
        LOGGER.info("Updating product: " + product);

        try (Connection connection = DBManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getQuantity());
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

        try (Connection connection = DBManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL);
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean insertImage() {
        LOGGER.info("Insert image");

        try (Connection connection = DBManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_PICTURE);
            statement.setInt(1, 1);
            InputStream in = new FileInputStream("/Users/tymofiivoitenko/Downloads/product.png");
            statement.setBlob(2, in);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
}
