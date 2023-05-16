package com.kodlamaio.stockservice.business.concretes;

import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.stockservice.business.abstracts.CategoryService;
import com.kodlamaio.stockservice.business.dto.requests.create.CreateCategoryRequest;
import com.kodlamaio.stockservice.business.dto.requests.update.UpdateCategoryRequest;
import com.kodlamaio.stockservice.business.dto.responses.create.CreateCategoryResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetAllCategoriesResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetCategoryResponse;
import com.kodlamaio.stockservice.business.dto.responses.update.UpdateCategoryResponse;
import com.kodlamaio.stockservice.entities.Category;
import com.kodlamaio.stockservice.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CategoryManager implements CategoryService {
    private final CategoryRepository repository;
    private final ModelMapperService mapper;
    @Override
    public List<GetAllCategoriesResponse> getAll() {
        var categories = repository.findAll();
        var responses = categories
                .stream()
                .map(category -> mapper.forResponse().map(category, GetAllCategoriesResponse.class))
                .toList();

        return responses;
    }

    @Override
    public GetCategoryResponse getById(UUID id) {
        var product = repository.findById(id).orElseThrow();
        var response  = mapper.forResponse().map(product, GetCategoryResponse.class);

        return response;
    }

    @Override
    public CreateCategoryResponse add(CreateCategoryRequest request) {
        var category = mapper.forRequest().map(request, Category.class);
        repository.save(category);

        var response = mapper.forResponse().map(category, CreateCategoryResponse.class);
        return response;
    }

    @Override
    public UpdateCategoryResponse update(UUID id, UpdateCategoryRequest request) {
        var category = mapper.forRequest().map(request, Category.class);
        category.setId(id);
        repository.save(category);

        var response = mapper.forResponse().map(category, UpdateCategoryResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
