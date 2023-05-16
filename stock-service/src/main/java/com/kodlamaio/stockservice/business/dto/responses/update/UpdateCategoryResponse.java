package com.kodlamaio.stockservice.business.dto.responses.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UpdateCategoryResponse {
    private UUID id;
    private String name;
    private String description;
}
