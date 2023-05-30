package com.kodlamaio.stockservice.business.rules;

import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.stockservice.entities.enums.State;
import com.kodlamaio.stockservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductBusinessRules {
    private final ProductRepository repository;
    public void checkIfProductExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("PRODUCT_NOT_EXISTS");
        }
    }

    public void checkProductAvailability(UUID id) {
        var product = repository.findById(id).orElseThrow();
        if (!product.getState().equals(State.Active)) {
            throw new BusinessException("PRODUCT_NOT_ACTIVE");
        }
    }

    public void checkProductInStock(UUID id, int requestQuantity) {
        var product = repository.findById(id).orElseThrow();
        if (product.getUnitsInStock()<requestQuantity) {
            throw new BusinessException("PRODUCT_IS_NOT_ENOUGH_STOCK");
        }
    }
}
