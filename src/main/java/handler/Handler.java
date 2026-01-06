package handler;

import exception.HttpException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Handler {

    void handle(HttpRequest request, HttpResponse response) throws HttpException;
}
