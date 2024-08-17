package com.example.template.domain.Product.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/*
* @Data = @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode
* 5개를 포함
* */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private long id;
    private String title;
    private String content;
    private long price;
    private boolean isNego;
    private long itemCategoryId;
    private UUID memberUUID;
}
