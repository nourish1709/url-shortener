create table shortened_urls
(
    id           text      not null primary key,
    original_url text      not null unique,
    title        text      not null,
    created_at   timestamp not null default now()
);
