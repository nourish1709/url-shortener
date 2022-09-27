package com.nourish1709.urlshortener.dao;

import com.nourish1709.urlshortener.entity.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl, String> {

    Optional<ShortenedUrl> findByOriginalUrl(String url);
}
