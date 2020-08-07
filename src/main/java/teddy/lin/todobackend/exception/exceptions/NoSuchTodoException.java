package teddy.lin.todobackend.exception.exceptions;

public class NoSuchTodoException extends RuntimeException {
    public NoSuchTodoException(String errorMessage) {
        super(errorMessage);
    }
}
