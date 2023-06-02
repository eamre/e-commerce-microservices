package com.kodlamaio.orderservice.api.clients;

import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import com.kodlamaio.commonpackage.utils.dto.CreateRentalPaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentClientFallback implements PaymentClient{
    @Override
    public ClientResponse checkPaymentProcess(CreateRentalPaymentRequest request) {
        log.info("Payment SERVICE IS DOWN!");
        throw new RuntimeException("Payment-SERVICE NOT AVAILABLE RIGHT NOW!");
    }
}
