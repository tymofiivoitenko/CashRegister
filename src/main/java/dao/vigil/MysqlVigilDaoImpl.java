package dao.vigil;

import connection.DBManager;
import model.Vigil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MysqlVigilDaoImpl implements VigilDao {
    private static final Logger LOGGER = Logger.getLogger(MysqlVigilDaoImpl.class);
    private static final String STATUS_STARTED = "STARTED";
    private static final String STATUS_FINISHED = "FINISHED";

    private static final String INSERT_VIGIL = "INSERT INTO vigils (user_id, start_date, status) VALUES ( ?, NOW(), ?);";
    private static final String UPDATE_FINISH_VIGIL = "UPDATE vigils SET status = ?, end_date = NOW() WHERE id = ?;";
    private static final String SELECT_VIGIL_WITH_GIVEN_STATUS = "SELECT v.id, v.start_date, users.username, v.status " +
            "FROM vigils v " +
            "INNER JOIN users ON v.user_id = users.id " +
            "WHERE v.user_id = ? AND status = ?;";

    @Override
    public void startVigil(int userId) {
        LOGGER.info("Start new Vigil for the user: <" + userId + ">");

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VIGIL);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, STATUS_STARTED);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Vigil getActiveVigil(int userId) {
        LOGGER.info("Get active Vigil for the user: <" + userId + ">");

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_VIGIL_WITH_GIVEN_STATUS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, "STARTED");

            ResultSet rs = preparedStatement.executeQuery();

            // Process the ResultSet object. Get first vigil, which is active
            if (rs.next()) {
                int id = rs.getInt("id");

                //String cashier = rs.getString("cashier");
                Date startDate = rs.getDate("start_date");
                String userName = rs.getString("username");
                String status = rs.getString("status");

                return new Vigil(id, startDate, status);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void finishVigil(int vigilId) {
        LOGGER.info("Finish vigil: " + vigilId+ ". Set new status: <" + STATUS_FINISHED + "> " +
                "and end date = now()");

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FINISH_VIGIL);
            preparedStatement.setString(1, STATUS_FINISHED);
            preparedStatement.setInt(2, vigilId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
