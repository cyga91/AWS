package helloworld;

import helloworld.s3_lambda_sqs.S3Bucket;

public class HelloWord {
    public static void main(String[] args) {
        S3Bucket.putObjectToBucket();
    }
}
