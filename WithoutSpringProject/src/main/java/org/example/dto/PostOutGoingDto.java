package org.example.dto;

public class PostOutGoingDto {
    private String content;
    private Long authorId;

    public PostOutGoingDto() {
    }

    public PostOutGoingDto(String content, Long authorId) {
        this.content = content;
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
