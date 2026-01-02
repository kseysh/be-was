package handler;

import http.HttpRequest;
import http.HttpResponse;

public class HomeHandler implements Handler {
    @Override
    public void handle(HttpRequest request, HttpResponse response) throws RuntimeException{
        request.setPath("/index.html");
        new StaticResourceHandler().handle(request, response);
    }
}
