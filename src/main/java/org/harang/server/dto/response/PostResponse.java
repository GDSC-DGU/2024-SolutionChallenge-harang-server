package org.harang.server.dto.response;

import lombok.Builder;
import org.harang.server.domain.Category;
import org.harang.server.domain.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record PostResponse(Long postId, String title, String author, String createdAt, String contentAbstract, List<String> category) {
    public static PostResponse of(Post post) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String createdAt = post.getCreatedAt().format(dateFormatter);
        String contentAbstract = post.getContent().substring(0, Math.min(post.getContent().length(), 70)) + "...";

        List<String> categories = post.getPostCategoryList().stream()
                .map(postCategory -> postCategory.getCategory().getName())
                .collect(Collectors.toList());

        return new PostResponse(post.getId(), post.getTitle(), post.getMember().getName(), createdAt, contentAbstract, categories);
    }

}
