package com.gaborcsikos.fullstack;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ActuatorIT extends AbstractIT {

    @Test
    void health() throws Exception {
        mockMvc
                .perform(RestDocumentationRequestBuilders.get("/actuator/health"))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}", prettyPrintRequest(), prettyPrintResponse()));
    }

    @Test
    void info() throws Exception {
        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.get("/actuator/info"))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}", prettyPrintRequest(), prettyPrintResponse()));
    }

    @Test
    void mapping() throws Exception {
        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.get("/actuator/mappings"))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}", prettyPrintRequest(), prettyPrintResponse()));
    }
}
