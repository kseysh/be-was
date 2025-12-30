package servlet;

import handler.Handler;
import handler.HandlerMapper;
import http.HttpRequest;
import http.HttpResponse;

public class DispatcherServlet {

    private static final DispatcherServlet INSTANCE = new DispatcherServlet();

    public static DispatcherServlet getInstance(){
        return INSTANCE;
    }

    public void doDispatch(HttpRequest request, HttpResponse response) {
        Handler handler = HandlerMapper.getHandler(request.getPath());
        handler.handle(request, response);
    }
}
