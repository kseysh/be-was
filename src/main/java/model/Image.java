package model;

import enums.ContentTypes;
import http.converter.ImageForm;
import util.FileReader;

import java.util.Base64;
import java.util.UUID;

public record Image(String imageId, byte[] bytes, String fileName, ContentTypes contentType) {

    private static final String FORMAT = "data:%s;base64,%s";
    private static final String DEFAULT_IMAGE_NAME = "profileImage";
    private static final byte[] DEFAULT_IMAGE_BYTES = FileReader.readFile("/img/basic_profileImage.svg");

    public static Image from(ImageForm imageForm) {
        return new Image(UUID.randomUUID().toString(), imageForm.bytes(), imageForm.fileName(), imageForm.contentType());
    }

    public static Image of(String imageId, ImageForm imageForm) {
        return new Image(imageId, imageForm.bytes(), imageForm.fileName(), imageForm.contentType());
    }

    public static Image defaultImage(){
        return new Image(UUID.randomUUID().toString(), DEFAULT_IMAGE_BYTES, DEFAULT_IMAGE_NAME, ContentTypes.IMAGE_SVG);
    }

    public String toImageString(){
        return FORMAT.formatted(contentType.getMimeType(), Base64.getEncoder().encodeToString(bytes));
    }
}
