package servlet;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Servlet {
    HttpResponse doService(HttpRequest request);
}
