package handler;

import enums.HttpMethod;
import exception.HttpException;
import exception.NotFoundException;
import http.HttpRequest;
import http.HttpResponse;

public class RegisterHandler implements Handler{
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
        new StaticResourceHandler().handle(request, response);
    }
}
