package com.kodlamaio.stockservice.business.abstracts;

import com.kodlamaio.stockservice.business.dto.requests.create.CreateCategoryRequest;
import com.kodlamaio.stockservice.business.dto.requests.update.UpdateCategoryRequest;
import com.kodlamaio.stockservice.business.dto.responses.create.CreateCategoryResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetAllCategoriesResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetCategoryResponse;
import com.kodlamaio.stockservice.business.dto.responses.update.UpdateCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<GetAllCategoriesResponse> getAll();
    GetCategoryResponse getById(UUID id);
    CreateCategoryResponse add(CreateCategoryRequest request);
    UpdateCategoryResponse update(UUID id, UpdateCategoryRequest request);
    void delete(UUID id);

}
