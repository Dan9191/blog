package dan.website.blog.blog.model;

import dan.website.blog.blog.entity.Article;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Article dto.
 */
@Data
public class ArticleDto {
    private Long id;
    private String mainPicture;
    private String body;
    private List<TagDto> tags;
    private ZonedDateTime createdAt;
    private String title;
    private String description;

    // Конвертер из Entity
    public static ArticleDto fromEntity(Article article) {
        ArticleDto dto = new ArticleDto();
        dto.setId(article.getId());
        dto.setMainPicture(article.getMainPicture());
        dto.setTitle(article.getTitle());
        dto.setBody(article.getBody());
        dto.setDescription(article.getDescription());
        dto.setTags(article.getTags().stream()
                .map(TagDto::fromEntity)
                .toList());
        dto.setCreatedAt(article.getCreatedAt());
        return dto;
    }
}
