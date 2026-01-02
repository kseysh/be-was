package handler;

import exception.HttpException;
import http.HttpRequest;
import http.HttpResponse;

public class RegisterHandler implements Handler{
    @Override
    public void handle(HttpRequest request, HttpResponse response) throws HttpException {
        request.setPath("/registration/index.html");
        new StaticResourceHandler().handle(request, response);
    }
}
