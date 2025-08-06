package dan.website.blog.blog.model;

import dan.website.blog.blog.entity.Article;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Abridged article dto.
 */
@Data
public class ShortArticle {
    private Long id;
    private String mainPicture;
    private String title;
    private String description;
    private List<TagDto> tags;
    private ZonedDateTime createdAt;

    // Можно добавить статический метод-конвертер из Entity
    public static ShortArticle fromEntity(Article article) {
        ShortArticle dto = new ShortArticle();
        dto.setId(article.getId());
        dto.setMainPicture(article.getMainPicture());
        dto.setTitle(article.getTitle());
        dto.setDescription(article.getDescription());
        dto.setTags(article.getTags().stream()
                .map(TagDto::fromEntity)
                .toList());

        return dto;
    }
}