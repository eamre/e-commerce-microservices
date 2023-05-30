package com.kodlamaio.orderservice.business.dto.requests.update;

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
public class UpdateOrderRequest {
    private UUID productId;
    private double price;
    private int quantity;
    private LocalDateTime saleDate;
}
