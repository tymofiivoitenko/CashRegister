package dao.user;

import bean.UserAccount;
import connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao {
    private static final String SELECT_USER_BY_ID = "SELECT username, password, role FROM user WHERE username = ?" +
            "AND password = ?";

    // Find a User by userName
    public static UserAccount findUser(String userName, String password) {

        // try-with-resource statement will auto close the connection.
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            // Process the ResultSet object.
            while (rs.next()) {
                String role = rs.getString("role");
                return new UserAccount(userName, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
