package com.leonardozw.orderservice.order.domain;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.leonardozw.orderservice.book.Book;
import com.leonardozw.orderservice.book.BookClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private final BookClient bookClient;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, BookClient bookClient){
        this.orderRepository = orderRepository;
        this.bookClient = bookClient;
    }

    public Flux<Order> getALLOrders(){
        return orderRepository.findAll();
    }

    public Mono<Order> submitOrder(String isbn, int quantity){
		return bookClient.getBookByIsbn(isbn)
            .map(book -> buildAcceptedOrder(book, quantity))
            .onErrorResume(WebClientResponseException.NotFound.class, ex -> {
                return Mono.just(buildRejectedOrder(isbn, quantity));
            })
            .flatMap(orderRepository::save);
    }

    public static Order buildAcceptedOrder(Book book, int quantity){
        return Order.of(book.isbn(), book.title() + " - " + book.author(), book.price(), quantity, OrderStatus.ACCEPTED);
    }

    public static Order buildRejectedOrder(String bookIsbn, int quantity){
        return Order.of(bookIsbn, null, null, quantity, OrderStatus.REJECTED);
    }
}
