package db.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import model.User;

public class UserMemoryDatabase implements UserDatabase {
    private static final Map<String, User> users = new ConcurrentHashMap<>();
    private static Long sequenceId = 0L;

    static {
        users.put("sangchu", new User("sangchu", "123", "상추", "sangchu@gmail.com"));
    } // 테스트를 위한 추가

    public void addUser(User user) {
        user.setSequenceId(++sequenceId);
        users.put(user.getLoginId(), user);
    }

    public User findUserByLoginId(String userId) {
        return users.get(userId);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void clear() {
        users.clear();
    }
}
