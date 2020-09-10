package helloworld;

import helloworld.s3_lambda_sqs.Lambda;
import helloworld.s3_lambda_sqs.S3Bucket;

import static helloworld.constant.Constants.LAMBDA_FUNCTION_NAME;

public class HelloWord {
    public static void main(String[] args) {
        Lambda.createLambdaFunction(LAMBDA_FUNCTION_NAME);
        S3Bucket.putObjectToBucket();
    }
}
