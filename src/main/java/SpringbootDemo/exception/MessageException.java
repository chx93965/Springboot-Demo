package SpringbootDemo.exception;

public class MessageException extends RuntimeException {
    protected final ErrorMessage errorMessage;

    public MessageException(ErrorMessage errorMessage) {
        super(errorMessage.getError());
        this.errorMessage = errorMessage;
    }

    public MessageException(ErrorMessage errorMessage, Throwable throwable) {
        super(errorMessage.getError(), throwable);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
