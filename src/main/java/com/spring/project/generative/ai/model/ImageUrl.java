package com.spring.project.generative.ai.model;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageUrl {
    private String url;
}
