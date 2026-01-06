package handler;

import enums.HttpMethod;
import exception.HttpException;
import exception.NotFoundException;
import http.HttpRequest;
import http.HttpResponse;

public class HomeHandler implements Handler {

    private static final HomeHandler INSTANCE = new HomeHandler();

    private HomeHandler() {
    }

    public static HomeHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws HttpException {
        if (request.getMethod() == HttpMethod.GET) {
            get(request, response);
        } else {
            throw new NotFoundException("Not Supported Method");
        }
    }

    private void get(HttpRequest request, HttpResponse response) throws HttpException {
        if (request.getPath().equals("/")) {
            request.setPath("/index.html");
        }
        StaticResourceHandler.getInstance().handle(request, response);
    }


}
