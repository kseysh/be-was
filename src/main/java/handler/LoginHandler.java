package handler;

import db.config.DatabaseConfig;
import db.cache.SessionManager;
import db.UserDatabase;
import enums.HttpHeader;
import enums.HttpStatus;
import exception.HttpException;
import exception.NotFoundException;
import exception.UnauthorizedException;
import http.converter.Form;
import http.converter.HttpMessageConverter;
import http.converter.HttpMessageConverterMapper;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Collections;
import java.util.Optional;
import model.User;
import webserver.view.StaticResourceView;
import webserver.view.View;

public class LoginHandler extends AbstractHandler {

    private static final UserDatabase userDatabase = DatabaseConfig.userDatabase;

    @Override
    protected void get(HttpRequest request, HttpResponse response) throws HttpException {
        View view = new StaticResourceView("/login/index.html");
        view.render(Collections.emptyMap(), request, response);
    }

    @Override
    protected void post(HttpRequest request, HttpResponse response) throws HttpException {
        HttpMessageConverter<Form> converter =
                HttpMessageConverterMapper.findHttpMessageConverter(Form.class, request.getContentType());
        Form<String, String> form = converter.read(request);

        String userId = form.get("userId");
        String password = form.get("password");

        User user = login(userId, password);

        String sessionId = SessionManager.getInstance().setAttribute(user);

        response.setStatusCode(HttpStatus.FOUND)
                .setHeader(HttpHeader.LOCATION.getValue(), "/")
                .setCookie(sessionId, "/");
    }

    private User login(String userId, String password) throws HttpException {
        Optional<User> optionalUser = userDatabase.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User Not Found");
        }

        User user = optionalUser.get();

        if (!user.matchesPassword(password)) {
            throw new UnauthorizedException("password not match");
        }

        return user;
    }
}
