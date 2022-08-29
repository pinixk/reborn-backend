package com.reborn.reborn.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
@SpringBootTest
public class TestController {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getMemberTest() throws Exception {

        mockMvc.perform(get("/member/{name}", "john"))
                .andDo(print())
                .andDo(document("getMember")); //test 수행시 snippets 로 생성해줌 ( 파일명 )
//                .andExpect(jsonPath("$.name", is("john")));
    }

}