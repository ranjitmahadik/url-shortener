package com.urlshortener.controllers;

import com.urlshortener.dto.UrlShortenerRequest;
import com.urlshortener.models.Url;
import com.urlshortener.services.IUrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1/api/url")
public class UrlShortenerController {
    @Autowired
    private IUrlShortenerService shortenerService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/shorten")
    public ResponseEntity<?> createShortUrl(@RequestBody UrlShortenerRequest urlShortenerRequest) throws URISyntaxException {
        if (urlShortenerRequest == null || urlShortenerRequest.getFullUrl() == null || urlShortenerRequest.getFullUrl().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("URL is required in order to shorten it.");
        }
        Url url = this.modelMapper.map(urlShortenerRequest, Url.class);
        Url savedUrl = this.shortenerService.generateShortUrl(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUrl);
    }


    @GetMapping("/{shortenURL}")
    public ResponseEntity<?> getLongUrl(@PathVariable("shortenURL") String shortenUrl) {
        if (shortenUrl == null || shortenUrl.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String fullUrl = this.shortenerService.getFullUrl(shortenUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, fullUrl);

        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }
}
