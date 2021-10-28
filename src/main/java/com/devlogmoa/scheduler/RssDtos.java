package com.devlogmoa.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RssDtos {

    private List<RssDto> rssDtos = new ArrayList<>();

    public void add(RssDto rssDto) {
        rssDtos.add(rssDto);
    }

    public void addAll(List<RssDto> rssDtos) {
        this.rssDtos.addAll(rssDtos);
    }

    public List<RssDto> getRssDtos() {
        return Collections.unmodifiableList(rssDtos);
    }

    @Override
    public String toString() {
        return "RssDtos{" +
                "rssDtos=" + rssDtos +
                '}';
    }
}
