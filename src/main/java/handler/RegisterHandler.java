package handler;

import exception.HttpException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Collections;
import webserver.view.StaticResourceView;
import webserver.view.View;

public class RegisterHandler extends AbstractHandler {

    public RegisterHandler() {
    }

    @Override
    protected void get(HttpRequest request, HttpResponse response) throws HttpException {
        View view = new StaticResourceView("/registration/index.html");
        view.render(Collections.emptyMap(), request, response);
    }
}
