package handler;

import exception.HttpException;
import http.HttpRequest;
import http.HttpResponse;

public class HomeHandler implements Handler {
    @Override
    public void handle(HttpRequest request, HttpResponse response) throws HttpException {
        request.setPath("/index.html");
        new StaticResourceHandler().handle(request, response);
    }
}
