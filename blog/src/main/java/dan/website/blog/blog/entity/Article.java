package dan.website.blog.blog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Articles.
 */
@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Main ink to main picture .
     */
    @Column(name = "main_picture")
    private String mainPicture;

    /**
     * Description.
     */
    @Column(name = "description")
    private String description;

    /**
     * Title.
     */
    @Column(name = "title")
    private String title;

    /**
     * * Article
     */
    @Column(columnDefinition = "TEXT")
    private String body; // Текст статьи

    /**
     * List of tags.
     */
    @ManyToMany
    @JoinTable(
            name = "article_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    /**
     * Create date.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;
}