package com.urlshortener.services;

import com.urlshortener.models.Url;
import com.urlshortener.repository.IUrlShortenerRepository;
import com.urlshortener.strategies.IUrlShortenerStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShortenerServiceImpl implements IUrlShortenerService {
    @Autowired
    private IUrlShortenerRepository shortenerRepository;

    @Autowired
    private IUrlShortenerStrategies urlShortenerStrategies;

    @Override
    public Url generateShortUrl(Url url) {
        String shortenUrl = urlShortenerStrategies.shortenUrl();
        Url savedUrl = shortenerRepository.save(new Url(url.getFullUrl(), shortenUrl, System.currentTimeMillis()));
        return savedUrl;
    }

    @Override
    public String getFullUrl(String shortUrl) {
        Optional<Url> fullUrl = shortenerRepository.findOne(shortUrl);
        if (fullUrl.isEmpty()) throw new RuntimeException("Short Url doest exists!");
        return fullUrl.get().getFullUrl();
    }
}
