package com.leonardozw.orderservice.order.web;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import com.leonardozw.orderservice.order.domain.Order;
import com.leonardozw.orderservice.order.domain.OrderStatus;

@JsonTest
public class OrderJsonTest {
    
    @Autowired
    private JacksonTester<Order> json;

    @Test
    void testSerialize() throws Exception{
        var order = new Order(394L, "1234567890", "Book Name", 9.90, 1, OrderStatus.ACCEPTED, Instant.now(), Instant.now(), 21);
        var jsonContent = json.write(order);

        assertThat(jsonContent).extractingJsonPathNumberValue("@.id").isEqualTo(order.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.bookIsbn").isEqualTo(order.bookIsbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.bookName").isEqualTo(order.bookName());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.bookPrice").isEqualTo(order.bookPrice());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.quantity").isEqualTo(order.quantity());
        assertThat(jsonContent).extractingJsonPathStringValue("@.status").isEqualTo(order.status().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdDate").isEqualTo(order.createdDate().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedDate").isEqualTo(order.lastModifiedDate().toString());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version").isEqualTo(order.version());
    }
}
