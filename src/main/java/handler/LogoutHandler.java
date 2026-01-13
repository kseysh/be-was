package handler;

import db.SessionManager;
import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpStatus;
import exception.HttpException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class LogoutHandler extends AbstractHandler {

    public LogoutHandler() {
    }

    @Override
    protected void post(HttpRequest request, HttpResponse response) throws HttpException {
        String sessionId = request.getCookieValue("sid");

        SessionManager.getInstance().removeAttribute(sessionId);

        response.setStatusCode(HttpStatus.FOUND)
                .setHeader(HttpHeader.LOCATION.getValue(), "/")
                .setVersion(request.getVersion())
                .setCookie(sessionId, "/")
                .setHeader(HttpHeader.CONTENT_TYPE.getValue(), ContentTypes.TEXT_HTML.getMimeType());
    }
}
