package dao.cashBox;

import connection.DBManager;
import bean.CashBox;
import bean.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class MysqlCashBoxDaoImpl implements CashBoxDao {
    private static final Logger LOGGER = Logger.getLogger(MysqlCashBoxDaoImpl.class);
    private static final String STATUS_OPENED = "OPENED";
    private static final String STATUS_CLOSED = "CLOSED";

    private static final String INSERT_CASHBOX = "INSERT INTO cashboxes (user_id, start_date, status) VALUES ( ?, NOW(), ?);";
    private static final String UPDATE_FINISH_CASHBOX = "UPDATE cashboxes SET status = ?, closed_date = NOW() WHERE id = ?;";
    private static final String SELECT_CASHBOX_WITH_GIVEN_STATUS = "SELECT c.id, c.start_date, users.username, c.status " +
            "FROM cashboxes c " +
            "INNER JOIN users ON c.user_id = users.id " +
            "WHERE c.user_id = ? AND status = ?;";

    private static final String SELECT_CASHBOXES = "SELECT c.id, c.start_date, users.username, c.status " +
            "FROM cashboxes c " +
            "INNER JOIN users ON c.user_id = users.id " +
            "WHERE c.user_id = ? AND (status = ? OR status = ?);";

    @Override
    public void startCashBox(int userId) {
        LOGGER.info("Open new CashBox for the user: <" + userId + ">");

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CASHBOX);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, STATUS_OPENED);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public CashBox getActiveCashBox(int userId) {
        LOGGER.info("Get active CashBox for the user: <" + userId + ">");

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CASHBOX_WITH_GIVEN_STATUS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, STATUS_OPENED);

            ResultSet rs = preparedStatement.executeQuery();

            // Process the ResultSet object. Get first cashBox, which is active
            if (rs.next()) {
                int id = rs.getInt("id");

                LocalDateTime startDate = null;

                if (rs.getTimestamp("start_date") != null) {
                    startDate = rs.getTimestamp("start_date").toInstant()
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDateTime();
                }
                String userName = rs.getString("username");

                return new CashBox(id, new User(userName), startDate);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void getCashBoxes(int userId) {

    }

    @Override
    public void finishCashBox(int cashboxId) {
        LOGGER.info("Finish cashbox: " + cashboxId + ". Set new status: <" + STATUS_CLOSED + "> " +
                "and closed date = now()");

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FINISH_CASHBOX);
            preparedStatement.setString(1, STATUS_CLOSED);
            preparedStatement.setInt(2, cashboxId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
