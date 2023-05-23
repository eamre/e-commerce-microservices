package com.example.filterservice.business.kafka.consumer;

import com.example.filterservice.business.abstracts.FilterService;
import com.example.filterservice.entities.Filter;
import com.kodlamaio.commonpackage.events.stock.ProductCreatedEvent;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockConsumer {
    private final FilterService service;
    private final ModelMapperService mapper;

    @KafkaListener(
            topics = "product-created",
            groupId = "product-create"
    )
    public void consume(ProductCreatedEvent event) {
        var filter = mapper.forResponse().map(event, Filter.class);
        service.add(filter);
        log.info("Product created event consumed {}", event);
    }
}
