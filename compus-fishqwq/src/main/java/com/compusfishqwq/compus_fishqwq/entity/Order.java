package com.compusfishqwq.compus_fishqwq.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders") // 避免与 SQL 关键字冲突
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 订单ID

    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber; // 订单编号（UUID或时间戳生成）

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer; // 买家

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 关联商品

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount; // 实际支付金额

    @Column(length = 20)
    private String status = "PENDING"; // 状态（PENDING/PAID/SHIPPED/FINISHED/CANCELLED）

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime = new Date(); // 下单时间

    // --- Getter & Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public User getBuyer() { return buyer; }
    public void setBuyer(User buyer) { this.buyer = buyer; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}