package com.kodlamaio.stockservice.api.controller;

import com.kodlamaio.stockservice.business.abstracts.CategoryService;
import com.kodlamaio.stockservice.business.dto.requests.create.CreateCategoryRequest;
import com.kodlamaio.stockservice.business.dto.requests.update.UpdateCategoryRequest;
import com.kodlamaio.stockservice.business.dto.responses.create.CreateCategoryResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetAllCategoriesResponse;
import com.kodlamaio.stockservice.business.dto.responses.get.GetCategoryResponse;
import com.kodlamaio.stockservice.business.dto.responses.update.UpdateCategoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/categories")
public class CategoriesController {
    private final CategoryService service;

    @GetMapping
    public List<GetAllCategoriesResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetCategoryResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCategoryResponse add(@RequestBody CreateCategoryRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateCategoryResponse update(@PathVariable UUID id, @RequestBody UpdateCategoryRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
