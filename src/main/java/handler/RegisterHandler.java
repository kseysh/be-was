package handler;

import exception.HttpException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class RegisterHandler extends AbstractHandler {

    public RegisterHandler() {
    }

    @Override
    protected void get(HttpRequest request, HttpResponse response) throws HttpException {
        response.respondWithStaticFile(request.getVersion(), "/registration/index.html");
    }
}
