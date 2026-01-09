package webserver.view;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Map;

public class StaticResourceView implements View {

    private final String filePath;

    public StaticResourceView(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) {
        response.respondWithStaticFile(request.getVersion(), filePath);
    }
}
