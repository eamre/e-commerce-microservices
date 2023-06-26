package com.kodlamaio.shippingservice.business.dto.responses;

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
public class GetAllShippingResponse {
    private UUID id;
    private UUID orderId;
    private String fullName;
    private String address;
    private String shippingCode;
    private LocalDateTime shippingDate;
}
