package com.example.tacocloud.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest // Web test for home controller
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc; // injects MockMvc

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()) // expects HTTP 200

                .andExpect(view().name("home")) // expects home view

                .andExpect(content().string(
                        containsString("Welcome to..."))); // expects Welcome to...
    }
}
