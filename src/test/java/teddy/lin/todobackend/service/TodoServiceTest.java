package teddy.lin.todobackend.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teddy.lin.todobackend.dto.RequestTodo;
import teddy.lin.todobackend.dto.ResponseTodo;
import teddy.lin.todobackend.exception.exceptions.IllegalUpdateIDException;
import teddy.lin.todobackend.exception.exceptions.NoSuchTodoException;
import teddy.lin.todobackend.exception.messages.ExceptionMessage;
import teddy.lin.todobackend.mapper.TodoMapper;
import teddy.lin.todobackend.model.Todo;
import teddy.lin.todobackend.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    private static final int ID = 1;
    @Mock
    private TodoRepository todoRepository;

    @Mock
    private TodoMapper todoMapper;

    @InjectMocks
    private TodoService todoService;

    @Test
    void should_return_all_todos_when_get_all_given_none() {
        //given
        Todo todo = new Todo(ID, "first todo", true);
        List<Todo> todos = singletonList(todo);
        List<ResponseTodo> responseTodoList = singletonList(new ResponseTodo(ID, "first todo", true));
        when(todoRepository.findAll()).thenReturn(todos);
        when(todoMapper.toResponseTodos(todos)).thenReturn(responseTodoList);

        //when
        List<ResponseTodo> responseTodos = todoService.getAll();

        //then
        assertEquals(1, responseTodos.size());
        assertEquals(responseTodoList.get(0).getId(), responseTodos.get(0).getId());
    }

    @Test
    void should_return_todo_when_save_given_todo() {
        //given
        Todo todo = new Todo(ID, "test", true);
        RequestTodo requestTodo = new RequestTodo(ID, "test", true);
        ResponseTodo expectedTodo = new ResponseTodo(ID, "test", true);
        when(todoMapper.toTodo(requestTodo)).thenReturn(todo);
        when(todoRepository.save(todo)).thenReturn(todo);
        when(todoMapper.toResponseTodo(todo)).thenReturn(expectedTodo);

        //when
        ResponseTodo responseTodo = todoService.save(requestTodo);

        //then
        assertEquals(requestTodo.getText(), responseTodo.getText());
        assertEquals(requestTodo.isStatus(), responseTodo.isStatus());
    }

    @Test
    void should_return_updated_todo_when_update_given_id() {
        //given
        Todo oldTodo = new Todo(ID, "test update", false);
        RequestTodo requestTodo = new RequestTodo(ID, "test", true);
        Todo newTodo = new Todo(ID, "test", true);
        ResponseTodo expectedTodo = new ResponseTodo(ID, "test", true);
        when(todoRepository.findById(ID)).thenReturn(Optional.of(oldTodo));
        when(todoMapper.toTodo(requestTodo)).thenReturn(newTodo);
        when(todoRepository.save(newTodo)).thenReturn(newTodo);
        when(todoMapper.toResponseTodo(newTodo)).thenReturn(expectedTodo);

        //when
        ResponseTodo responseTodo = todoService.update(ID, requestTodo);

        //then
        assertEquals(requestTodo.getId(), responseTodo.getId());
        assertEquals(requestTodo.getText(), responseTodo.getText());
        assertEquals(requestTodo.isStatus(), responseTodo.isStatus());
    }

    @Test
    void should_throw_illegal_update_id_exception_when_update_given_wrong_id() {
        //given
        int id = 10;
        RequestTodo requestTodo = new RequestTodo(ID, "test", true);

        //when
        Throwable exception = assertThrows(IllegalUpdateIDException.class, () ->
                todoService.update(id, requestTodo)
        );

        //then
        assertEquals(ExceptionMessage.illegal_Update_ID.getErrorMessage(),exception.getMessage());
    }

    @Test
    void should_throw_no_such_todo_exception_when_update_given_null_id() {
        //given
        RequestTodo requestTodo = new RequestTodo(ID, "test", true);
        when(todoRepository.findById(ID)).thenReturn(Optional.empty());

        //when
        Throwable exception = assertThrows(NoSuchTodoException.class, () ->
                todoService.update(ID, requestTodo)
        );

        //then
        assertEquals(ExceptionMessage.No_Such_Todo.getErrorMessage(), exception.getMessage());
    }

    @Test
    void should_delete_todo_when_delete_given_id() {
        //given
        Todo todo = new Todo(ID, "test", true);
        when(todoRepository.findById(ID)).thenReturn(Optional.of(todo));

        //when
        todoService.delete(ID);

        //then
        verify(todoRepository).delete(todo);
    }

    @Test
    void should_throw_no_such_todo_exception_when_delete_given_id() {
        //given
        when(todoRepository.findById(ID)).thenReturn(Optional.empty());

        //when
        Throwable exception = assertThrows(NoSuchTodoException.class, () -> todoService.delete(ID));

        //then
        assertEquals(ExceptionMessage.No_Such_Todo.getErrorMessage(), exception.getMessage());
    }
}
