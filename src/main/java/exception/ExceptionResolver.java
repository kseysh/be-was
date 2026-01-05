package exception;

import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpStatus;
import http.HttpRequest;
import http.HttpResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import util.FileReader;

public class ExceptionResolver {

    private static final String ERROR_RESOURCES_PATH = "./src/main/resources/static/error";
    private static final String DEFAULT_ERROR_PAGE = "/403.html";
    private static final Map<HttpStatus, String> errorPageMapper = Map.of(
            HttpStatus.INTERNAL_SERVER_ERROR, "/500.html",
            HttpStatus.NOT_FOUND, "/404.html"
    );

    private ExceptionResolver() {
    }

    public static void resolve(HttpRequest request, HttpResponse response, HttpException e) {
        byte[] body;

        String errorPage = errorPageMapper.get(e.getStatus());
        if (errorPage == null) {
            errorPage = DEFAULT_ERROR_PAGE;
        }
        try {
            File file = new File(ERROR_RESOURCES_PATH + errorPage);
            body = FileReader.readAllBytes(file);
        } catch (IOException ioException) {
            return;
        }

        Map<String, String> headers = Map.of(
                HttpHeader.CONTENT_TYPE.getValue(), ContentTypes.TEXT_HTML.getMimeType()
        );
        response.setHeaders(headers);
        response.setStatusCode(e.getStatus());
        response.setBody(body);
        response.setVersion(request.getVersion());
    }
}
