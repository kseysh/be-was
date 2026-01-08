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
import java.util.Optional;
import model.User;
import util.FileReader;

public class LoginHandler implements Handler {

    private static final LoginHandler INSTANCE = new LoginHandler();

    private LoginHandler() {
    }

    public static LoginHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws HttpException {
        if (request.getMethod() == HttpMethod.GET) {
            get(request, response);
        } else if (request.getMethod() == HttpMethod.POST) {
            post(request, response);
        } else {
            throw new MethodNotAllowedException("Not Supported Method");
        }
    }

    private void get(HttpRequest request, HttpResponse response) throws HttpException {
        response.respondWithStaticFile(request.getVersion(), "/login/index.html");
    }

    private void post(HttpRequest request, HttpResponse response) throws HttpException {
        HttpMessageConverter<Form> converter =
                HttpMessageConverterMapper.findHttpMessageConverter(Form.class, request.getContentType());
        Form<String, String> form = converter.read(request);

        String userId = form.get("userId");
        String password = form.get("password");

        User user = login(userId, password);

        String sessionId = SessionManager.getInstance().setAttribute(user);

        response.setStatusCode(HttpStatus.OK)
                .setVersion(request.getVersion())
                .setCookie(sessionId, "/")
                .setHeader(HttpHeader.CONTENT_TYPE.getValue(), ContentTypes.TEXT_HTML.getMimeType())
                .setBody(FileReader.readFile("/index.html"));
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
