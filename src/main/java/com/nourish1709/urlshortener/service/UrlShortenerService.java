package com.nourish1709.urlshortener.service;

import com.nourish1709.urlshortener.dto.ShortenUrlDto;
import com.nourish1709.urlshortener.entity.ShortenedUrl;

public interface UrlShortenerService {

    ShortenedUrl shortenUrl(ShortenUrlDto shortenUrlDto);

    ShortenedUrl findShortenedUrl(String shortenUrlId);
}
