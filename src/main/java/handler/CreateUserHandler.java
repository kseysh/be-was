package handler;

import db.Database;
import enums.HttpStatus;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserHandler implements Handler {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserHandler.class);

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
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
        response.setStatusCode(HttpStatus.OK);
    }
}
