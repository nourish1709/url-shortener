package com.nourish1709.urlshortener.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "shortened_urls")
@Data
public class ShortenedUrl {

    @Id
    private String id;

    @Column(name = "original_url", nullable = false, unique = true)
    private String originalUrl;

    @Column(name = "title", nullable = false)
    private String title;

    @CreatedDate
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
