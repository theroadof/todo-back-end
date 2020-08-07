package teddy.lin.todobackend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import teddy.lin.todobackend.repository.TodoRepository;
import teddy.lin.todobackend.service.TodoService;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class TodoControllerTest {
    private static final int ID = 1;
    private static final String TODOS_URL = "/todos";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @AfterEach
    private void clearAll() {
        todoRepository.deleteAll();
    }

    @Test
    void should_return_response_todos_when_get_all_given() throws Exception {
        //when
        mockMvc.perform(get(TODOS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }
}
