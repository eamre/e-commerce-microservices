package com.example.filterservice.repository;

import com.example.filterservice.entities.Filter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface FilterRepository extends MongoRepository<Filter, String> {
    void deleteByProductId(UUID productId);
    Filter getFilterByProductId(UUID productId);
}
