package com.kodlamaio.orderservice.api.clients;

import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "stock-service", fallback = ProductClientFallback.class)
public interface ProductClient {
    @GetMapping(value = "/api/products/check-product-available/{productId}")
    @Retry(name = "retry-stock")
    ClientResponse checkIfProductAvailable(@PathVariable UUID productId);

    @GetMapping(value = "/api/products/check-product-in-stock/{productId}/{requestQuantity}")
    @Retry(name = "retry-stock2")
    ClientResponse checkIsProductInStock(@PathVariable UUID productId, @PathVariable int requestQuantity);
}
