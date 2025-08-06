SET search_path TO blog_service;

ALTER TABLE blog_service.articles
    ADD COLUMN created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW();

COMMENT ON COLUMN blog_service.articles.created_at IS 'Дата и время создания статьи';
