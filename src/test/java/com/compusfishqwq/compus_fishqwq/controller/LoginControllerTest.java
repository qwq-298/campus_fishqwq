package com.compusfishqwq.compus_fishqwq.controller;

import com.compusfishqwq.compus_fishqwq.service.LoginService;
import com.compusfishqwq.compus_fishqwq.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false) 
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    void contextLoads() throws Exception {
        Mockito.when(loginService.checkLogin("test", "123"))
               .thenReturn(false);

        mockMvc.perform(post("/api/login")
                .param("username", "test")
                .param("password", "123"))
            .andExpect(status().isOk());
    }
    @Test
    void testLoginFail() throws Exception {
    Mockito.when(loginService.checkLogin("user", "wrong"))
           .thenReturn(false);

    mockMvc.perform(post("/api/login")
            .param("username", "user")
            .param("password", "wrong"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.msg").value("用户名或密码错误"));
    }
    @Test
    void testLoginSuccess() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("张三");
    user.setEmail("test@test.com");

    Mockito.when(loginService.checkLogin("user1", "123"))
           .thenReturn(true);
    Mockito.when(loginService.getUser("user1", "123"))
           .thenReturn(user);

    mockMvc.perform(post("/api/login")
            .param("username", "user1")
            .param("password", "123"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.msg").value("登录成功"))
        .andExpect(jsonPath("$.user.name").value("张三"))
        .andExpect(jsonPath("$.user.email").value("test@test.com"));
    } 
    @Test
   void testEmptyUsername() throws Exception {
    mockMvc.perform(post("/api/login")
            .param("username", "")
            .param("password", "123"))
        .andExpect(status().isBadRequest());
    }

   @Test
   void testEmptyPassword() throws Exception {
    mockMvc.perform(post("/api/login")
            .param("username", "user")
            .param("password", ""))
        .andExpect(status().isBadRequest());
    }
    

}
