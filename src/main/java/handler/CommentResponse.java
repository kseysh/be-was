package handler;

import model.Image;

public class CommentResponse {

    private final String writerName;
    private final Image writerProfileImage;
    private final String content;

    public CommentResponse(String writerName, Image writerProfileImage, String content) {
        this.writerName = writerName;
        this.writerProfileImage = writerProfileImage;
        this.content = content;
    }

    public String getWriterName() {
        return writerName;
    }

    public String getWriterProfileImage() {
        return writerProfileImage.toImageString();
    }

    public String getContent() {
        return content;
    }
}
