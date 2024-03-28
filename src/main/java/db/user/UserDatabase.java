package db.user;

import java.util.List;
import model.User;

public interface UserDatabase {
    void addUser(User user);
    
    User findUserByLoginId(String userId);

    List<User> findAll();

    void clear();
}
