package com.urlshortener.repository;

import com.urlshortener.models.Url;

import java.util.Optional;

public interface IUrlShortenerRepository {
    Url save(Url url);

    Optional<Url> findOne(String shortUrl);
}

