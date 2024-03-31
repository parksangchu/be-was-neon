package webserver.requesthandler.handlermapper;

import static utils.DIContainer.getArticleDatabase;
import static utils.DIContainer.getUserDatabase;
import static webserver.requesthandler.URLConst.ARTICLE_FORM_URL;
import static webserver.requesthandler.URLConst.ARTICLE_URL;
import static webserver.requesthandler.URLConst.COMMENT_URL;
import static webserver.requesthandler.URLConst.HOME_URL;
import static webserver.requesthandler.URLConst.LOGIN_URL;
import static webserver.requesthandler.URLConst.LOGOUT_URL;
import static webserver.requesthandler.URLConst.REGISTRATION_URL;
import static webserver.requesthandler.URLConst.USER_LIST_URL;

import java.util.HashMap;
import java.util.Map;
import webserver.requesthandler.handlerimpl.ArticleFormHandler;
import webserver.requesthandler.handlerimpl.ArticleHandler;
import webserver.requesthandler.handlerimpl.CommentHandler;
import webserver.requesthandler.handlerimpl.HomeHandler;
import webserver.requesthandler.handlerimpl.LoginHandler;
import webserver.requesthandler.handlerimpl.LogoutHandler;
import webserver.requesthandler.handlerimpl.RegistrationHandler;
import webserver.requesthandler.handlerimpl.RequestHandler;
import webserver.requesthandler.handlerimpl.StaticResourceHandler;
import webserver.requesthandler.handlerimpl.UserListHandler;
import webserver.requesthandler.http.HttpRequest;

public class RequestHandlerMapper {
    private static final Map<String, RequestHandler> store = new HashMap<>();

    static {
        store.put(HOME_URL, new HomeHandler(getArticleDatabase()));
        store.put(REGISTRATION_URL, new RegistrationHandler(getUserDatabase()));
        store.put(LOGIN_URL, new LoginHandler(getUserDatabase()));
        store.put(LOGOUT_URL, new LogoutHandler());
        store.put(ARTICLE_FORM_URL, new ArticleFormHandler(getArticleDatabase()));
        store.put(ARTICLE_URL, new ArticleHandler(getArticleDatabase()));
        store.put(COMMENT_URL, new CommentHandler());
        store.put(USER_LIST_URL, new UserListHandler(getUserDatabase()));
    }

    public static RequestHandler findRequestHandler(HttpRequest request) {
        return store.getOrDefault(request.getURL(), new StaticResourceHandler());
    }
}
