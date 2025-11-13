package com.compusfishqwq.compus_fishqwq.repository;

import com.compusfishqwq.compus_fishqwq.entity.Order;
import com.compusfishqwq.compus_fishqwq.entity.User;
import com.compusfishqwq.compus_fishqwq.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByBuyer(User buyer);

    List<Order> findByProduct(Product product);

    List<Order> findByStatus(String status);

    List<Order> findByBuyerAndStatus(User buyer, String status);

    List<Order> findByCreateTimeBetween(Date start, Date end);

    Optional<Order> findByOrderNumber(String orderNumber);

    long countByBuyer(User buyer);

    @Query("SELECT o FROM Order o JOIN FETCH o.buyer b JOIN FETCH o.product p WHERE b.id = :userId")
    List<Order> findOrdersWithBuyerAndProductByUserId(Long userId);
}
