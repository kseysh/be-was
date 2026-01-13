package model;

import enums.ContentTypes;
import http.converter.ImageForm;
import java.util.UUID;

public record Image(String imageId, byte[] bytes, String fileName, ContentTypes contentType) {

    public static Image from(ImageForm imageForm) {
        return new Image(UUID.randomUUID().toString(), imageForm.bytes(), imageForm.fileName(), imageForm.contentType());
    }
}
