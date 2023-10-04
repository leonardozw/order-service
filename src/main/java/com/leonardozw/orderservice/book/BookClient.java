package com.leonardozw.orderservice.book;

import java.time.Duration;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class BookClient {

    private static final String BOOKS_ROOT_API = "/books/";
    private final WebClient webClient;

    public BookClient(WebClient webClient) {
        this.webClient = webClient;
    }

    /* retryWhen can be put before or after timeout!, keep in mind that if it put before all retries will hapen within the given time limit
     * and if it put after timeout, the timeout will be applied to each retry.
     */
    public Mono<Book> getBookByIsbn(String Isbn){
        return webClient
            .get()
            .uri(BOOKS_ROOT_API + Isbn)
            .retrieve()
            .bodyToMono(Book.class)
            .timeout(Duration.ofSeconds(3), Mono.empty()) // Sets a 3-second timeout for the GET request and can return an empty Mono if the timeout occurs.
            .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.empty()) // If the response is 404, returns a empty Mono.
            .retryWhen(Retry.backoff(3, Duration.ofMillis(100))) // Sets a retry policy that retries up to 3 times with a 100-millisecond delay between retries.
            .onErrorResume(Exception.class, ex -> Mono.empty()); // If any other exception occurs after the retry attempts, returns an empty Mono.
    }

}
