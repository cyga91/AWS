package helloworld.exception;

public class UnsupportedException extends UnsupportedOperationException {

    public static final String ORDER_WITH_NO_ID = "This order has no id";

    public UnsupportedException(String message) {
        super(message);
    }
}
