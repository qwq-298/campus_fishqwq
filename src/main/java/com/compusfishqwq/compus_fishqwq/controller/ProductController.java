package com.compusfishqwq.compus_fishqwq.controller;

import com.compusfishqwq.compus_fishqwq.entity.Product;
import com.compusfishqwq.compus_fishqwq.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.compusfishqwq.compus_fishqwq.entity.User;
import com.compusfishqwq.compus_fishqwq.repository.UserRepository;
import com.compusfishqwq.compus_fishqwq.entity.SellerInfoDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    @Autowired
    private UserRepository userRepository;
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

    @GetMapping("/{id}/seller")
    public SellerInfoDTO getSeller(@PathVariable Long id) {
       Product product = productService.getProductById(id).orElse(null); // 你已有的查询逻辑
       User seller = product.getUser();
       return new SellerInfoDTO(seller.getUsername(), seller.getEmail());
    }
    
    @GetMapping("/show/{id}")
    public List<Product> getProductWithSeller(@PathVariable Long id){
        return productService.getProductsBySeller(id);
    }

    @GetMapping("/randomtwo")
    public List<Product> getrandomProducts(){
        List<Product> allProducts=productService.getAllProducts();
        List<Product> randomProducts=new ArrayList<>();
        int size=allProducts.size();
        if(size<=2){
            return allProducts;
        }
        else{
            Random rand=new Random();
            while(randomProducts.size()<2){
                int index=rand.nextInt(size);
                Product pro=allProducts.get(index);
                if(randomProducts.contains(pro)){
                    continue;
                }
                else{
                    randomProducts.add(pro);
                }
            }
            return randomProducts;
        }
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

    @PostMapping("api/publish")
    public Map<String,Object> publishProduct(
        @RequestParam String title,
        @RequestParam String description,
        @RequestParam BigDecimal price,
        @RequestParam String category,
        @RequestParam String username,
         @RequestParam(required = false) String images 
    ){
        Map<String,Object> result= new HashMap<>();
        User user=userRepository.findByUsername(username).orElse(null);
        if(user==null){
           result.put("success",false);
           result.put("msg","用户不存在");
           return result;
        }
       /*  Product product=new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setUser(user);

        if (images != null && !images.isEmpty()) {
        List<String> imageList = List.of(images.split(",")); // 逗号分割
        product.setImages(imageList);
        }

        productService.saveProduct(product);*/
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setUser(user);

        // 先保存 product
        productService.saveProduct(product);  // 先 flush，确保 product.id 已生成

        if(images != null && !images.isEmpty()) {
          //List<String> imageList = Arrays.asList(images.split(","));
          //List<String> imageList = new ArrayList<>(images);
          //product.setImages(imageList);
          product.setImages(new ArrayList<>(Arrays.asList(images.split(","))));
          productService.saveProduct(product); // 再保存 images
        }

        result.put("success",true);
        result.put("msg","发布成功");
        result.put("product",product);
        return result;
    }
}

