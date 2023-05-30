package com.kodlamaio.orderservice.business.abstracts;

import com.kodlamaio.orderservice.business.dto.requests.create.CreateOrderRequest;
import com.kodlamaio.orderservice.business.dto.requests.update.UpdateOrderRequest;
import com.kodlamaio.orderservice.business.dto.responses.create.CreateOrderResponse;
import com.kodlamaio.orderservice.business.dto.responses.get.GetAllOrdersResponse;
import com.kodlamaio.orderservice.business.dto.responses.get.GetOrderResponse;
import com.kodlamaio.orderservice.business.dto.responses.update.UpdateOrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<GetAllOrdersResponse> getAll();
    GetOrderResponse getById(UUID id);
    CreateOrderResponse add(CreateOrderRequest request);
    UpdateOrderResponse update(UUID id, UpdateOrderRequest request);
    void delete(UUID id);
}
