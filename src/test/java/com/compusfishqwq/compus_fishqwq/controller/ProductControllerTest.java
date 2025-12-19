package com.compusfishqwq.compus_fishqwq.controller;

import com.compusfishqwq.compus_fishqwq.entity.User;
import com.compusfishqwq.compus_fishqwq.service.ProductService;
import com.compusfishqwq.compus_fishqwq.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false) 
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductService productService;
    
    @Test
    void testPublishProduct_userNotExist() throws Exception {
    Mockito.when(userRepository.findByUsername("ghost"))
           .thenReturn(Optional.empty());

    mockMvc.perform(post("/products/api/publish")
            .param("title", "test product")
            .param("description", "desc")
            .param("price", "99.9")
            .param("category", "book")
            .param("username", "ghost"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.msg").value("用户不存在"));
    }

    @Test
    void testPublishProduct_success_withoutImages() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setUsername("alice");

    Mockito.when(userRepository.findByUsername("alice"))
           .thenReturn(Optional.of(user));

    mockMvc.perform(post("/products/api/publish")
            .param("title", "Java Book")
            .param("description", "good book")
            .param("price", "88.8")
            .param("category", "book")
            .param("username", "alice"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.msg").value("发布成功"))
        .andExpect(jsonPath("$.product.title").value("Java Book"))
        .andExpect(jsonPath("$.product.user.username").value("alice"));
    }

    @Test
    void testPublishProduct_success_withImages() throws Exception {
    User user = new User();
    user.setId(2L);
    user.setUsername("bob");

    Mockito.when(userRepository.findByUsername("bob"))
           .thenReturn(Optional.of(user));

    mockMvc.perform(post("/products/api/publish")
            .param("title", "Phone")
            .param("description", "new phone")
            .param("price", "1999")
            .param("category", "digital")
            .param("username", "bob")
            .param("images", "a.jpg,b.jpg"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.product.images").isArray())
        .andExpect(jsonPath("$.product.images.length()").value(2));
    }
}