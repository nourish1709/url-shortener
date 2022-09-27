package com.nourish1709.urlshortener.controller;

import com.nourish1709.urlshortener.dto.ShortenUrlDto;
import com.nourish1709.urlshortener.entity.ShortenedUrl;
import com.nourish1709.urlshortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/short")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @PostMapping("/urls")
    public ResponseEntity<Void> shortenUrl(@RequestBody ShortenUrlDto shortenUrlDto) {
        final ShortenedUrl shortenedUrlEntity = urlShortenerService.shortenUrl(shortenUrlDto);

        final URI shortenedUrl = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .pathSegment("short", "{id}")
                .buildAndExpand(shortenedUrlEntity.getId())
                .toUri();

        return ResponseEntity.created(shortenedUrl).build();
    }

    @GetMapping("/{shortenUrlId}")
    public ResponseEntity<Void> redirectFromShortenedUrl(@PathVariable String shortenUrlId) {
        final ShortenedUrl shortenedUrl = urlShortenerService.findShortenedUrl(shortenUrlId);

        final URI originalURI = UriComponentsBuilder
                .fromHttpUrl(shortenedUrl.getOriginalUrl())
                .build().toUri();
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                .location(originalURI)
                .build();
    }
}
