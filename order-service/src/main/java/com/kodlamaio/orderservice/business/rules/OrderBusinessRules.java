package com.kodlamaio.orderservice.business.rules;

import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.orderservice.api.clients.ProductClient;
import com.kodlamaio.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderBusinessRules {
    private final OrderRepository repository;
    private final ProductClient productClient;

    public void checkIfOrderExists(UUID id){
        if(!repository.existsById(id)){
            throw new BusinessException("ORDER_NOT_EXISTS");
        }
    }
    public void ensureProductIsActive(UUID productId){
        var response = productClient.checkIfProductAvailable(productId);
        if(!response.isSuccess()){
            throw new BusinessException(response.getMessage());
        }
    }

    public void ensureProductHaveEnoughStock(UUID productId, int requestQuantity){
        var response = productClient.checkIsProductInStock(productId, requestQuantity);
        if(!response.isSuccess()){
            throw new BusinessException(response.getMessage());
        }
    }
}
