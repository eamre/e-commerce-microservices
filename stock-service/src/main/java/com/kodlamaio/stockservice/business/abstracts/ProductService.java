package com.kodlamaio.stockservice.business.abstracts;

import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import com.kodlamaio.stockservice.business.dto.requests.create.CreateProductRequest;
import com.kodlamaio.stockservice.business.dto.requests.update.UpdateProductRequest;
import com.kodlamaio.stockservice.business.dto.responses.create.CreateProductResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetAllProductsResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetProductResponse;
import com.kodlamaio.stockservice.business.dto.responses.update.UpdateProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<GetAllProductsResponse> getAll(boolean showInactive);
    GetProductResponse getById(UUID id);
    CreateProductResponse add(CreateProductRequest request);
    UpdateProductResponse update(UUID id, UpdateProductRequest request);
    void delete(UUID id);
    ClientResponse checkIfProductAvailable (UUID id);
    ClientResponse checkIsProductInStock (UUID id, int requestQuantity);
    void updateStock(UUID id, int requestQuantity);
}
