package teddy.lin.todobackend.exception.exceptions;

public class IllegalUpdateIDException extends RuntimeException{
    public IllegalUpdateIDException(String errorMessage) {
        super(errorMessage);
    }
}
