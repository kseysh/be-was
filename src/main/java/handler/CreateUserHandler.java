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

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws HttpException {
        if(request.getMethod() == HttpMethod.GET) {
            get(request, response);
        }else{
            throw new NotFoundException("Not Supported Method");
        }
    }

    public void get(HttpRequest request, HttpResponse response) throws HttpException {
        String userId = request.getQuery().get("userId");
        String password = request.getQuery().get("password");
        String name = request.getQuery().get("name");
        String email = request.getQuery().get("email");

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

    private void validateParameters(String userId, String password, String name, String email) throws HttpException {
        int requiredParameterCount = 0;
        StringBuilder sb = new StringBuilder();

        if(userId == null){
            sb.append("userId");
            requiredParameterCount++;
        }

        if(password == null){
            if(requiredParameterCount == 0){
                sb.append("password");
            }else{
                sb.append(", password");
            }
            requiredParameterCount++;
        }

        if(name == null){
            if(requiredParameterCount == 0){
                sb.append("name");
            }else{
                sb.append(", name");
            }
            requiredParameterCount++;
        }

        if(email == null){
            if(requiredParameterCount == 0){
                sb.append("email");
            }else{
                sb.append(", email");
            }
        }

        if(requiredParameterCount != 0){
            if(requiredParameterCount == 1){
                sb.append("is required");
            }else{
                sb.append("are required");
            }
            throw new BadRequestException(sb.toString());
        }
    }
}
