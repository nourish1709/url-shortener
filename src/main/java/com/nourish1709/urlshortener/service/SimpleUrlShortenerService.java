package com.nourish1709.urlshortener.service;

import com.nourish1709.urlshortener.dao.ShortenedUrlRepository;
import com.nourish1709.urlshortener.dto.ShortenUrlDto;
import com.nourish1709.urlshortener.entity.ShortenedUrl;
import com.nourish1709.urlshortener.exception.UrlNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleUrlShortenerService implements UrlShortenerService {

    private static final int SHORTENED_URL_ID_LENGTH = 8;

    private final ShortenedUrlRepository shortenedUrlRepository;

    @Override
    @Transactional
    public ShortenedUrl shortenUrl(ShortenUrlDto shortenUrlDto) {
        final Optional<ShortenedUrl> existingShortenedUrl = shortenedUrlRepository.findByOriginalUrl(shortenUrlDto.url());
        if (existingShortenedUrl.isPresent()) {
            return existingShortenedUrl.get();
        }

        final ShortenedUrl shortenedUrl = new ShortenedUrl();

        String urlId = RandomStringUtils.random(SHORTENED_URL_ID_LENGTH, true, false);

        while (shortenedUrlRepository.existsById(urlId)) {
            urlId = RandomStringUtils.random(SHORTENED_URL_ID_LENGTH, true, false);
        }

        shortenedUrl.setId(urlId);
        shortenedUrl.setOriginalUrl(shortenUrlDto.url());
        shortenedUrl.setTitle(shortenUrlDto.title());

        return shortenedUrlRepository.save(shortenedUrl);
    }

    @Override
    @Cacheable("urlById")
    public ShortenedUrl findShortenedUrl(String shortenUrlId) {
        return shortenedUrlRepository.findById(shortenUrlId)
                .orElseThrow(UrlNotFoundException::new);
    }
}
