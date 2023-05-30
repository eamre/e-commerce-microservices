package com.kodlamaio.stockservice.api.controller;

import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import com.kodlamaio.stockservice.business.abstracts.ProductService;
import com.kodlamaio.stockservice.business.dto.requests.create.CreateProductRequest;
import com.kodlamaio.stockservice.business.dto.requests.update.UpdateProductRequest;
import com.kodlamaio.stockservice.business.dto.responses.create.CreateProductResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetAllProductsResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetProductResponse;
import com.kodlamaio.stockservice.business.dto.responses.update.UpdateProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductsController {
    private final ProductService service;

    @GetMapping
    public List<GetAllProductsResponse> getAll(@RequestParam(defaultValue = "true")boolean showInactive) {
        return service.getAll(showInactive);
    }

    @GetMapping("/{id}")
    public GetProductResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductResponse add(@RequestBody CreateProductRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateProductResponse update(@PathVariable UUID id, @RequestBody UpdateProductRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/check-product-available/{id}")
    public ClientResponse checkIfProductAvailable(@PathVariable UUID id) {
        return service.checkIfProductAvailable(id);
    }

    @GetMapping("/check-product-in-stock/{id}/{requestQuantity}")
    public ClientResponse checkIsProductInStock(@PathVariable UUID id, @PathVariable int requestQuantity) {
        return service.checkIsProductInStock(id, requestQuantity);
    }


}
