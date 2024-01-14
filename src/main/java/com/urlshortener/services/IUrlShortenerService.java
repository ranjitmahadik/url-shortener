package com.urlshortener.services;

import com.urlshortener.models.Url;

public interface IUrlShortenerService {
    Url generateShortUrl(Url url);

    String getFullUrl(String shortUrl);
}
