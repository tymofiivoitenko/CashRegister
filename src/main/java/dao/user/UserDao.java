package dao.user;

import bean.User;

public interface UserDao {
    // Find a User by userName
    User findUser(String userName, String password);
}
