package com.example.template.domain.Product.repository;

import com.example.template.domain.Product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findAll();
    Optional<ProductCategory> findById(Long id);
}
