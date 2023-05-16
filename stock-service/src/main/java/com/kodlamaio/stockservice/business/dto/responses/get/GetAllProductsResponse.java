package com.kodlamaio.stockservice.business.dto.responses.get;

import com.kodlamaio.stockservice.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GetAllProductsResponse {
    private UUID id;
    private List<UUID> categoryIds;
    private String name;
    private int unitsInStock;
    private double unitPrice;
    private State state;
    private String description;
    private List<String> categoryNames;
}
