package db.user;

import java.util.List;
import model.User;

public interface UserDatabase {
    void addUser(User user);

    User findUserById(String userId);

    List<User> findAll();

    void clear();
}
