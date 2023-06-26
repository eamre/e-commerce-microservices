package com.kodlamaio.shippingservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.shipping.ShippingCreatedEvent;
import com.kodlamaio.commonpackage.utils.dto.CreateShippingRequest;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.shippingservice.business.abstacts.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {
    private final ShippingService service;;
    private final ModelMapperService mapper;

    @KafkaListener(
            topics = "shipping-created",
            groupId = "shipping-create"
    )
    public void consume(ShippingCreatedEvent event) {
        var shipping = mapper.forRequest().map(event, CreateShippingRequest.class);
        service.add(shipping);
        log.info("shipping created event consumed {}", event);
    }
}
