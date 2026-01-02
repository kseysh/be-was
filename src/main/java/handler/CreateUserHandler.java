package handler;

import db.Database;
import enums.HttpHeader;
import enums.HttpMethod;
import enums.HttpStatus;
import exception.BadRequestException;
import exception.HttpException;
import exception.NotFoundException;
import http.HttpRequest;
import http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserHandler implements Handler {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserHandler.class);
    private static final CreateUserHandler INSTANCE = new CreateUserHandler();

    private CreateUserHandler() {}

    public static CreateUserHandler getInstance() {
        return INSTANCE;
    }

    public void handle(HttpRequest request, HttpResponse response) throws HttpException {
        if(request.getMethod() == HttpMethod.GET) {
            get(request, response);
        }else{
            throw new NotFoundException("Not Supported Method");
        }
    }

    private static void get(HttpRequest request, HttpResponse response) throws HttpException {
        Map<String, String> queries = request.getQuery();
        String userId = queries.get("userId");
        String password = queries.get("password");
        String name = queries.get("name");
        String email = queries.get("email");

        validateParameters(userId, password, name, email);

        User user = new User(userId, password, name, email);
        Database.addUser(user);
        logger.info("User added: {}", user);

        response.setVersion(request.getVersion());
        response.setStatusCode(HttpStatus.FOUND);
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeader.LOCATION.getValue(), "/index.html");
        headers.put(HttpHeader.CONTENT_TYPE.getValue(), "text/html");
        response.setHeaders(headers);
    }

    private static void validateParameters(String userId, String password, String name, String email) throws HttpException {
        if(userId == null || password == null || name == null || email == null) {
            throw new BadRequestException("parameters are missing");
        }
    }
}
