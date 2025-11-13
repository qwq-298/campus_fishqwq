package com.compusfishqwq.compus_fishqwq.service;

import com.compusfishqwq.compus_fishqwq.entity.Product;
import com.compusfishqwq.compus_fishqwq.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ProductService
 * -----------------
 * 负责商品相关业务逻辑，如发布、查询、删除、搜索等。
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 获取所有商品
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * 根据ID获取商品
     */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * 发布或更新商品
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * 删除商品
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * 根据商品名称模糊搜索
     */
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByTitleContaining(name);
    }

    /**
     * 查询指定卖家的商品
     */
    public List<Product> getProductsBySeller(Long userId) {
        return productRepository.findByUserId(userId);
    }

    /**
     * 按价格区间查询商品
     */
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
}

