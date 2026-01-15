package model;

public class Comment {
    private final Long commentId;
    private final String content;
    private final String userId;
    private final Long articleId;

    public Comment(Long commentId, String content, String userId, Long articleId){
        this.commentId = commentId;
        this.content = content;
        this.userId = userId;
        this.articleId = articleId;
    }

    public static Comment newComment(String content, String userId, Long articleId){
        return new Comment(null, content, userId, articleId);
    }

    public String getUserId() {
        return userId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public Long getArticleId() {
        return articleId;
    }
}
