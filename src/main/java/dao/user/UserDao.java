package dao.user;

import model.User;

public interface UserDao {
    // Find a User by userName
    User findUser(String userName, String password);
}
