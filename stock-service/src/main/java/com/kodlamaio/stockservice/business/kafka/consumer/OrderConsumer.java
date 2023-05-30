package com.kodlamaio.stockservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.order.OrderCreatedEvent;
import com.kodlamaio.stockservice.business.abstracts.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {
    private final ProductService service;
    @KafkaListener(
            topics = "order-created",
            groupId = "stock-order-create"
    )
    public void consume(OrderCreatedEvent event) {
        service.updateStock(event.getProductId(), event.getRequestQuantity());
        log.info("Order created event consumed {}", event);
    }
}
