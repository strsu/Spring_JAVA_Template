package com.example.template.domain.Product.controller;

import com.example.template.domain.Product.repository.dto.ProductCategoryDto;
import com.example.template.domain.Product.repository.dto.ProductRequestDto;
import com.example.template.domain.Product.repository.dto.ProductResponseDto;
import com.example.template.domain.Product.service.ProductCategoryService;
import com.example.template.domain.Product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item") // 하위 Controller에 모두 /api/ 이 붙는다
public class ProductController {

    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @GetMapping("/category")
    @ResponseBody
    public List<ProductCategoryDto> getResourceGroupList() {
        return productCategoryService.getAllItemCategory();
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ProductResponseDto>> getProducts(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        Page<ProductResponseDto> products = productService.getItems(pageNo, pageSize);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/upload")
    @ResponseBody
    public ProductResponseDto itemUpload(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.uploadItem(productRequestDto);
    }
}
