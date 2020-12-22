package helloworld.constant;

public final class Constants {
    // SQS
    public static final String QUEUE_INPUT_NAME = "lambdaSQSlambdaQueue";
    public static final int WITH_DELAY_SECONDS = 10;

    // messages
    public static final String MESSAGE_SENT_TO_QUEUE = "Message has been sent to queue ";

    public static final String SQS_RECEIVED_MESSAGE = "Message received from SQS: ";

    public static final String MESSAGE_SENT_TO_API = "Message has been sent to API ";

    // httpClient
    public static final String HEADER_NAME = "accept";
    public static final String HEADER_VALUE = "application/json";

    private Constants() {
        throw new AssertionError();
    }
}
