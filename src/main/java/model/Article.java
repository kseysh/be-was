package model;

public record Article(
        String articleId,
        String title,
        String content,
        String userId,
        String imageId
) {

}
