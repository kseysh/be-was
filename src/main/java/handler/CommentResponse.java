package handler;

public class CommentResponse {
    private final String writerName;
    private final byte[] writerProfileImage;
    private final String content;

    public CommentResponse(String writerName, byte[] writerProfileImage, String content){
        this.writerName = writerName;
        this.writerProfileImage = writerProfileImage;
        this.content = content;
    }

    public String getWriterName() {
        return writerName;
    }

    public byte[] getWriterProfileImage() {
        return writerProfileImage;
    }

    public String getContent() {
        return content;
    }
}
