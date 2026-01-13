package http.converter;

import enums.ContentTypes;

public record ImageForm(byte[] bytes, String fileName, ContentTypes contentType) {

}
