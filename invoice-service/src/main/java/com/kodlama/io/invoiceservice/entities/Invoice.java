package com.kodlama.io.invoiceservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Document
public class Invoice {
    @Id
    private String id;
    private String cardHolder;
    private String productName;
    private double price;
    private int quantity;
    private LocalDateTime orderDate;
}
