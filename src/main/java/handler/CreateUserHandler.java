package handler;

import db.Database;
import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpMethod;
import enums.HttpStatus;
import exception.BadRequestException;
import exception.HttpException;
import exception.MethodNotAllowedException;
import exception.UnsupportedMediaTypeException;
import http.converter.Form;
import http.converter.FormHttpMessageConverter;
import http.converter.HttpMessageConverter;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserHandler implements Handler {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserHandler.class);
    private static final CreateUserHandler INSTANCE = new CreateUserHandler();

    private CreateUserHandler() {
    }

    public static CreateUserHandler getInstance() {
        return INSTANCE;
    }

    public void handle(HttpRequest request, HttpResponse response) throws HttpException {
        if (request.getMethod() == HttpMethod.POST) {
            post(request, response);
        } else {
            throw new MethodNotAllowedException("Not Supported Method");
        }
    }

    private static void post(HttpRequest request, HttpResponse response) throws HttpException {
        HttpMessageConverter<Form<String, String>> converter = new FormHttpMessageConverter();
        if(!converter.canRead(Form.class, request.getContentType())){
            logger.warn("Not Supported Method");
            throw new UnsupportedMediaTypeException("Not Supported ContentType");
        }
        Form<String, String> form = converter.read(request);

        String userId = form.get("userId");
        String password = form.get("password");
        String name = form.get("name");
        String email = form.get("email");

        validateParameters(userId, password, name);

        User user = new User(userId, password, name, email);
        Database.addUser(user);
        logger.info("User added: {}", user);

        response.setVersion(request.getVersion());
        response.setStatusCode(HttpStatus.FOUND);
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeader.LOCATION.getValue(), "/index.html");
        headers.put(HttpHeader.CONTENT_TYPE.getValue(), ContentTypes.TEXT_HTML.getMimeType());
        response.setHeaders(headers);
    }

    private static void validateParameters(String userId, String password, String name)
            throws HttpException {
        if (userId == null || password == null || name == null) {
            throw new BadRequestException("parameters are missing");
        }
    }
}
