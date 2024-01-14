package com.urlshortener.repository;

import com.urlshortener.models.Url;

import java.util.Optional;

public class PostgresRepositoryImpl implements IUrlShortenerRepository {

//    private JdbcTemplate jdbcTemplate;

    @Override
    public Url save(Url url) {
//        int rowCount = jdbcTemplate.update("INSERT INTO url_shortener(short_url,long_url,created_at) VALUES(?,?, extract(epoch from now()));", url.getShortUrl(), url.getFullUrl());
        return null;
    }

    @Override
    public Optional<Url> findOne(String shortUrl) {
        return Optional.empty();
    }
}
