package handler;

import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpStatus;
import exception.InternalServerErrorException;
import exception.NotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import http.HttpRequest;
import http.HttpResponse;
import util.FileReader;

public class StaticResourceHandler implements Handler {
    private static final String DEFAULT_PATH = "/index.html";
    private static final String RESOURCES_PATH = "./src/main/resources/static";

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws RuntimeException {
        String path = request.getPath();

        if(path.equals("/")) path = DEFAULT_PATH;
        byte[] body;
        try{
            File file = new File(RESOURCES_PATH + path);

            if (file.exists() && file.isFile()) {
                body = FileReader.readAllBytes(file);
            }else{
                throw new NotFoundException(path + " file not found");
            }
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        Map<String, String> headers = new HashMap<>();
        String extension = getFileExtension(path);
        if(!extension.isEmpty()){
            headers.put(HttpHeader.CONTENT_TYPE.getValue(), getContentType(extension));
        }
        response.setStatusCode(HttpStatus.OK);
        response.setBody(body);
        response.setHeaders(headers);
        response.setVersion(request.getVersion());
    }

    private String getContentType(String extension) {
        return ContentTypes.from(extension).getMimeType();
    }

    private String getFileExtension(String path) {
        if (path == null) return "";
        int lastDotIndex = path.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == path.length() - 1) return "";
        return path.substring(lastDotIndex + 1);
    }
}
