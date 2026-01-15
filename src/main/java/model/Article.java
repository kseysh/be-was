package model;

public class Article {
    private final Long articleId;
    private final String content;
    private final String userId;
    private final String imageId;
    private final Long likeCount;


    public Article(Long articleId, String content, String userId, String imageId, Long likeCount){
        this.articleId = articleId;
        this.content = content;
        this.userId = userId;
        this.imageId = imageId;
        this.likeCount = likeCount;
    }

    public static Article newArticle(String content, String userId, String imageId){
        return new Article(null, content, userId, imageId, 0L);
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getContent() {
        return content;
    }

    public String getUserId() {
        return userId;
    }

    public String getImageId() {
        return imageId;
    }

    public Long getLikeCount() {
        return likeCount;
    }
}
