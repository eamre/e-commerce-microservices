package com.example.filterservice.business.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllFiltersResponse {
    private String id;
    private UUID productId;
    private UUID[] categoryIds;
    private String productName;
    private String[] categoryNames;
    private int unitsInStock;
    private double unitPrice;
    private String state;
    private String description;
}
