package helloworld.constant;

import java.nio.file.Paths;

public final class Constants {
    // SQS (could be move to .yaml)
    public static final String QUEUE_NAME = "SQSS3Lambda";
    public static final int WITH_DELAY_SECONDS = 10;


    // S3 Bucket (could be move to .yaml)
    public static final String BUCKET_NAME_INPUT = "radekincloudbucketinput";
    public static final String BUCKET_NAME_OUTPUT = "radekincloudbucketoutput";
    public static final String BUCKET_OUTPUT_KEY = "HelloWorld-1.0.jar";
    public static final String FILE_PATH = "src/main/resources/simpleText.txt";
    public static final String KEY_NAME = Paths.get(FILE_PATH).getFileName().toString();

    // Lambda
    public static final String LAMBDA_FUNCTION_NAME = "rcLambdaS3SQS";
    public static final String LAMBDA_FUNCTION_ROLE = "arn:aws:iam::910682323108:role/aws-jdk-tool-HelloWorldFunctionRole-FQMTW660F1QY";
    public static final String LAMBDA_FUNCTION_RUNTIME = "java8";
    public static final String LAMBDA_FUNCTION_HANDLER = "helloworld.s3_lambda_sqs.ReadWriteS3LambdaSQS::handleRequest";

    // messages
    public static final String SUCCESS_WRITE_TO_S3 = "File has been written to S3 bucket!";
    public static final String CURRENT_BUCKET = "Current bucket is: ";
    public static final String BUCKET_FOUND = "Bucket found : ";

    public static final String MESSAGE = "Hello from message ";
    public static final String MESSAGE_SENT = "Message has been sent to queue";
}
