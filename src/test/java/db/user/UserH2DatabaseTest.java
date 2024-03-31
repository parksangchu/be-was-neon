package db.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserH2DatabaseTest {
    UserDatabase userDatabase;

    @BeforeEach
    void setUp() {
        userDatabase = new UserH2Database();
        userDatabase.clear();
        userDatabase.addUser(new User("sangchu", "123", "상추", "sangchu@gmail.com"));
    }

    @Test
    @DisplayName("로그인 ID로 DB에 저장된 user를 찾을 수 있다.")
    void findUserByLoginId() {
        User findUser = userDatabase.findUserByLoginId("sangchu");
        assertThat(findUser.getSequenceId()).isNotNull();
        assertThat(findUser.getLoginId()).isEqualTo("sangchu");
        assertThat(findUser.getName()).isEqualTo("상추");
        assertThat(findUser.getPassword()).isEqualTo("123");
        assertThat(findUser.getEmail()).isEqualTo("sangchu@gmail.com");
    }

    @Test
    @DisplayName("DB에 일치하는 로그인 ID가 없다면 null을 반환한다.")
    void findNullByLoginId() {
        User baechu = userDatabase.findUserByLoginId("baechu");
        assertThat(baechu).isNull();
    }

    @Test
    @DisplayName("DB에 저장된 모든 User를 반환한다.")
    void findAll() {
        userDatabase.addUser(new User("baechu", "123", "배추", "baechu@gmail.com"));
        List<User> users = userDatabase.findAll();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getLoginId()).isEqualTo("sangchu");
        assertThat(users.get(1).getLoginId()).isEqualTo("baechu");
    }

    @Test
    @DisplayName("DB에 로우가 존재하지 않는다면 빈 리스트를 반환한다.")
    void findAllEmtpy() {
        userDatabase.clear();
        assertThat(userDatabase.findAll()).isEmpty();
    }
}