package com.eddie.springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        // param 의 값은 String 만 허용되기 때문에 숫자, 날짜 등의 데이터를 사용하려면 문자열로 변경해야한다.
        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.amount").value(amount));
    }
}
