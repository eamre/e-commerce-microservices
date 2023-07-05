package com.kodlamaio.orderservice.api.clients;

import com.kodlamaio.commonpackage.utils.dto.GetProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import java.util.UUID;
@Slf4j
@Component
public class ProductClientFallback implements ProductClient{
    @Override
    public ClientResponse checkIfProductAvailable(UUID productId) {
        log.info("STOCK SERVICE IS DOWN!");
        throw new RuntimeException("STOCK-SERVICE NOT AVAILABLE RIGHT NOW!checkIfProductAvailable");
    }

    @Override
    public ClientResponse checkIsProductInStock(UUID productId,int requestQuantity) {
        log.info("STOCK SERVICE IS DOWN! checkIsProductInStock");
        throw new RuntimeException("STOCK-SERVICE NOT AVAILABLE RIGHT NOW!checkIsProductInStock");
    }

    @Override
    public GetProductResponse getById(UUID productId) {
        log.info("STOCK SERVICE IS DOWN! getById");
        throw new RuntimeException("STOCK-SERVICE NOT AVAILABLE RIGHT NOW!getById");
    }
}
