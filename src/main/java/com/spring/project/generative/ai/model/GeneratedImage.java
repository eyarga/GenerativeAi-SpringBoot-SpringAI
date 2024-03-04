package com.spring.project.generative.ai.model;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a generated image.
 */
@Getter
@Setter
@Builder
public class GeneratedImage {
    private List<ImageUrl> data;
}
