# CREATE GENERATIVE AI USING SPRING BOOT AND SPRING AI

## Description

This Spring Boot application generates Joke and book, and image based on a provided topic from OpenAI. 
The idea for this project came from an interesting article I read. 
I was inspired by the concepts and decided to implement them in this application. 
Over time, I've made several updates to improve the functionality and ensure it works smoothly.

About the image generation, I used the configuration:
- One image per generation
- Model `dall-e-3`
- Image size `"1024x1024"`.

Below, an image generated with the prompt : "St Laurent in Canada"
![Screenshot of the app](images/St_laurent_Quebec.jpeg)

## Installation

1. Clone the repository: `git clone https://github.com/eyarga/GenerativeAi-SpringBootApplication-SpringAI.git`
2. Navigate into the directory: `GenerativeAi`
3. Go to the application.properties and update the `spring.ai.openai.apikey` with your `API_KEY` from OpenAI
4. Install the dependencies: `mvn install`

## Usage

Run the application: `mvn spring-boot:run`
http://localhost:8080/swagger-ui/index.html

## Future Updates

I plan to continue refining and expanding this project. 
Future updates will focus on making the application more dynamic and interactive, with less reliance on static text.

## Contributing

Pull requests are welcome. 
For major changes, please open an issue first to discuss what you would like to change.
