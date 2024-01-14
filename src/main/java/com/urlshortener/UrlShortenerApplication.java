package com.urlshortener;

import com.urlshortener.idgenerator.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
@Slf4j
public class UrlShortenerApplication implements CommandLineRunner {

    @Autowired
    private IDGenerator idGenerator;

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Set<Long> uniqueIds = new HashSet<>();
        Long minMilli = Long.MAX_VALUE;
        Long maxMilli = Long.MIN_VALUE;
        for (int i = 0; i < 10_000; i++) {
            minMilli = Math.min(minMilli, System.currentTimeMillis());
            maxMilli = Math.max(maxMilli, System.currentTimeMillis());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Long id = idGenerator.generateId();
                    if (uniqueIds.contains(id)) {
                        log.warn("Duplicate ID generated : {}", id);
                    } else {
                        uniqueIds.add(id);
                    }
                }
            }).start();
        }
        log.info("Generated {} unique ids in {} milliseconds ", uniqueIds.size(), (maxMilli - minMilli));
    }
}
