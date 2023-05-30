package com.kodlamaio.orderservice.api.controllers;

import com.kodlamaio.orderservice.business.abstracts.OrderService;
import com.kodlamaio.orderservice.business.dto.requests.create.CreateOrderRequest;
import com.kodlamaio.orderservice.business.dto.requests.update.UpdateOrderRequest;
import com.kodlamaio.orderservice.business.dto.responses.create.CreateOrderResponse;
import com.kodlamaio.orderservice.business.dto.responses.get.GetAllOrdersResponse;
import com.kodlamaio.orderservice.business.dto.responses.get.GetOrderResponse;
import com.kodlamaio.orderservice.business.dto.responses.update.UpdateOrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/orders")
public class OrdersController {

    private final OrderService service;

    @GetMapping
    public List<GetAllOrdersResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetOrderResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse add(@RequestBody CreateOrderRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateOrderResponse update(@PathVariable UUID id, @RequestBody UpdateOrderRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
