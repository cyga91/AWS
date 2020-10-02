package helloworld.exception;

public class AmazonServiceException extends RuntimeException {

    public static final String CANNOT_PUT_TO_BUCKET = "Cannot put object to S3Bucket";
    public static final String CANNOT_CREATE_BUCKET = "Cannot create S3Bucket";
    public static final String CANNOT_FIND_BUCKET = "Cannot find S3Bucket with name ";

    public AmazonServiceException(String message) {
        super(message);
    }
}
