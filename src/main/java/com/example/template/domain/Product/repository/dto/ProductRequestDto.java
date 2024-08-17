package com.example.template.domain.Product.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private String title;
    private String content;
    private long price;
    private boolean isNego;
    private long itemCategoryId;
}
