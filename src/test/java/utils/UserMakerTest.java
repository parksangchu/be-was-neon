package utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import db.Database;
import java.util.Map;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpRequest;

class UserMakerTest {

    @Test
    @DisplayName("쿼리파라미터로 받은 정보로 회원을 생성하고 DB에 저장할 수 있다.")
    void createUser() {
        Map<String, String> params = Map.of("userId", "sangchu", "password", "password", "name", "상추", "email",
                "sangchu%40gmail.com");
        HttpRequest request = new HttpRequest();
        request.setParams(params);

        User user = UserMaker.createUser(request);
        assertThat(user.getUserId()).isEqualTo("sangchu");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("상추");
        assertThat(user.getEmail()).isEqualTo("sangchu@gmail.com");

        User sangchu = Database.findUserById("sangchu");
        assertThat(sangchu).isEqualTo(user);
    }
}