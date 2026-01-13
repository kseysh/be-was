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
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserHandler extends AbstractHandler {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserHandler.class);

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

        User user = new User(userId, password, name, email);
        Database.addUser(user);
        logger.info("User added: {}", user);

        response.setVersion(request.getVersion())
                .setStatusCode(HttpStatus.FOUND)
                .setHeader(HttpHeader.LOCATION.getValue(), "/index.html")
                .setHeader(HttpHeader.CONTENT_TYPE.getValue(), ContentTypes.TEXT_HTML.getMimeType());
    }

    private void validateParameters(String userId, String password, String name)
            throws HttpException {
        if (userId == null || password == null || name == null) {
            throw new BadRequestException("parameters are missing");
        }
    }
}
