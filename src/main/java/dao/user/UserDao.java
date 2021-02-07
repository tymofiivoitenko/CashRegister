package dao.user;

import bean.UserAccount;

public interface UserDao {
    // Find a User by userName
    UserAccount findUser(String userName, String password);
}
