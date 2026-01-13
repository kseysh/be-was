package http.converter;

import java.util.HashMap;
import java.util.Map;

public class MultipartData {
    private final Map<String, String> texts = new HashMap<>();
    private final Map<String, ImageForm> files = new HashMap<>();

    public void addText(String name, String s) {
        texts.put(name, s);
    }

    public void addFile(String name, ImageForm form) {
        files.put(name, form);
    }

    public String getTexts(String name) {
        return texts.get(name);
    }

    public ImageForm getFileBytes(String name) {
        return files.get(name);
    }
}
