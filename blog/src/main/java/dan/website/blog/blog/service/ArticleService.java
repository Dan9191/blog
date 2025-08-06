package dan.website.blog.blog.service;

import dan.website.blog.blog.entity.Article;
import dan.website.blog.blog.entity.Tag;
import dan.website.blog.blog.model.ArticleDto;
import dan.website.blog.blog.model.ShortArticle;
import dan.website.blog.blog.model.TagDto;
import dan.website.blog.blog.repository.ArticleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final EntityManager entityManager;

    /**
     * Пагинируемый список свежих статей.
     */
    public Page<ShortArticle> getLatestArticles(Pageable pageable) {
        return articleRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(ShortArticle::fromEntity);
    }

    /**
     * Получение полной статьи по ID
     *
     * @param id Id.
     * @return полная статья.
     */
    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found"));
        return ArticleDto.fromEntity(article);
    }

    /**
     * Фильтрация по тегам с сортировкой по количеству совпадений
     *
     * @param tagNames Список тегов
     * @param pageable пагинация.
     * @return пагинируемый список отфильтрованных статей.
     */
    public Page<ShortArticle> getArticlesByTags(List<String> tagNames, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> query = cb.createQuery(Article.class);
        Root<Article> root = query.from(Article.class);

        // Join с тегами
        Join<Article, Tag> tagJoin = root.join("tags", JoinType.INNER);

        // Условие: тег.name в списке tagNames
        Predicate tagPredicate = tagJoin.get("name").in(tagNames);

        // Группировка и сортировка по количеству совпадений тегов
        query.select(root)
                .where(tagPredicate)
                .groupBy(root.get("id"))
                .orderBy(cb.desc(cb.count(tagJoin.get("id"))));

        // Пагинация
        TypedQuery<Article> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Article> articles = typedQuery.getResultList();
        long total = getTotalCountForTagFilter(tagNames);

        return new PageImpl<>(
                articles.stream()
                        .map(ShortArticle::fromEntity)
                        .collect(Collectors.toList()),
                pageable,
                total
        );
    }

    private long getTotalCountForTagFilter(List<String> tagNames) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Article> root = query.from(Article.class);

        Join<Article, Tag> tagJoin = root.join("tags", JoinType.INNER);
        query.select(cb.countDistinct(root.get("id")))
                .where(tagJoin.get("name").in(tagNames));

        return entityManager.createQuery(query).getSingleResult();
    }

}
