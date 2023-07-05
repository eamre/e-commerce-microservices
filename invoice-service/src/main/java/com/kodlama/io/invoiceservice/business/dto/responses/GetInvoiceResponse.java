package com.kodlama.io.invoiceservice.business.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetInvoiceResponse {
    private String id;
    private String cardHolder;
    private String productName;
    private double price;
    private int quantity;
    private LocalDateTime orderDate;
}
