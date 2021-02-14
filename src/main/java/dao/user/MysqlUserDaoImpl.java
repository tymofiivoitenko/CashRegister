package dao.user;

import bean.UserAccount;
import connection.DBManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MysqlUserDaoImpl implements UserDao{
    private static final Logger LOGGER = Logger.getLogger(MysqlUserDaoImpl.class);
    private static final String SELECT_USER_BY_ID = "SELECT username, password, role FROM user WHERE username = ?" +
            "AND password = ?";

    // Find a User by userName
    public UserAccount findUser(String userName, String password) {
        LOGGER.info("Find user by username: <" + userName + "> and password");
        
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DBManager.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            // Process the ResultSet object.
            while (rs.next()) {
                String role = rs.getString("role");
                return new UserAccount(userName, password, role);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
