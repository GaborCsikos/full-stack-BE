package com.gaborcsikos.fullstack;

import com.gaborcsikos.fullstack.todo.Priority;
import com.gaborcsikos.fullstack.todo.Todo;
import com.gaborcsikos.fullstack.todo.TodoRestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(
        scripts = {"clean_todos.sql", "add_todos.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TodoTestIT extends AbstractIT {

    @Autowired
    private TodoRestRepository todoRestRepository;

    @Test
    void getById() throws Exception {
        mockMvc
                .perform(RestDocumentationRequestBuilders.get("/api/todo/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.description").value("Added from test"))
                .andExpect(jsonPath("$.priority").value("LOW"))
                .andExpect(jsonPath("$.createdDate").isNotEmpty())
                .andExpect(jsonPath("$.modifiedDate").isNotEmpty())
                .andDo(document("{class-name}/{method-name}", prettyPrintRequest(), prettyPrintResponse()));
    }

    @Test
    void getAll() throws Exception {
        mockMvc
                .perform(RestDocumentationRequestBuilders.get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.todo.length()").value(1))
                .andExpect(jsonPath("$._embedded.todo[0].id").value(1L))
                .andExpect(jsonPath("$._embedded.todo[0].name").value("test"))
                .andExpect(jsonPath("$._embedded.todo[0].description").value("Added from test"))
                .andExpect(jsonPath("$._embedded.todo[0].priority").value("LOW"))
                .andExpect(jsonPath("$._embedded.todo[0].createdDate").isNotEmpty())
                .andExpect(jsonPath("$._embedded.todo[0].modifiedDate").isNotEmpty())
                .andDo(document("{class-name}/{method-name}", prettyPrintRequest(), prettyPrintResponse()));
    }

    @Test
    void getByIdNoTodo() throws Exception {
        mockMvc
                .perform(RestDocumentationRequestBuilders.get("/api/todo/{id}", 10))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void create() throws Exception {
        Todo todo = Todo.builder().name("New name").priority(Priority.HIGH).description("no description").build();
        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.post("/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("New name"))
                .andExpect(jsonPath("$.description").value("no description"))
                .andExpect(jsonPath("$.priority").value("HIGH"))
                .andExpect(jsonPath("$.createdDate").isNotEmpty())
                .andExpect(jsonPath("$.modifiedDate").isNotEmpty())
                .andDo(document("{class-name}/{method-name}", prettyPrintRequest(), prettyPrintResponse()));
    }


    @Test
    void update() throws Exception {
        Todo todo = Todo.builder().id(1L).name("New name").priority(Priority.HIGH).description("no description").build();
        todoRestRepository.findAll();
        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.put("/api/todo/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New name"))
                .andExpect(jsonPath("$.description").value("no description"))
                .andExpect(jsonPath("$.priority").value("HIGH"))
                .andExpect(jsonPath("$.createdDate").isNotEmpty())
                .andExpect(jsonPath("$.modifiedDate").isNotEmpty())
                .andDo(document("{class-name}/{method-name}", prettyPrintRequest(), prettyPrintResponse()));
    }


    @Test
    void delete() throws Exception {
        mockMvc
                .perform(RestDocumentationRequestBuilders.delete("/api/todo/{id}", 1))
                .andExpect(status().is(204)) //returns 204
                .andDo(
                        document(
                                "{class-name}/{method-name}",
                                prettyPrintRequest(),
                                prettyPrintResponse(),
                                pathParameters(parameterWithName("id").description("ID of the Todo")))); //Example how documenting would work
    }
}
