package com.compusfishqwq.compus_fishqwq.repository;

import com.compusfishqwq.compus_fishqwq.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductRepository
 * -----------------
 * 负责对商品表（product）进行数据库访问。
 * Spring Data JPA 会自动实现此接口中定义的方法。
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  
    List<Product> findByTitleContaining(String name);

   
    List<Product> findByUserId(Long userId);

  
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}

