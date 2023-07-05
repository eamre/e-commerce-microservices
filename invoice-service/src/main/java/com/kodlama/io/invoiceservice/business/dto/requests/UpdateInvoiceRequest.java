package com.kodlama.io.invoiceservice.business.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInvoiceRequest {
    private String cardHolder;
    private String productName;
    private double price;
    private int quantity;
    private LocalDateTime orderDate;
}
