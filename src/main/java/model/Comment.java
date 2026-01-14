package model;

public record Comment(
        String commentId,
        String content,
        String userId,
        String articleId
) {

}
