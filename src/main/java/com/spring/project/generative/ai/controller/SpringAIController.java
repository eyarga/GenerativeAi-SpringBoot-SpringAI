package com.spring.project.generative.ai.controller;

import com.spring.project.generative.ai.service.SpringAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

/**
 * This class is a controller for the Spring AI API.
 * It handles HTTP requests related to jokes, books, and images.
 */
@RestController
@RequestMapping("/api/v1")
public class SpringAIController {

    @Autowired
    SpringAIService aiService;

    @GetMapping("/joke")
    public String getJoke(@RequestParam String topic) {
        return aiService.getJoke(topic);
    }

    @GetMapping("/book")
    public String getBook(@RequestParam(name = "category") String category, @RequestParam(name = "year") String year) {
        return aiService.getBestBook(category, year);
    }

    @GetMapping(value = "/image", produces = "image/jpeg")
    public ResponseEntity<InputStreamResource> getImage(@RequestParam(name = "topic") String topic) throws URISyntaxException {
        return ResponseEntity.ok().body(aiService.getImage(topic));
    }
}
