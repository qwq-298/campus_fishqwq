/*package com.compusfishqwq.compus_fishqwq.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity  // 必须有这个注解！
@Table (name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // 记得加上无参构造函数，否则JPA会出错
    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getter & Setter 方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}*/
package com.compusfishqwq.compus_fishqwq.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    // 🔴 关键修改：新增 username 字段
    @Column(unique = true, nullable = false, length = 50) 
    private String username; 
    
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    // 一个用户可以发布多个商品
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Product> products;

    // 一个用户可以创建多个订单
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<Order> orders;

    public User() {}

    // 🔴 关键修改：更新构造函数以包含 username
    public User(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }

    // --- Getter & Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    // 🔴 关键新增：username 的 Getter & Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}

