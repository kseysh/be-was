package servlet;

import exception.ErrorException;
import exception.ExceptionResolver;
import handler.Handler;
import handler.HandlerMapper;
import http.HttpRequest;
import http.HttpResponse;

public class DispatcherServlet {

    private static final DispatcherServlet INSTANCE = new DispatcherServlet();

    private DispatcherServlet() {}

    public static DispatcherServlet getInstance(){
        return INSTANCE;
    }

    public void doDispatch(HttpRequest request, HttpResponse response) {
        try{
            Handler handler = HandlerMapper.getHandler(request.getPath());
            handler.handle(request, response);
        }catch (ErrorException e){
            ExceptionResolver.resolve(request, response, e);
        }
    }
}
