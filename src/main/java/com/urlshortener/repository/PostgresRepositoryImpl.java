package com.urlshortener.repository;

import com.urlshortener.models.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PostgresRepositoryImpl implements IUrlShortenerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Url save(Url url) {
        int rowCount = jdbcTemplate.update("INSERT INTO url_shortener(short_url,full_url,created_at) VALUES(?,?, extract(epoch from now()));", url.getShortUrl(), url.getFullUrl());
        if (rowCount > 0)
            return this.fetchUrl(url.getShortUrl()).orElseThrow(() -> new RuntimeException("Failed to fetch url!"));
        throw new RuntimeException("Failed to save url!");
    }

    private Optional<Url> fetchUrl(String shortUrl) {
        try {
            Url url = jdbcTemplate.queryForObject(
                    "SELECT short_url, full_url, created_at FROM url_shortener WHERE short_url = ?",
                    new Object[]{shortUrl},
                    (rs, rowNum) -> {
                        Url result = new Url();
                        result.setShortUrl(rs.getString("short_url"));
                        result.setFullUrl(rs.getString("full_url"));
                        result.setCreatedAt(rs.getLong("created_at"));
                        return result;
                    });
            return Optional.of(url);
        } catch (EmptyResultDataAccessException e) {
            // Handle the case when no record is found
            return Optional.empty();
        }
    }

    @Override
    public Optional<Url> findOne(String shortUrl) {
        return this.fetchUrl(shortUrl);
    }
}
