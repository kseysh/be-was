package webserver.view;

import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.FileReader;

public class TemplateView implements View {

    private final String filePath;

    private static final Pattern LOOP_PATTERN =
            Pattern.compile("<(\\w+)[^>]*\\s+data-for=\"(\\w+)\\s*:\\s*([\\w.]+)\"[^>]*>(.*?)</\\1>", Pattern.DOTALL);
    private static final Pattern CONDITION_PATTERN =
            Pattern.compile("<(\\w+)[^>]*\\s+data-if=\"([\\w.!]+)\"[^>]*>(.*?)</\\1>", Pattern.DOTALL);
    private static final Pattern VARIABLE_PATTERN =
            Pattern.compile("\\{\\{\\s*([\\w.]+)\\s*}}");

    public TemplateView(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) {
        String html = new String(FileReader.readFile(filePath));

        html = processLoops(html, model);
        html = processConditionals(html, model);
        html = processVariables(html, model);

        response.setHeader(HttpHeader.CONTENT_TYPE.getValue(), ContentTypes.TEXT_HTML.getMimeType())
                .setStatusCode(HttpStatus.OK)
                .setBody(html.getBytes(StandardCharsets.UTF_8));
    }

    private String processLoops(String html, Map<String, Object> model) {
        Matcher matcher = LOOP_PATTERN.matcher(html);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String tagName = matcher.group(1);
            String itemName = matcher.group(2);
            String listName = matcher.group(3);
            String innerHtml = matcher.group(4);
            String fullTag = matcher.group(0);

            Object listObj = model.get(listName);

            StringBuilder loopResult = new StringBuilder();
            if (listObj instanceof Collection<?>) {
                for (Object item : (Collection<?>) listObj) {
                    String processedItemHtml = replaceLoopVariables(innerHtml, itemName, item);
                    String tagHeader = fullTag.substring(0, fullTag.indexOf(">") + 1);
                    String tagHeaderWithoutFor = tagHeader.replaceAll("\\s+data-for=\"[^\"]*\"", "");

                    loopResult.append(tagHeaderWithoutFor)
                            .append(processedItemHtml)
                            .append("</").append(tagName).append(">");
                }
            }
            matcher.appendReplacement(sb, Matcher.quoteReplacement(loopResult.toString()));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String replaceLoopVariables(String html, String itemName, Object itemObj) {
        Pattern pattern = Pattern.compile("\\{\\{\\s*" + itemName + "\\.([\\w.]+)\\s*}}");
        Matcher matcher = pattern.matcher(html);
        StringBuilder sb = new StringBuilder();

        while(matcher.find()) {
            String fieldPath = matcher.group(1);
            Object value = getFieldValue(itemObj, fieldPath);
            matcher.appendReplacement(sb, value != null ? String.valueOf(value) : "");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String processConditionals(String html, Map<String, Object> model) {
        Matcher matcher = CONDITION_PATTERN.matcher(html);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String conditionKey = matcher.group(2);
            String tagContent = matcher.group(0);

            boolean negate = false;
            if (conditionKey.startsWith("!")) {
                negate = true;
                conditionKey = conditionKey.substring(1);
            }

            Object conditionVal = model.get(conditionKey);

            boolean isTrue = false;
            if (conditionVal instanceof Boolean) {
                isTrue = (Boolean) conditionVal;
            } else if (conditionVal != null) {
                isTrue = true;
            }

            if (negate) {
                isTrue = !isTrue;
            }

            if (isTrue) {
                String processedTag = tagContent.replaceFirst("\\s+data-if=\"[^\"]*\"", "");
                matcher.appendReplacement(sb, Matcher.quoteReplacement(processedTag));
            } else {
                matcher.appendReplacement(sb, "");
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    // 3. 변수 치환 처리 ({{variable}})
    private String processVariables(String html, Map<String, Object> model) {
        Matcher matcher = VARIABLE_PATTERN.matcher(html);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String key = matcher.group(1);
            Object value = model.get(key);
            matcher.appendReplacement(sb, value != null ? String.valueOf(value) : "");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private Object getFieldValue(Object object, String fieldName) {
        try {
            String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            Method method = object.getClass().getMethod(getterName);
            return method.invoke(object);
        } catch (Exception e) {
            try {
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(object);
            } catch (Exception ex) {
                return "";
            }
        }
    }
}
