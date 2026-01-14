package handler;

import db.*;
import enums.HttpHeader;
import enums.HttpStatus;
import exception.BadRequestException;
import exception.HttpException;
import exception.UnsupportedMediaTypeException;
import http.converter.Form;
import http.converter.FormHttpMessageConverter;
import http.converter.HttpMessageConverter;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Image;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserHandler extends AbstractHandler {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserHandler.class);

    private final UserDatabase userDatabase = DatabaseConfig.userDatabase;
    private final ImageDatabase imageDatabase = DatabaseConfig.imageDatabase;

    public CreateUserHandler() {
    }

    @Override
    protected void post(HttpRequest request, HttpResponse response) throws HttpException {
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

        Image profileImage = Image.defaultImage();
        User user = new User(userId, password, name, email, profileImage.imageId());

        imageDatabase.save(profileImage);
        userDatabase.save(user);

        logger.info("User added: {}", user);

        response.setStatusCode(HttpStatus.FOUND)
                .setHeader(HttpHeader.LOCATION.getValue(), "/index.html");
    }

    private void validateParameters(String userId, String password, String name)
            throws HttpException {
        if (userId == null || password == null || name == null) {
            throw new BadRequestException("parameters are missing");
        }
    }
}
