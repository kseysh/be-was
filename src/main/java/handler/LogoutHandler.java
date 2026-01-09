package handler;

import db.SessionManager;
import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpMethod;
import enums.HttpStatus;
import exception.HttpException;
import exception.MethodNotAllowedException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class LogoutHandler implements Handler {

    private static final LogoutHandler INSTANCE = new LogoutHandler();

    private LogoutHandler() {
    }

    public static LogoutHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws HttpException {
        if (request.getMethod() == HttpMethod.POST) {
            post(request, response);
        } else {
            throw new MethodNotAllowedException("Not Supported Method");
        }
    }

    private void post(HttpRequest request, HttpResponse response) throws HttpException {
        String sessionId = request.getCookieValue("sid");

        SessionManager.getInstance().removeAttribute(sessionId);

        response.setStatusCode(HttpStatus.FOUND)
                .setHeader(HttpHeader.LOCATION.getValue(), "/")
                .setVersion(request.getVersion())
                .setCookie(sessionId, "/")
                .setHeader(HttpHeader.CONTENT_TYPE.getValue(), ContentTypes.TEXT_HTML.getMimeType());
    }
}
