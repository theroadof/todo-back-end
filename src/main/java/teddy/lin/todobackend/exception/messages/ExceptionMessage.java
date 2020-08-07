package teddy.lin.todobackend.exception.messages;

public enum ExceptionMessage {
    No_Such_Todo("No Such Todo"),
    illegal_Update_ID("illegalUpdateID");

    private String errorMessage;

    ExceptionMessage(String errorMessage){
        this.errorMessage =errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }
}
