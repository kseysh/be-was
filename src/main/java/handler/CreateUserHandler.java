package handler;

import db.Database;
import enums.HttpHeader;
import enums.HttpMethod;
import enums.HttpStatus;
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

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        if(request.getMethod() == HttpMethod.GET) {
            get(request, response);
        }else{
            throw new NotFoundException("Not Supported Method");
        }
    }

    public void get(HttpRequest request, HttpResponse response){
        String userId = request.getQuery().get("userId");
        String password = request.getQuery().get("password");
        String name = request.getQuery().get("name");
        String email = request.getQuery().get("email");

        if (userId == null || password == null || name == null || email == null) {
            response.setVersion(request.getVersion());
            response.setStatusCode(HttpStatus.BAD_REQUEST);
        }

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
}
