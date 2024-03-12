package utils;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;

public class UserMaker {
    private static final Logger logger = LoggerFactory.getLogger(UserMaker.class);

    public static User createUser(HttpRequest request) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));

        Database.addUser(user);
        logger.debug("신규 유저가 생성되었습니다. {}", user);
        return user;
    }
}
