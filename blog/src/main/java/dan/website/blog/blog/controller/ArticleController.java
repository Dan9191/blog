package dan.website.blog.blog.controller;

import dan.website.blog.blog.model.ArticleDto;
import dan.website.blog.blog.model.ShortArticle;
import dan.website.blog.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public Page<ShortArticle> getLatestArticles(Pageable pageable) {
        return articleService.getLatestArticles(pageable);
    }

    @GetMapping("/{id}")
    public ArticleDto getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @GetMapping("/filter")
    public Page<ShortArticle> getArticlesByTags(
            @RequestParam List<String> tags,
            Pageable pageable) {
        return articleService.getArticlesByTags(tags, pageable);
    }
}
