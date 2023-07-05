package com.kodlama.io.invoiceservice.business.kafka.consumer;

import com.kodlama.io.invoiceservice.business.abstracts.InvoiceService;
import com.kodlama.io.invoiceservice.business.dto.requests.CreateInvoiceRequest;
import com.kodlamaio.commonpackage.events.invoice.InvoiceCreatedEvent;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderConsumer {
    private final InvoiceService service;
    private final ModelMapperService mapper;

    @KafkaListener(
            topics = "invoice-created",
            groupId = "invoice-order-create"
    )
    public void consume(InvoiceCreatedEvent event){
        var invoice = mapper.forResponse().map(event, CreateInvoiceRequest.class);
        service.add(invoice);
        log.info("invoice created event consumed {}", event);
    }
}
