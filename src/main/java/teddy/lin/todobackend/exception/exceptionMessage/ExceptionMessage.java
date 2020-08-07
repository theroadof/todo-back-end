package teddy.lin.todobackend.exception.exceptionMessage;

public enum ExceptionMessage {
    No_Such_Todo("No Such TodoM");

    private String errorMessage;

    ExceptionMessage(String errorMessage){
        this.errorMessage =errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }
}
