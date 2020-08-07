package teddy.lin.todobackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import teddy.lin.todobackend.exception.exceptionMessage.ExceptionMessage;
import teddy.lin.todobackend.exception.exceptions.NoSuchTodoException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchTodoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleNoSuchTodoException(){
        return ExceptionMessage.No_Such_Todo.getErrorMessage();
    }
}
