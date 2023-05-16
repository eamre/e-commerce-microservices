package com.kodlamaio.stockservice.repository;

import com.kodlamaio.stockservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
