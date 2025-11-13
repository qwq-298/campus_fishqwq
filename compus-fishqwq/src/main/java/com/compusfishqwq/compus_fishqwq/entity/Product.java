package com.compusfishqwq.compus_fishqwq.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long id;

    @Column(nullable = false, length = 100)
    private String title; // 商品标题

    @Column(length = 500)
    private String description; // 商品描述

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // 商品价格

    @Column(length = 50)
    private String category; // 分类（如：书籍/电子产品/生活用品）

    @Column(length = 20)
    private String status = "ON_SALE"; // 状态（ON_SALE / SOLD / REMOVED）

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publish_time")
    private Date publishTime = new Date(); // 发布时间

    // 商品图片URL（简化为字符串存储，可扩展为 Image 表）
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> images;

    // 多个商品属于一个用户
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 一个商品可以对应多个订单项
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Order> orders;

    // --- Getter & Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getPublishTime() { return publishTime; }
    public void setPublishTime(Date publishTime) { this.publishTime = publishTime; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}
