package db.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import model.User;

public class UserMemoryDatabase implements UserDatabase {
    private static final Map<String, User> users = new ConcurrentHashMap<>();
    private static Long sequenceId = 0L;

    public void addUser(User user) {
        user.setSequenceId(++sequenceId);
        users.put(user.getLoginId(), user);
    }

    public User findUserByLoginId(String loginId) {
        return users.get(loginId);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void clear() {
        users.clear();
        sequenceId = 0L;
    }
}
