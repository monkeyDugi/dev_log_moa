package com.devlogmoa.scheduler;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class FeedDtos {

    private final List<FeedDto> feedDtos = new ArrayList<>();

    public void add(FeedDto feedDto) {
        feedDtos.add(feedDto);
    }

    public List<FeedDto> getFeedDtos() {
        return Collections.unmodifiableList(feedDtos);
    }

    public FeedDto firstFeed() throws IndexOutOfBoundsException {
        return feedDtos.get(0);
    }

    @Override
    public String toString() {
        return "FeedDtos{" +
                "feedDtos=" + feedDtos +
                '}';
    }
}
