package com.example.filterservice.business.kafka.consumer;

import com.example.filterservice.business.abstracts.FilterService;
import com.kodlamaio.commonpackage.events.order.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {
    private final FilterService service;;

    @KafkaListener(
            topics = "order-created",
            groupId = "filter-order-create"
    )
    public void consume(OrderCreatedEvent event) {
        service.updateStock(event);
        log.info("Order created event consumed {}", event);
    }
}
