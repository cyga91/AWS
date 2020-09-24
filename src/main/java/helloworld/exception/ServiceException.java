package helloworld.exception;

public class ServiceException extends RuntimeException {

    public static final String CANNOT_CREATE_LAMBDA = "Lambda function was not created";

    public ServiceException(String message) {
        super(message);
    }
}
