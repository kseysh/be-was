package handler;

import db.SessionManager;
import exception.HttpException;
import exception.UnauthorizedException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Collections;
import webserver.view.StaticResourceView;
import webserver.view.View;

public class MyPageHandler extends AbstractHandler{

    public MyPageHandler() {}

    @Override
    protected void get(HttpRequest request, HttpResponse response) throws HttpException {
        String sessionId = request.getCookieValue("sid");
        SessionManager.getInstance().getAttribute(sessionId).orElseThrow(() -> new UnauthorizedException("unauthorized"));
        View view = new StaticResourceView("/mypage/index.html");
        view.render(Collections.emptyMap(), request, response);
    }
}
