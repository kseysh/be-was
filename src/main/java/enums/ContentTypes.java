package enums;

public enum ContentTypes {
    TEXT_PLAIN("text/plain", "txt"),
    TEXT_HTML("text/html", "html"),
    TEXT_CSS("text/css", "css"),
    IMAGE_JPEG("image/jpeg", "jpeg"),
    IMAGE_JPG("image/jpeg", "jpg"),
    IMAGE_PNG("image/png", "png"),
    IMAGE_GIF("image/gif", "gif"),
    IMAGE_SVG("image/svg+xml", "svg"),
    IMAGE_ICO("image/x-icon", "ico"),
    APPLICATION_JAVASCRIPT("application/javascript", "js"),
    APPLICATION_JSON("application/json", "json"),
    APPLICATION_XML("application/xml", "xml"),
    APPLICATION_OCTET_STREAM("application/octet-stream", "bin");

    private final String mimeType;

    private final String extension;

    ContentTypes(String mimeType, String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
    }

    public static ContentTypes from(String extension) {

        for (ContentTypes contentType : ContentTypes.values()) {
            if (contentType.extension.equals(extension)) {
                return contentType;
            }
        }

        return APPLICATION_OCTET_STREAM;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getExtension() {
        return extension;
    }

}
