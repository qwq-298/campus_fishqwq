package com.compusfishqwq.compus_fishqwq.controller;

import com.compusfishqwq.compus_fishqwq.entity.Product;
import com.compusfishqwq.compus_fishqwq.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ProductController
 * -----------------
 * 负责处理与商品（Product）相关的 HTTP 请求：
 * 包括发布、查询、删除、按名称搜索、按价格区间筛选等。
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /** 获取所有商品 */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /** 根据 ID 查询商品 */
    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    /** 新增或更新商品 */
    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    /** 删除商品 */
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product " + id + " deleted successfully.";
    }

    /** 按名称模糊搜索商品 */
    @GetMapping("/search")
    public List<Product> searchProductsByTitle(@RequestParam("title") String title) {
        return productService.searchProductsByName(title);
    }

    /** 查询指定卖家的商品 */
    @GetMapping("/seller/{sellerId}")
    public List<Product> getProductsBySeller(@PathVariable Long sellerId) {
        return productService.getProductsBySeller(sellerId);
    }

    /** 按价格区间查询商品 */
    @GetMapping("/price")
    public List<Product> getProductsByPriceRange(
            @RequestParam("min") Double minPrice,
            @RequestParam("max") Double maxPrice) {
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }
}

