package com.example.script.demo.shortlink;

import lombok.Data;


@Data
public class ShortLinkResult {

    private long id;
    private String sourceUrl;
    private String shortUrl;

    public ShortLinkResult(long id, String sourceUrl, String shortUrl) {
        this.id = id;
        this.sourceUrl = sourceUrl;
        this.shortUrl = shortUrl;
    }
}
