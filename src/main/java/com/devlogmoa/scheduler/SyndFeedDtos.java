package com.devlogmoa.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SyndFeedDtos {

    private final List<FeedDtos> feedDtos = new ArrayList<>();

    public void add(FeedDtos feedDtos) {
        this.feedDtos.add(feedDtos);
    }

    public List<FeedDtos> getFeedDtos() {
        return Collections.unmodifiableList(feedDtos);
    }

    @Override
    public String toString() {
        return "SyndFeedDtos{" +
                "feedDtos=" + feedDtos +
                '}';
    }
}
