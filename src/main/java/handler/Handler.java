package handler;

import http.HttpRequest;
import http.HttpResponse;

public interface Handler {
    void handle(HttpRequest request, HttpResponse response);
}
