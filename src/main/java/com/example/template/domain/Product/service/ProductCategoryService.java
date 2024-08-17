package com.example.template.domain.Product.service;

import com.example.template.domain.Product.entity.ProductCategory;
import com.example.template.domain.Product.repository.ProductCategoryRepository;
import com.example.template.domain.Product.repository.dto.ProductCategoryDto;
import com.example.template.global.error.ErrorCode;
import com.example.template.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository itemCatagoryRepository;

    @Transactional(readOnly = true)
    public List<ProductCategoryDto> getAllItemCategory() {
        List<ProductCategory> itemCategories = itemCatagoryRepository.findAll();

        List<ProductCategoryDto> productCategoryDtos = new ArrayList<>();

        itemCategories.forEach(itemCategory -> {
            productCategoryDtos.add(
                    ProductCategoryDto.builder()
                            .id(itemCategory.getId())
                            .name(itemCategory.getName())
                            .build());
        });

        return productCategoryDtos;
    }

    @Transactional(readOnly = true)
    public ProductCategory getItemCategory(long id) {
        return itemCatagoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ITEM_CATEGORY_NOT_FOUND));

    }
}
