package com.kodlamaio.stockservice.business.concretes;

import com.kodlamaio.commonpackage.events.stock.ProductCreatedEvent;
import com.kodlamaio.commonpackage.kafka.producer.KafkaProducer;
import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.stockservice.business.abstracts.CategoryService;
import com.kodlamaio.stockservice.business.abstracts.ProductService;
import com.kodlamaio.stockservice.business.dto.requests.create.CreateProductRequest;
import com.kodlamaio.stockservice.business.dto.requests.update.UpdateProductRequest;
import com.kodlamaio.stockservice.business.dto.responses.create.CreateProductResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetAllProductsResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetCategoryResponse;
import com.kodlamaio.commonpackage.utils.dto.GetProductResponse;
import com.kodlamaio.stockservice.business.dto.responses.update.UpdateProductResponse;
import com.kodlamaio.stockservice.business.rules.ProductBusinessRules;
import com.kodlamaio.stockservice.entities.Category;
import com.kodlamaio.stockservice.entities.Product;
import com.kodlamaio.stockservice.entities.enums.State;
import com.kodlamaio.stockservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductManager implements ProductService {
    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final ModelMapperService mapper;
    private final KafkaProducer producer;
    private final ProductBusinessRules rules;
    @Override
    public List<GetAllProductsResponse> getAll(boolean showInactive) {
        var products = repository.findAll();
        var responses = products
                .stream()
                .filter(product -> showInactive || !product.getState().equals(State.Inactive))
                .map(product -> {
                    var response = mapper.forResponse().map(product, GetAllProductsResponse.class);
                    response.setCategoryNames(product.getCategories().stream().map(category -> category.getName()).toList());
                    response.setCategoryIds(product.getCategories().stream().map(category -> category.getId()).toList());
                    return response;
                })
                .toList();
        return responses;
    }

    @Override
    public GetProductResponse getById(UUID id) {
        var product = repository.findById(id).orElseThrow();
        var response  = mapper.forResponse().map(product, GetProductResponse.class);
        response.setCategoryNames(product.getCategories().stream().map(category -> category.getName()).toList());
        response.setCategoryIds(product.getCategories().stream().map(category -> category.getId()).toList());
        return response;
    }

    @Override
    public CreateProductResponse add(CreateProductRequest request) {
        var product = mapper.forRequest().map(request, Product.class);
        List<UUID> categoryIds = setCategoryForProduct(request.getCategoryIds(), product);
        product.setId(null);
        repository.save(product);

        var response = mapper.forResponse().map(product, CreateProductResponse.class);
        response.setCategoryIds(categoryIds);
        response.setCategoryNames(product.getCategories().stream().map(category -> category.getName()).toList());
        var createdProduct = response;
        sendKafkaCarCreatedEvent(createdProduct);

        return response;
    }

    @Override
    public UpdateProductResponse update(UUID id, UpdateProductRequest request) {
        var product = mapper.forRequest().map(request, Product.class);
        product.setId(id);
        List<UUID> categoryIds = setCategoryForProduct(request.getCategoryIds(), product);
        repository.save(product);

        var response = mapper.forResponse().map(product, UpdateProductResponse.class);
        response.setCategoryIds(categoryIds);

        return response;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public ClientResponse checkIfProductAvailable(UUID id) {
        var response = new ClientResponse();
        validateProductAvailability(id, response);
        return response;
    }

    @Override
    public ClientResponse checkIsProductInStock(UUID id, int requestQuantity) {
        var response = new ClientResponse();
        validateProductInStock(id, requestQuantity, response);
        return response;
    }

    @Override
    public void updateStock(UUID id, int requestQuantity) {
        var product = repository.findById(id).orElseThrow();
        product.setUnitsInStock(product.getUnitsInStock()-requestQuantity);
        if(product.getUnitsInStock()==0){
            product.setState(State.OutOfStock);
        }
        repository.save(product);
    }

    private List<UUID> setCategoryForProduct(List<UUID> categoryIds, Product product) {
        Set<Category> categories = new HashSet<>();
        for (UUID categoryId : categoryIds) {
            GetCategoryResponse categoryResponse = categoryService.getById(categoryId);
            Category category = mapper.forResponse().map(categoryResponse, Category.class);
            categories.add(category);
        }
        product.setCategories(categories);
        List<UUID> categoryList = categories
                .stream()
                .map(category -> category.getId())
                .toList();
        return categoryList;
    }

    private void sendKafkaCarCreatedEvent(CreateProductResponse createdProduct) {
        var event = mapper.forResponse().map(createdProduct, ProductCreatedEvent.class);
        producer.sendMessage(event, "product-created");
    }
    private void validateProductInStock(UUID id, int requestQuantity, ClientResponse response) {
        try {
            rules.checkIfProductExists(id);
            rules.checkProductInStock(id,requestQuantity);
            response.setSuccess(true);
        } catch (BusinessException exception) {
            response.setSuccess(false);
            response.setMessage(exception.getMessage());
        }
    }

    private void validateProductAvailability(UUID id, ClientResponse response) {
        try {
            rules.checkIfProductExists(id);
            rules.checkProductAvailability(id);
            response.setSuccess(true);
        } catch (BusinessException exception) {
            response.setSuccess(false);
            response.setMessage(exception.getMessage());
        }
    }
}
