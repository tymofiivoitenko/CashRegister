package dao.receipt;

import bean.*;
import connection.DBManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class MysqlReceiptDaoImpl implements ReceiptDao {
    private static final Logger LOGGER = Logger.getLogger(MysqlReceiptDaoImpl.class);

    private static final String INSERT_RECEIPT = "INSERT INTO receipts (cashbox_id, created_date, status) VALUES ( ?, NOW(), ?);";
    private static final String INSERT_ITEM = "INSERT INTO receipt_items (product_id, quantity, receipt_id) " +
            "VALUES ( ?, ?, ?);";
    private static final String UPDATE_ITEM_QUANTITY = "UPDATE receipt_items SET quantity = ? WHERE id = ?;";
    private static final String UPDATE_RECEIPT_SET_STATUS = "UPDATE receipts SET status = ? WHERE id = ?;";
    private static final String UPDATE_CLOSE_RECEIPT = "UPDATE receipts SET closed_date = NOW(), status = ? WHERE id = ?;";
    private static final String DELETE_ITEM_SQL = "DELETE FROM receipt_items WHERE id = ?;";
    private static final String SELECT_RECEIPT_NUMBER = "SELECT COUNT(*) FROM receipts;";
    private static final String SELECT_LAST_INSERTED_RECEIPT_ID = "SELECT last_insert_id() as last_id from receipts;";
    private static final String SELECT_ALL_RECEIPTS = "SELECT rs.id, rs.cashbox_id, users.username, rs.created_date, " +
            "rs.closed_date, SUM(prs.price * ris.quantity) total_price, rs.status " +
            "FROM receipts rs " +
            "INNER JOIN receipt_items ris ON rs.id = ris.receipt_id " +
            "INNER JOIN cashboxes cbox ON rs.cashbox_id = cbox.id " +
            "INNER JOIN products prs ON ris.product_id = prs.id " +
            "INNER JOIN users ON cbox.user_id = users.id " +
            "GROUP BY rs.id " +
            "ORDER BY rs.created_date DESC;";

    private static final String SELECT_LIMITED_NUMBER_OF_RECEIPTS = "SELECT rs.id, rs.cashbox_id, users.username, rs.created_date, " +
            "rs.closed_date, SUM(prs.price * ris.quantity) total_price, rs.status " +
            "FROM receipts rs " +
            "INNER JOIN receipt_items ris ON rs.id = ris.receipt_id " +
            "INNER JOIN cashboxes cbox ON rs.cashbox_id = cbox.id " +
            "INNER JOIN products prs ON ris.product_id = prs.id " +
            "INNER JOIN users ON cbox.user_id = users.id " +
            "GROUP BY rs.id " +
            "ORDER BY rs.created_date DESC LIMIT ? OFFSET ?;";

    private static final String SELECT_ITEM_WITH_GIVEN_PRODUCT_ID = "SELECT products.name, products.price, products.unit, receipt_items.id, receipt_items.quantity " +
            "FROM receipt_items " +
            "INNER JOIN products ON products.id = receipt_items.product_id " +
            "WHERE receipt_id = ? AND product_id = ?;";
    private static final String SELECT_All_ITEMS_FOR_RECEIPT = "SELECT receipt_items.receipt_id, products.name, products.price, products.unit, receipt_items.id, receipt_items.quantity " +
            "FROM receipt_items " +
            "INNER JOIN products ON products.id = receipt_items.product_id " +
            "WHERE receipt_id = ?;";

    @Override
    public List<Receipt> findAll() {
        LOGGER.info("Getting list of all receipts ....");

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Receipt> receipts = new ArrayList<>();

        // Step 1: Establishing a Connection
        try (Connection connection = DBManager.getInstance().getConnection()) {

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RECEIPTS);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            receipts = retrieveReceiptsFromResultSet(rs);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return receipts;
    }

    @Override
    public int createReceipt(int cashBox_id) {
        LOGGER.info("Creating receipt ....");

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {

            // Insert Receipt
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RECEIPT);
            preparedStatement.setInt(1, cashBox_id);
            preparedStatement.setString(2, "CREATED");

            preparedStatement.executeUpdate();

            // Get just generated (AutoIncremented) receiptId
            preparedStatement = connection.prepareStatement(SELECT_LAST_INSERTED_RECEIPT_ID);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int receiptId = rs.getInt("last_id");
                return receiptId;
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public boolean addItemToReceipt(int receiptId, ReceiptItem receiptItem) {
        LOGGER.info("Adding Item: " + receiptItem + " into receipt: " + receiptId);

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ITEM);

            preparedStatement.setInt(1, receiptItem.getProduct().getId());
            preparedStatement.setInt(2, receiptItem.getQuantity());
            preparedStatement.setInt(3, receiptId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<ReceiptItem> getAllItemsForReceipt(int receiptId) {

        LOGGER.info("Select all items for receipt: " + receiptId);

        List<ReceiptItem> receiptItems = new ArrayList<>();

        // Using try-with-resources to avoid closing resources (boiler plate code)
        try (Connection connection = DBManager.getInstance().getConnection()) {

            // Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_All_ITEMS_FOR_RECEIPT);
            preparedStatement.setInt(1, receiptId);

            // Execute the query or update query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process Result Set
            receiptItems = retrieveItemsFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return receiptItems;
    }

    @Override
    public boolean setReceiptStatus(int receiptId, String status) {
        LOGGER.info("Set new status for receipt: <" + receiptId + "> = " + status);

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RECEIPT_SET_STATUS);

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, receiptId);


            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean closeReceipt(int receiptId) {
        LOGGER.info("Close receipt : <" + receiptId + ">");

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLOSE_RECEIPT);

            preparedStatement.setString(1, "CLOSED");
            preparedStatement.setInt(2, receiptId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getReceiptsNumber() {
        LOGGER.info("Counting total number of receipts ...");
        int receiptNumber = -1;

        try (Connection connection = DBManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_RECEIPT_NUMBER);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                receiptNumber = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return receiptNumber;
    }

    @Override
    public List<Receipt> findReceipts(int page, int pageSize) {
        List<Receipt> receipts = new ArrayList<>();

        try (Connection connection = DBManager.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_LIMITED_NUMBER_OF_RECEIPTS);

            statement.setInt(1, pageSize);
            statement.setInt(2, pageSize * (page - 1));
            ResultSet rs = statement.executeQuery();

            receipts = retrieveReceiptsFromResultSet(rs);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
        }

        return receipts;
    }

    @Override
    public boolean deleteItem(int itemId) {
        LOGGER.info("Delete item: " + itemId);

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ITEM_SQL);
            preparedStatement.setInt(1, itemId);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateItemQuantity(int itemId, int quantity) {
        LOGGER.info("Update item with: <" + itemId + "> set new quantity: " + quantity);

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ITEM_QUANTITY);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, itemId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ReceiptItem getReceiptItemByProduct(int receiptId, int productId) {
        LOGGER.info("Select all item from receipt: <" + receiptId + "> with product: ");

        ReceiptItem receiptItem = null;

        // Using try-with-resources to avoid closing resources (boiler plate code)
        try (Connection connection = DBManager.getInstance().getConnection()) {

            // Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM_WITH_GIVEN_PRODUCT_ID);
            preparedStatement.setInt(1, receiptId);
            preparedStatement.setInt(2, productId);

            // Execute the query or update query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process Result Set. Get the first item from list
            List<ReceiptItem> receiptItems = retrieveItemsFromResultSet(resultSet);

            // Check if receipt contains exactly 1 item with given product
            if (receiptItems.size() == 1) {
                receiptItem = receiptItems.get(0);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return receiptItem;
    }


    List<ReceiptItem> retrieveItemsFromResultSet(ResultSet rs) throws SQLException {
        List<ReceiptItem> receiptItems = new ArrayList<>();

        // Process the ResultSet object.
        while (rs.next()) {

            String productName = rs.getString("name");
            int price = rs.getInt("price");
            String unit = rs.getString("unit");
            int quantity = rs.getInt("quantity");
            int itemId = rs.getInt("id");

            Product product = new Product(productName, price, unit);
            receiptItems.add(new ReceiptItem(itemId, product, quantity));
        }

        return receiptItems;
    }

    List<Receipt> retrieveReceiptsFromResultSet(ResultSet rs) throws SQLException {
        List<Receipt> receipts = new ArrayList<>();

        // Process the ResultSet object.
        // Step 4: Process the ResultSet object.
        while (rs.next()) {
            int receiptId = rs.getInt("id");
            int cashBoxId = rs.getInt("cashbox_id");
            //int status = rs.getInt("status");
            String userName = rs.getString("userName");
            LocalDateTime createdDate = null;
            LocalDateTime closedDate = null;

            if (rs.getTimestamp("created_date") != null) {
                createdDate = rs.getTimestamp("created_date").toInstant()
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDateTime();
            }
            if (rs.getTimestamp("closed_date") != null) {
                closedDate = rs.getTimestamp("closed_date").toInstant()
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDateTime();
            }

            Double totalPrice = rs.getDouble("total_price");
            String status = rs.getString("status");

            CashBox cashBox = new CashBox(cashBoxId, new User(userName));

            receipts.add(new Receipt(receiptId, createdDate, closedDate, cashBox, totalPrice, status));
        }

        return receipts;
    }


}
