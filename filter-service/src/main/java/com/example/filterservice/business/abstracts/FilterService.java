package com.example.filterservice.business.abstracts;

import com.example.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.example.filterservice.business.dto.responses.GetFilterResponse;
import com.example.filterservice.entities.Filter;
import com.kodlamaio.commonpackage.events.order.OrderCreatedEvent;

import java.util.List;
import java.util.UUID;

public interface FilterService {
    List<GetAllFiltersResponse> getAll();
    GetFilterResponse getById(String id);
    void add(Filter filter);
    void delete(String id);
    void deleteByProductId(UUID productId);
    void updateStock(OrderCreatedEvent event);
}
