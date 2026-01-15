package model;

public class Comment {
    private final String commentId;
    private final String content;
    private final String userId;
    private final String articleId;

    private Comment(String commentId, String content, String userId, String articleId){
        this.commentId = commentId;
        this.content = content;
        this.userId = userId;
        this.articleId = articleId;
    }

    public static Comment newComment(String content, String userId, String articleId){
        return new Comment(null, content, userId, articleId);
    }

    public String getUserId() {
        return userId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public String getArticleId() {
        return articleId;
    }
}
