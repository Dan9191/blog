SET search_path TO blog_service;

-- Создание таблицы тегов в схеме blog_service
CREATE TABLE blog_service.tags (
                                   id BIGSERIAL PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL UNIQUE
);

COMMENT ON TABLE blog_service.tags IS 'Таблица для хранения тегов статей';
COMMENT ON COLUMN blog_service.tags.id IS 'Уникальный идентификатор тега';
COMMENT ON COLUMN blog_service.tags.name IS 'Уникальное название тега';

-- Создание таблицы статей
CREATE TABLE IF NOT EXISTS articles (
                                       id BIGSERIAL PRIMARY KEY,
                                       main_picture VARCHAR(512),
                                       title VARCHAR(512),
                                       description VARCHAR(512),
                                       body TEXT NOT NULL
);

-- Создание таблицы связи многие-ко-многим между статьями и тегами
CREATE TABLE IF NOT EXISTS article_tags (
                                           article_id BIGINT NOT NULL REFERENCES blog_service.articles(id),
                                           tag_id BIGINT NOT NULL REFERENCES blog_service.tags(id),
                                           PRIMARY KEY (article_id, tag_id)
);

-- Комментарии к таблицам и колонкам
COMMENT ON TABLE blog_service.articles IS 'Таблица для хранения статей блога';
COMMENT ON COLUMN blog_service.articles.id IS 'Уникальный идентификатор статьи';
COMMENT ON COLUMN blog_service.articles.main_picture IS 'URL главного изображения статьи';
COMMENT ON COLUMN blog_service.articles.title IS 'Название статьи';
COMMENT ON COLUMN blog_service.articles.description IS 'Описание статьи';
COMMENT ON COLUMN blog_service.articles.body IS 'Текст статьи в формате HTML или Markdown';

COMMENT ON TABLE blog_service.article_tags IS 'Таблица связи между статьями и тегами (many-to-many)';
COMMENT ON COLUMN blog_service.article_tags.article_id IS 'Идентификатор статьи';
COMMENT ON COLUMN blog_service.article_tags.tag_id IS 'Идентификатор тега';
