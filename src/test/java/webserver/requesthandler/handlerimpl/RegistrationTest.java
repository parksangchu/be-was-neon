package webserver.requesthandler.handlerimpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import db.user.UserDatabase;
import db.user.UserMemoryDatabase;
import java.util.Map;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.requesthandler.http.HttpRequest;
import webserver.requesthandler.http.HttpResponse;

class RegistrationTest {
    UserDatabase userDatabase = new UserMemoryDatabase();

    @Test
    @DisplayName("파라미터로 받은 정보로 회원을 생성하고 DB에 저장할 수 있다.")
    void createUser() {
        Map<String, String> params = Map.of("userId", "sangchu", "password", "password", "name", "상추", "email",
                "sangchu@gmail.com");
        HttpRequest request = new HttpRequest();
        request.setParameters(params);

        RegistrationHandler registrationHandler = new RegistrationHandler();
        registrationHandler.handlePost(request, new HttpResponse());

        User user = userDatabase.findUserByLoginId("sangchu");

        assertThat(user.getLoginId()).isEqualTo("sangchu");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("상추");
        assertThat(user.getEmail()).isEqualTo("sangchu@gmail.com");
    }

    @AfterEach
    void tearDown() {
        userDatabase.clear();
    }
}