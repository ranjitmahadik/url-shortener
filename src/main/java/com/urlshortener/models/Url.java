package com.urlshortener.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Url {
    private String fullUrl;
    private String shortUrl;
    private Long createdAt;
}
