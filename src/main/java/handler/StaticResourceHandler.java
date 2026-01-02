package handler;

import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpStatus;
import exception.BadRequestException;
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
    private static final String RESOURCES_PATH = "./src/main/resources/static";
    private static final String EMPTY_EXTENSION = "";

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws RuntimeException {
        String path = request.getPath();

        byte[] body;
        try{
            File file = new File(RESOURCES_PATH + path);
            validateFile(file, path);
            body = FileReader.readAllBytes(file);
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        Map<String, String> headers = new HashMap<>();
        String extension = getFileExtension(path);
        if(extension.equals(EMPTY_EXTENSION)){
            headers.put(HttpHeader.CONTENT_TYPE.getValue(), getContentType(extension));
        }
        response.setStatusCode(HttpStatus.OK);
        response.setBody(body);
        response.setHeaders(headers);
        response.setVersion(request.getVersion());
    }

    private void validateFile(File file, String path) throws IOException {
        if(!file.exists()){
            throw new NotFoundException(path + " file not found");
        }

        if(!file.isFile()){
            throw new BadRequestException(path + " file is not a file");
        }
    }

    private String getContentType(String extension) {
        return ContentTypes.from(extension).getMimeType();
    }

    private String getFileExtension(String path) {
        if (path == null) return EMPTY_EXTENSION;
        int lastDotIndex = path.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == path.length() - 1) return EMPTY_EXTENSION;
        return path.substring(lastDotIndex + 1);
    }
}
