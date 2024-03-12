package utils;

import db.Database;
import java.util.HashMap;
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMaker {
    private static final Logger logger = LoggerFactory.getLogger(UserMaker.class);

    public static User createUser(String url) {
        String[] parameters = url.split("\\?")[1].split("&");
        Map<String, String> paramMap = new HashMap<>();
        for (String parameter : parameters) {
            String[] splitedParam = parameter.split("=");
            paramMap.put(splitedParam[0], splitedParam[1]);
        }

        User user = new User(paramMap.get("userId"), paramMap.get("password"), paramMap.get("name"),
                paramMap.get("email"));
        logger.debug("신규 유저가 생성되었습니다. {}", user);

        Database.addUser(user);
        return user;
    }
}
