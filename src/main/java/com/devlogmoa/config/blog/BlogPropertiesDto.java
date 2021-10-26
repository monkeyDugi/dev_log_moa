package com.devlogmoa.config.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class BlogPropertiesDto {

    private String url;
    private String rssUrl;
    private String description;
}
