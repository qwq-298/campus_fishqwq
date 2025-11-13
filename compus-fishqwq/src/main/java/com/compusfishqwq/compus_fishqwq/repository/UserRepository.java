package com.compusfishqwq.compus_fishqwq.repository;

import com.compusfishqwq.compus_fishqwq.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 根据邮箱查找用户
    Optional<User> findByEmail(String email);

    // 根据名称模糊搜索用户
    List<User> findByNameContaining(String name);

    // 检查邮箱是否存在（可用于注册时查重）
    boolean existsByEmail(String email);
}