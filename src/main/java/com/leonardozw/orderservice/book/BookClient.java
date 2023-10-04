package com.leonardozw.orderservice.book;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class BookClient {

    private static final String BOOKS_ROOT_API = "/books/";
    private final WebClient webClient;

    public BookClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Book> getBookByIsbn(String Isbn){
        return webClient
            .get()
            .uri(BOOKS_ROOT_API + Isbn)
            .retrieve()
            .bodyToMono(Book.class);
    }
}
