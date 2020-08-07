package teddy.lin.todobackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import teddy.lin.todobackend.exception.exceptions.IllegalUpdateIDException;
import teddy.lin.todobackend.exception.exceptions.NoSuchTodoException;
import teddy.lin.todobackend.exception.messages.ExceptionMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchTodoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleNoSuchTodoException(){
        return ExceptionMessage.No_Such_Todo.getErrorMessage();
    }

    @ExceptionHandler(IllegalUpdateIDException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleIllegalUpdateIDException(){
        return ExceptionMessage.illegal_Update_ID.getErrorMessage();
    }
}
