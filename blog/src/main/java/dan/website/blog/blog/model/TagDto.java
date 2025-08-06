package dan.website.blog.blog.model;

import dan.website.blog.blog.entity.Tag;
import lombok.Data;

/**
 * Tags
 */
@Data
public class TagDto {
    private Long id;
    private String name;

    public static TagDto fromEntity(Tag tag) {
        TagDto dto = new TagDto();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        return dto;
    }
}