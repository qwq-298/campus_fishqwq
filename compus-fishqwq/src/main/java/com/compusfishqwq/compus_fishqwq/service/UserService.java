package com.compusfishqwq.compus_fishqwq.service;

import com.compusfishqwq.compus_fishqwq.entity.User;
import com.compusfishqwq.compus_fishqwq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * UserService
 * -----------------
 * 负责用户相关的业务逻辑，如注册、查询、删除等。
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 获取所有用户
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 根据ID获取用户
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 创建或更新用户
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 删除用户
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * 根据邮箱查找用户
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 根据姓名模糊查询
     */
    public List<User> searchUsersByName(String name) {
        return userRepository.findByNameContaining(name);
    }

    /**
     * 检查邮箱是否存在（注册校验）
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}


