package handler;

import http.HttpRequest;
import http.HttpResponse;

public class RegisterHandler implements Handler{

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws RuntimeException{
       request.setPath("/registration/index.html");
       new StaticResourceHandler().handle(request, response);
    }
}
