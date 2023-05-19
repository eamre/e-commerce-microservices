package com.example.filterservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Document
public class Filter {
    @Id
    private String id;
    private UUID productId;
    private UUID[] categoryId;
    private String productName;
    private String[] categoryName;
    private int unitsInStock;
    private double unitPrice;
    private String state;
    private String description;
}
