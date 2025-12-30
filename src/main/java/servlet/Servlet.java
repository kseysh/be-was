package servlet;

import http.HttpRequest;
import http.HttpResponse;

public interface Servlet {
    HttpResponse doService(HttpRequest request);
}
