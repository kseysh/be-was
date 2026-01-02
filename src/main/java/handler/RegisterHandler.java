package handler;

import enums.HttpMethod;
import exception.HttpException;
import exception.NotFoundException;
import http.HttpRequest;
import http.HttpResponse;

public class RegisterHandler implements Handler{

    private static final RegisterHandler INSTANCE = new RegisterHandler();

    private RegisterHandler() {}

    public static RegisterHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws HttpException {
        if (request.getMethod() == HttpMethod.GET) {
            get(request, response);
        }else{
            throw new NotFoundException("Not Supported Method");
        }
    }

    private void get(HttpRequest request, HttpResponse response) throws HttpException {
        request.setPath("/registration/index.html");
        StaticResourceHandler.getInstance().handle(request, response);
    }
}
