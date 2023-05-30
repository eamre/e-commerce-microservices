package com.kodlamaio.orderservice.business.dto.responses.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateOrderResponse {
    private UUID id;
    private UUID productId;
    private double price;
    private int quantity;
    private double totalPrice;
    private LocalDateTime saleDate;
}
