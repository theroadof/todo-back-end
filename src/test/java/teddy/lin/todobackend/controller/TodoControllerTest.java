package teddy.lin.todobackend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import teddy.lin.todobackend.model.Todo;
import teddy.lin.todobackend.repository.TodoRepository;
import teddy.lin.todobackend.service.TodoService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private TodoRepository todoRepository;

    @AfterEach
    private void clearAll() {
        todoRepository.deleteAll();
    }

    @Test
    void should_return_response_todos_when_get_all_given() throws Exception {
        //given
        todoRepository.save(new Todo(ID, "test", true));

        //when
        mockMvc.perform(get(TODOS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void should_return_response_todo_when_add_given_request_todo() throws Exception {
        //given
        String requestTodo = "{\n" +
                "        \"id\": 71,\n" +
                "        \"text\": \"yes ok good fine \",\n" +
                "        \"status\": true\n" +
                "    }";

        //when
        mockMvc.perform(post(TODOS_URL).contentType(MediaType.APPLICATION_JSON)
                .content(requestTodo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber());

    }

    @Test
    void should_return_updated_response_to_when_update_given_id_and_request_todo() throws Exception {
        //given
        todoRepository.save(new Todo(ID,"test",true));
        List<Todo> todos = todoRepository.findAll();
        Todo todo = todos.get(0);
        String requestTodo = "{\n" +
                "        \"id\": "+todo.getId()+",\n" +
                "        \"text\": \"yes ok good fine \",\n" +
                "        \"status\": false\n" +
                "    }";

        //when
        mockMvc.perform(put(TODOS_URL+"/"+todo.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(requestTodo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todo.getId()))
                .andExpect(jsonPath("$.text").value("yes ok good fine "))
                .andExpect(jsonPath("$.status").value(false));

        //then
    }
}
