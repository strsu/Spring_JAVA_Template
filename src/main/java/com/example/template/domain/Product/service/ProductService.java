package com.example.template.domain.Product.service;

import com.example.template.domain.Product.entity.Product;
import com.example.template.domain.Product.entity.ProductCategory;
import com.example.template.domain.Product.repository.ProductRepository;
import com.example.template.domain.Product.repository.dto.ProductRequestDto;
import com.example.template.domain.Product.repository.dto.ProductResponseDto;
import com.example.template.domain.Member.entity.Member;
import com.example.template.global.error.ErrorCode;
import com.example.template.global.error.exception.EntityNotFoundException;
import com.example.template.global.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final AuthUtil authUtil;
    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService;

    @Transactional
    public ProductResponseDto uploadItem(ProductRequestDto productRequestDto) {

        /*
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member member;
            if (authentication != null && authentication.getPrincipal() instanceof Member) {
                member = (Member) authentication.getPrincipal();
            } else {
                throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
            }
        * */

        Member loginMember = authUtil.getLoginMember();

        ProductCategory productCategory = productCategoryService.getItemCategory(productRequestDto.getItemCategoryId());

        Product product = Product.builder()
                .title(productRequestDto.getTitle())
                .content(productRequestDto.getContent())
                .price(productRequestDto.getPrice())
                .isNego(productRequestDto.isNego())
                .productCategory(productCategory)
                .member(loginMember)
                .build();

        productRepository.save(product);

        return ProductResponseDto.builder()
                .title(product.getTitle())
                .content(product.getContent())
                .price(product.getPrice())
                .isNego(product.isNego())
                .itemCategoryId(product.getProductCategory().getId())
                .memberId(product.getMember().getId())
                .build();
    }

    public Page<ProductResponseDto> getItems(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        /*
        * [문제]
        * return itemRepository.findAll(pageable); // 을 하면
        * -> Type definition error: [simple type, class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor]
        *  위같은 오류가 발생한다. 이는 Entity에서 fetch = FetchType.LAZY 로 설정된 필드가 jackson이 안되서!
        * [해결]
        * DTO 를 활용한다.
        * */
        Page<Product> itemPage = productRepository.findAll(pageable);

        List<ProductResponseDto> itemDtos = itemPage.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getTitle(),
                        product.getContent(),
                        product.getPrice(),
                        product.isNego(),
                        product.getProductCategory().getId(),
                        product.getMember().getId()
                ))
                .collect(Collectors.toList());

        // Page<ItemDto>로 변환하여 반환
        return new PageImpl<>(itemDtos, pageable, itemPage.getTotalElements());
    }
}
