package handler;

import exception.HttpException;
import exception.MethodNotAllowedException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractHandler implements Handler {

    public final void handle(HttpRequest request, HttpResponse response) throws HttpException {
        switch (request.getMethod()) {
            case GET: get(request, response); break;
            case POST: post(request, response); break;
            case PUT: put(request, response);  break;
            case PATCH: patch(request, response); break;
            case DELETE: delete(request, response); break;
            default: throw new MethodNotAllowedException("Method not allowed");
        }
    }

    protected void get(HttpRequest request, HttpResponse response){
        throw new MethodNotAllowedException("get method not allowed");
    }

    protected void post(HttpRequest request, HttpResponse response){
        throw new MethodNotAllowedException("post method not allowed");
    }

    protected void put(HttpRequest request, HttpResponse response){
        throw new MethodNotAllowedException("put method not allowed");
    }

    protected void patch(HttpRequest request, HttpResponse response){
        throw new MethodNotAllowedException("patch method not allowed");
    }

    protected void delete(HttpRequest request, HttpResponse response){
        throw new MethodNotAllowedException("delete method not allowed");
    }
}
