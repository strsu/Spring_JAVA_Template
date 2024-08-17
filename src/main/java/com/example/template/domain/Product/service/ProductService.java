package com.example.template.domain.Product.service;

import com.example.template.domain.Product.entity.Product;
import com.example.template.domain.Product.entity.ProductCategory;
import com.example.template.domain.Product.entity.ProductView;
import com.example.template.domain.Product.repository.ProductRepository;
import com.example.template.domain.Product.repository.ProductViewRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final AuthUtil authUtil;
    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService;
    private final ProductViewRepository productViewRepository;

    @Transactional
    public ProductResponseDto uploadProduct(ProductRequestDto productRequestDto) {

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
                .id(product.getId())
                .title(product.getTitle())
                .content(product.getContent())
                .price(product.getPrice())
                .isNego(product.isNego())
                .itemCategoryId(product.getProductCategory().getId())
                .memberUUID(product.getMember().getUuid())
                .build();
    }

    public Page<ProductResponseDto> getProducts(int pageNo, int pageSize) {
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

        // 페이징된 결과에서 제품 ID를 추출
        List<Long> productIds = itemPage.getContent().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        // 제품 ID 리스트를 기반으로 조회 횟수 가져오기
        List<Object[]> results = productViewRepository.countViewsGroupedByProductIds(productIds);

        Map<Long, Long> viewCountMap = new HashMap<>();
        for (Object[] result : results) {
            Long productId = (Long) result[0];  // productId를 Long으로 추출
            Long viewCount = (Long) result[1];  // viewCount를 Long으로 추출
            viewCountMap.put(productId, viewCount);
        }

        List<ProductResponseDto> itemDtos = itemPage.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getTitle(),
                        product.getContent(),
                        product.getPrice(),
                        product.isNego(),
                        product.getProductCategory().getId(),
                        product.getMember().getUuid(),
                        viewCountMap.getOrDefault(product.getId(), 0L)
                ))
                .collect(Collectors.toList());

        // Page<ItemDto>로 변환하여 반환
        return new PageImpl<>(itemDtos, pageable, itemPage.getTotalElements());
    }

    public ProductResponseDto getProductDetail(long id) {
        Member loginMember = authUtil.getLoginMember();

        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        // 자신의 게시물이 아니라면
        if(product.getMember() != loginMember) {
            if(productViewRepository.findByMemberAndProduct(loginMember, product).isEmpty()) {
                // 조회 기록을 남김
                ProductView productView = ProductView.builder()
                        .member(loginMember)
                        .product(product)
                        .build();
                productViewRepository.save(productView);
            }
        }

        long viewCnt = productViewRepository.countByProductId(id);

        return ProductResponseDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .content(product.getContent())
                .price(product.getPrice())
                .isNego(product.isNego())
                .memberUUID(product.getMember().getUuid())
                .itemCategoryId(product.getProductCategory().getId())
                .viewCnt(viewCnt)
                .build();
    }
}
