package handler;

import db.*;
import enums.HttpHeader;
import enums.HttpStatus;
import exception.BadRequestException;
import exception.HttpException;
import http.converter.Form;
import http.converter.HttpMessageConverter;
import http.converter.HttpMessageConverterMapper;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Image;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserHandler extends AbstractHandler {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserHandler.class);
    private static final int MIN_LENGTH = 4;

    private final UserDatabase userDatabase = DatabaseConfig.userDatabase;
    private final ImageDatabase imageDatabase = DatabaseConfig.imageDatabase;

    public CreateUserHandler() {
    }

    @Override
    protected void post(HttpRequest request, HttpResponse response) throws HttpException {
        HttpMessageConverter<Form> converter =
                HttpMessageConverterMapper.findHttpMessageConverter(Form.class, request.getContentType());
        Form<String, String> form = converter.read(request);

        String userId = form.get("userId");
        String password = form.get("password");
        String name = form.get("name");
        String email = form.get("email");

        validateParameters(userId, password, name);
        checkDuplicated(userId, name);

        Image profileImage = Image.defaultImage();
        User user = new User(userId, password, name, email, profileImage.imageId());

        imageDatabase.save(profileImage);
        userDatabase.save(user);

        logger.info("User added: {}", user);

        response.setStatusCode(HttpStatus.FOUND)
                .setHeader(HttpHeader.LOCATION.getValue(), "/login");
    }

    private void checkDuplicated(String userId, String name) {
        userDatabase.findById(userId).ifPresent(u -> {
            throw new BadRequestException("중복된 아이디: " + u);
        });
        userDatabase.findByName(name).ifPresent(u -> {
            throw new BadRequestException("중복된 닉네임: " + u);
        });
    }

    private void validateParameters(String userId, String password, String name)
            throws HttpException {
        if (userId == null || password == null || name == null) {
            throw new BadRequestException("parameters are missing");
        }

        if (userId.length() < MIN_LENGTH || password.length() < MIN_LENGTH || name.length() < MIN_LENGTH){
            throw new BadRequestException("userId, password, name이 너무 짧습니다");
        }
    }
}
