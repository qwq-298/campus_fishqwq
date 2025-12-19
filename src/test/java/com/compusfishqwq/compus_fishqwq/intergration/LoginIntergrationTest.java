package com.compusfishqwq.compus_fishqwq.intergration;

import com.compusfishqwq.compus_fishqwq.entity.User;
import com.compusfishqwq.compus_fishqwq.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class LoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    /**
     * 每个测试前准备真实数据库数据
     */
    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("123456");
        user.setEmail("test@qq.com");
        user.setName("测试用户");
        userRepository.save(user);
    }

    /**
     * 集成测试 1：用户名 + 密码正确 → 登录成功
     */
    @Test
    void login_success() throws Exception {
        mockMvc.perform(post("/api/login")
                .param("username", "testuser")
                .param("password", "123456"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
    }

    /**
     * 集成测试 2：密码错误 → 登录失败
     */
    @Test
    void login_wrongPassword() throws Exception {
        mockMvc.perform(post("/api/login")
                .param("username", "testuser")
                .param("password", "wrong"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(false));
    }

    /**
     * 集成测试 3：用户不存在 → 登录失败
     */
    @Test
    void login_userNotExist() throws Exception {
        mockMvc.perform(post("/api/login")
                .param("username", "nobody")
                .param("password", "123456"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(false));
    }
}


