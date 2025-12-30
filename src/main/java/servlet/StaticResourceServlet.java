package servlet;

import enums.HttpHeader;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import http.HttpRequest;
import http.HttpResponse;

public class StaticResourceServlet implements Servlet {

    private static final String RESOURCES_PATH = "./src/main/resources/static";
    private static final String DEFAULT_PATH = "/index.html";

    @Override
    public HttpResponse doService(HttpRequest request) {
        String path = request.getPath();
        if(path.equals("/")) path = DEFAULT_PATH;
        File file = new File(RESOURCES_PATH + path);

        if (file.exists() && file.isFile()) {
            byte[] body;
            try {
                body = Files.readAllBytes(file.toPath());
            }catch (Exception e) {
                return HttpResponse.internalServerError(Map.of(), new byte[0], request.getVersion());
            }

            Map<String, String> headers = new HashMap<>();
            headers.put(HttpHeader.CONTENT_TYPE.getValue(), getContentType(path));
            return HttpResponse.ok(headers, body, request.getVersion());
        }
        return HttpResponse.notFound(null, null, request.getVersion());
    }

    private String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css"))  return "text/css";
        if (path.endsWith(".js"))   return "application/javascript";
        if (path.endsWith(".svg"))  return "image/svg+xml";
        return "application/octet-stream"; // 알 수 없는 타입일 때의 기본값
    }
}
