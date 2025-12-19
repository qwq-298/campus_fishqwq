package com.compusfishqwq.compus_fishqwq.intergration;

import com.compusfishqwq.compus_fishqwq.entity.User;
import com.compusfishqwq.compus_fishqwq.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    /**
     * 集成测试 1：
     * 已存在用户，成功发布商品
     */
    @Test
    void publishProduct_success_integration() throws Exception {
        // 1. 准备真实用户数据（进入数据库）
        User user = new User();
        user.setUsername("integration_user");
        user.setPassword("123456");
        user.setEmail("test@test.com");
        user.setName("卖家A");
        userRepository.save(user);

        // 2. 调用真实接口
        mockMvc.perform(post("/products/api/publish")
                .param("title", "Integration Test Product")
                .param("description", "integration test desc")
                .param("price", "100")
                .param("category", "test")
                .param("username", "integration_user"))
            // 3. 验证 HTTP 层
            .andExpect(status().isOk())
            // 4. 验证业务结果
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.msg").value("发布成功"))
            .andExpect(jsonPath("$.product.title").value("Integration Test Product"));
    }
}

