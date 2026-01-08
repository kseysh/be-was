package exception;

import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Map;
import util.FileReader;

public class ExceptionResolver {

    private static final String DEFAULT_ERROR_PAGE = "/error/403.html";
    private static final Map<HttpStatus, String> errorPageMapper = Map.of(
            HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html",
            HttpStatus.NOT_FOUND, "/error/404.html"
    );

    private ExceptionResolver() {
    }

    public static void resolve(HttpRequest request, HttpResponse response, HttpException e) {
        String errorPage = errorPageMapper.get(e.getStatus());
        if (errorPage == null) {
            errorPage = DEFAULT_ERROR_PAGE;
        }

        response.setStatusCode(e.getStatus())
                .setVersion(request.getVersion())
                .setBody(FileReader.readFile(errorPage))
                .setHeader(HttpHeader.CONTENT_TYPE.getValue(), ContentTypes.TEXT_HTML.getMimeType());
    }
}
