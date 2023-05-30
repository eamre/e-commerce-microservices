package com.kodlamaio.commonpackage.events.stock;

import com.kodlamaio.commonpackage.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent implements Event {
    private UUID productId;
    private UUID[] categoryIds;
    private String productName;
    private String[] categoryNames;
    private int unitsInStock;
    private double unitPrice;
    private String state;
    private String description;
}
