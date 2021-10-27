package com.devlogmoa.scheduler;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RssDto {

    private final String url;
    private final String rssUrl;
    private final String title;
    private final String pubLink;
    private final String pubTitle;
    private final LocalDate pubDate;

    public RssDto(String url, String rssUrl, String title, String pubLink, String pubTitle, LocalDate pubDate) {
        this.url = url;
        this.rssUrl = rssUrl;
        this.title = title;
        this.pubLink = pubLink;
        this.pubTitle = pubTitle;
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "RssDto{" +
                "url='" + url + '\'' +
                ", rssUrl='" + rssUrl + '\'' +
                ", title='" + title + '\'' +
                ", pubLink='" + pubLink + '\'' +
                ", pubTitle='" + pubTitle + '\'' +
                ", pubDate=" + pubDate +
                '}';
    }
}
