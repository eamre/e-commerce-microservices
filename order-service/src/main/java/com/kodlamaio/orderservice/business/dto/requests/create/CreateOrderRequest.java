package com.kodlamaio.orderservice.business.dto.requests.create;

import com.kodlamaio.commonpackage.utils.dto.CreateShippingRequest;
import com.kodlamaio.commonpackage.utils.dto.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateOrderRequest {
    private UUID productId;
    private double price;
    private int quantity;

    private PaymentRequest paymentRequest;
    private CreateShippingRequest shippingRequest;
}
