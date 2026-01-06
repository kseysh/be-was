package handler;

import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpStatus;
import exception.BadRequestException;
import exception.HttpException;
import exception.InternalServerErrorException;
import exception.NotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import http.request.HttpRequest;
import http.response.HttpResponse;
import util.FileReader;

public class StaticResourceHandler implements Handler {

    private static final String RESOURCES_PATH = "./src/main/resources/static";
    private static final String EMPTY_EXTENSION = "";
    private static final char EXTENSION_SEPARATOR = '.';
    private static final StaticResourceHandler INSTANCE = new StaticResourceHandler();

    private StaticResourceHandler() {
    }

    public static StaticResourceHandler getInstance() {
        return INSTANCE;
    }


    @Override
    public void handle(HttpRequest request, HttpResponse response) throws HttpException {
        String path = request.getPath();

        byte[] body;
        try {
            File file = new File(RESOURCES_PATH + path);
            validateFile(file, path);
            body = FileReader.readAllBytes(file);
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        Map<String, String> headers = new HashMap<>();
        String extension = getFileExtension(path);
        if (!extension.equals(EMPTY_EXTENSION)) {
            headers.put(HttpHeader.CONTENT_TYPE.getValue(), getContentType(extension));
        }

        response.setStatusCode(HttpStatus.OK);
        response.setBody(body);
        response.setHeaders(headers);
        response.setVersion(request.getVersion());
    }

    private void validateFile(File file, String path) throws HttpException {
        if (!file.exists()) {
            throw new NotFoundException(path + " file not found");
        }

        if (!file.isFile()) {
            throw new BadRequestException(path + " file is not a file");
        }
    }

    private String getContentType(String extension) {
        return ContentTypes.fromExtension(extension).getMimeType();
    }

    private String getFileExtension(String path) {
        if (path == null) {
            return EMPTY_EXTENSION;
        }
        int separatorIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
        if (separatorIndex == -1 || separatorIndex == path.length() - 1) {
            return EMPTY_EXTENSION;
        }
        return path.substring(separatorIndex + 1);
    }
}
