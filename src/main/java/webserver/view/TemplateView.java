package webserver.view;

import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import util.FileReader;

public class TemplateView implements View {

    private final String filePath;

    public TemplateView(String filePath) {
        this.filePath = filePath;
    }

    // TODO: StringBuilder를 이용한 구현으로 변경
    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) {
        String content = new String(FileReader.readFile(filePath));

        for (String key : model.keySet()) {
            content = content.replace("{{" + key + "}}", model.get(key).toString());
        }

        response.setHeader(HttpHeader.CONTENT_TYPE.getValue(), ContentTypes.TEXT_HTML.getMimeType())
                .setStatusCode(HttpStatus.OK)
                .setVersion(request.getVersion())
                .setBody(content.getBytes(StandardCharsets.UTF_8));
    }
}
