package com.compusfishqwq.compus_fishqwq.controller;

import com.compusfishqwq.compus_fishqwq.entity.Order;
import com.compusfishqwq.compus_fishqwq.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * OrderController
 * -----------------
 * 订单控制层，负责处理订单相关的 HTTP 请求。
 * 提供下单、查询、更新、删除等接口。
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 获取所有订单
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // 根据订单ID查询订单
    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // 根据订单号查询订单
    @GetMapping("/number/{orderNumber}")
    public Optional<Order> getOrderByNumber(@PathVariable String orderNumber) {
        return orderService.getOrderByNumber(orderNumber);
    }

    // 根据买家ID查询其所有订单
    @GetMapping("/buyer/{buyerId}")
    public List<Order> getOrdersByBuyer(@PathVariable Long buyerId) {
        return orderService.getOrdersByBuyer(buyerId);
    }

    // 根据状态查询订单
    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable String status) {
        return orderService.getOrdersByStatus(status);
    }

    // 根据时间范围查询订单（格式：yyyy-MM-dd）
    @GetMapping("/range")
    public List<Order> getOrdersByDateRange(@RequestParam Date start, @RequestParam Date end) {
        return orderService.getOrdersByDateRange(start, end);
    }

    // 创建订单（传入 buyerId 和 productId）
    @PostMapping("/create")
    public Order createOrder(@RequestParam Long buyerId, @RequestParam Long productId) {
        return orderService.createOrder(buyerId, productId);
    }

    // 更新订单状态
    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    // 删除订单
    @DeleteMapping("/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return "Order " + orderId + " deleted successfully.";
    }

    // 查询用户总消费额（仅统计状态为 PAID 的订单）
    @GetMapping("/buyer/{buyerId}/total")
    public BigDecimal getTotalSpentByUser(@PathVariable Long buyerId) {
        return orderService.calculateTotalSpentByUser(buyerId);
    }
}
/*✅ 该控制器提供的 REST API 一览：
HTTP 方法	路径	功能说明
GET	/orders	获取所有订单
GET	/orders/{id}	根据 ID 获取订单
GET	/orders/number/{orderNumber}	根据订单号查询
GET	/orders/buyer/{buyerId}	查询某用户的所有订单
GET	/orders/status/{status}	查询某状态的订单
GET	/orders/range?start=2025-01-01&end=2025-12-31	查询时间范围内的订单
POST	/orders/create?buyerId=1&productId=3	创建新订单
PUT	/orders/{orderId}/status?status=PAID	更新订单状态
DELETE	/orders/{orderId}	删除订单
GET	/orders/buyer/{buyerId}/total	查询用户总消费金额 */
