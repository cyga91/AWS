package helloworld.constant;

import java.nio.file.Paths;

public final class Constants {
    public static final String QUEUE_NAME = "SQSS3Lambda";
    public static final int WITH_DELAY_SECONDS = 10;

    public static final String MESSAGE = "Hello from message ";
    public static final String MESSAGE_SENT = "Message has been sent to queue";

    public static final String BUCKET_NAME = "radekincloudbucketinput";
    public static final String FILE_PATH = "src/main/resources/simpleText.txt";
    public static final String KEY_NAME = Paths.get(FILE_PATH).getFileName().toString();

    public static final String SUCCESS_WRITE_TO_S3 = "File has been written to S3 bucket!";
    public static final String CURRENT_BUCKET = "Current bucket is: ";
    public static final String BUCKET_FOUND = "Bucket found : ";
}
