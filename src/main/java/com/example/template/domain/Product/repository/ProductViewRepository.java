package com.example.template.domain.Product.repository;

import com.example.template.domain.Member.entity.Member;
import com.example.template.domain.Product.entity.Product;
import com.example.template.domain.Product.entity.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, Long> {
    Optional<ProductView> findByMemberAndProduct(Member member, Product product);
    long countByProductId(Long productId);
    // 여러 제품 ID에 대한 조회 횟수를 가져오는 쿼리
    @Query("SELECT pv.product.id AS productId, COUNT(pv) AS viewCount " +
            "FROM ProductView pv " +
            "WHERE pv.product.id IN :productIds " +
            "GROUP BY pv.product.id")
    List<Object[]> countViewsGroupedByProductIds(@Param("productIds") List<Long> productIds);
}
