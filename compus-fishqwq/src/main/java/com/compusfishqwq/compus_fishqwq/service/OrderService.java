package com.compusfishqwq.compus_fishqwq.service;

import com.compusfishqwq.compus_fishqwq.entity.Order;
import com.compusfishqwq.compus_fishqwq.entity.User;
import com.compusfishqwq.compus_fishqwq.entity.Product;
import com.compusfishqwq.compus_fishqwq.repository.OrderRepository;
import com.compusfishqwq.compus_fishqwq.repository.ProductRepository;
import com.compusfishqwq.compus_fishqwq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> getOrderByNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<Order> getOrdersByBuyer(Long buyerId) {
        Optional<User> buyer = userRepository.findById(buyerId);
        return buyer.map(orderRepository::findByBuyer).orElse(List.of());
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> getOrdersByDateRange(Date start, Date end) {
        return orderRepository.findByCreateTimeBetween(start, end);
    }

    public Order createOrder(Long buyerId, Long productId) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setBuyer(buyer);
        order.setProduct(product);
        order.setAmount(product.getPrice());
        order.setStatus("PENDING");
        order.setCreateTime(new Date());

        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    public BigDecimal calculateTotalSpentByUser(Long buyerId) {
        Optional<User> buyer = userRepository.findById(buyerId);
        if (buyer.isEmpty()) return BigDecimal.ZERO;

        List<Order> orders = orderRepository.findByBuyerAndStatus(buyer.get(), "PAID");
        return orders.stream()
                .map(Order::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
/*功能	方法
获取所有订单	getAllOrders()
通过ID查询订单	getOrderById()
通过订单号查询	getOrderByNumber()
根据买家ID获取订单	getOrdersByBuyer()
创建新订单	createOrder(buyerId, productId)
更新订单状态	updateOrderStatus(orderId, newStatus)
删除订单	deleteOrder(orderId)
计算用户总消费额	calculateTotalSpentByUser(buyerId) */
