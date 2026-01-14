package model;

import java.util.UUID;

public record Comment(
        String commentId,
        String content,
        String userId,
        String articleId
) {

    public static Comment newComment(String content, String userId, String articleId){
        return new Comment(UUID.randomUUID().toString(), content, userId, articleId);
    }
}
