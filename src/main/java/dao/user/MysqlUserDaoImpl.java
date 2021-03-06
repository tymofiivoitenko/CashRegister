package dao.user;

import bean.User;
import connection.DBManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MysqlUserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(MysqlUserDaoImpl.class);
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE username = ? AND password = ?";

    // Find a User by userName
    public User findUser(String userName, String password) {
        LOGGER.info("Find user by username: <" + userName + "> and password");

        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            // Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String role = rs.getString("role");
                return new User(id, userName, password, role);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
