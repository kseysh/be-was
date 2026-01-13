package handler;

import db.Database;
import db.SessionManager;
import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpMethod;
import enums.HttpStatus;
import exception.HttpException;
import exception.MethodNotAllowedException;
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

    public LoginHandler() {
    }

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
                .setVersion(request.getVersion())
                .setCookie(sessionId, "/")
                .setHeader(HttpHeader.CONTENT_TYPE.getValue(), ContentTypes.TEXT_HTML.getMimeType());
    }

    private User login(String userId, String password) throws HttpException {
        Optional<User> optionalUser = Database.findUserByUserId(userId);

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
