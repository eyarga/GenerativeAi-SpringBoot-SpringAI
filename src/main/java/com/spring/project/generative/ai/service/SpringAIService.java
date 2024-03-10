package com.spring.project.generative.ai.service;

import com.spring.project.generative.ai.model.GeneratedImage;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * This class represents a service for handling AI-related operations in a Spring application.
 * It provides methods for getting jokes, recommending books, and generating images based on a given topic.
 */
@Service
public class SpringAIService {

    private final OpenAiChatClient aiClient;
    @Value("${spring.ai.openai.apikey}")
    private final String apiKey;
    @Value("${spring.ai.openai.imageUrl}")
    private final String openAIImageUrl;

    public SpringAIService(OpenAiChatClient aiClient, @Value("${spring.ai.openai.apikey}") String apiKey, @Value("${spring.ai.openai.imageUrl}") String openAIImageUrl) {
        this.aiClient = aiClient;
        this.apiKey = apiKey;
        this.openAIImageUrl = openAIImageUrl;
    }

    public String getJoke(String topic) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                I'm curating a collection of country jokes for my website. How about I whip up a joke about {topic} for you?
                """);
        promptTemplate.add("topic", topic);
        ChatResponse response = this.aiClient.call(promptTemplate.create());
        return response.getResults().get(0).toString();

    }

    public String getBestBook(String category, String year) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                I'm on a quest for some captivating books. How about recommending a book in the {category} genre from {year} to kick off my literary exploration?
                But here's the challenge – aim for the absolute best. I fancy myself a book critic, you know. Ratings are a solid starting point.
                Who crafted this literary masterpiece? And who endorsed it? Could you provide a concise plot summary along with its title?
                Hold back a bit on the details; I'd like to revel in the element of surprise.
                And, if you may, present these details in the following JSON format: category, year, bookName, author, review, smallSummary.
                  """);
        Map.of("category", category, "year", year).forEach(promptTemplate::add);
        ChatResponse generate = this.aiClient.call(promptTemplate.create());
        return generate.getResult().toString();
    }


    public InputStreamResource getImage(@RequestParam(name = "topic") String topic) throws URISyntaxException {
        PromptTemplate promptTemplate = createPromptTemplate(topic);
        String imagePrompt = this.aiClient.call(promptTemplate.create()).getResults().get(0).getOutput().getContent();

        // Create HttpEntity with the Map and Headers
        HttpEntity<String> httpEntity = createHttpEntity(imagePrompt);

        byte[] imageBytes = getImageUrl(httpEntity);
        assert imageBytes != null;
        return new InputStreamResource(new java.io.ByteArrayInputStream(imageBytes));
    }

    private HttpEntity<String> createHttpEntity(String imagePrompt) {
        // Create HttpHeaders and set content type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + apiKey);

        // Create HttpEntity with the Map and Headers
        return new HttpEntity<>(imagePrompt, headers);
    }

    private byte[] getImageUrl(HttpEntity<String> httpEntity) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GeneratedImage> responseEntity = restTemplate.exchange(openAIImageUrl, HttpMethod.POST, httpEntity, GeneratedImage.class);
        GeneratedImage generatedImage = responseEntity.getBody();
        String imageUrl = generatedImage != null ? generatedImage.getData().get(0).getUrl() : null;
        return imageUrl != null ? restTemplate.getForObject(new URI(imageUrl), byte[].class) : null;
    }

    private PromptTemplate createPromptTemplate(String topic) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                 I am really board from online memes. Can you create me a prompt about {topic}.
                 Elevate the given topic. Make it classy.
                 Make a resolution of "1024x1024", but ensure that it is presented in json it need to be string.
                 Use the model dall-e-3.
                 I desire only one creation. Give me as JSON format: prompt, n, size, model.
                """);
        promptTemplate.add("topic", topic);
        return promptTemplate;
    }
}
