package util;

import enums.ContentTypes;

public class ContentTypeExtractor {

    private static final String EMPTY_EXTENSION = "";
    private static final char EXTENSION_SEPARATOR = '.';

    private ContentTypeExtractor() {
    }

    public static String extractContentTypeOnFilePath(String path) {
        return extractContentTypeOnUrlPath(path);
    }


    public static String extractContentTypeOnUrlPath(String path) {
        String extension = getFileExtension(path);
        return ContentTypes.fromExtension(extension).getMimeType();
    }

    private static String getFileExtension(String path) {
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
