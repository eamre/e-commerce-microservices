package com.kodlamaio.shippingservice.business.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UpdateShippingRequest {
    private UUID orderId;
    private String fullName;
    private String address;
}
