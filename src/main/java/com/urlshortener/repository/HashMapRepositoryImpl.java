package com.urlshortener.repository;

import com.urlshortener.models.Url;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HashMapRepositoryImpl implements IUrlShortenerRepository {
    private Map<String, Url> db = new HashMap<>();

    @Override
    public Url save(Url url) {
        db.put(url.getShortUrl(), url);
        return db.get(url.getShortUrl());
    }

    @Override
    public Optional<Url> findOne(String shortUrl) {
        if (db.containsKey(shortUrl)) return Optional.of(db.get(shortUrl));
        return Optional.empty();
    }
}
