package com.eddie.springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @Runwith(SpringRunner.class) is to be @ExtendWith(SpringExtension.class)
@ExtendWith(SpringExtension.class) // 공부
@WebMvcTest(controllers = HelloController.class) // 공부
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    // 아래 코드에 대해서 한 줄 한 줄 남겨두기 (내가 나중에 기억하기 위해)
    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }
}
