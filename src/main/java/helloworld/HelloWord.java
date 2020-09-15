package helloworld;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import helloworld.s3_lambda_sqs.Lambda;
import helloworld.s3_lambda_sqs.S3Bucket;
import helloworld.s3_lambda_sqs.Sqs;

import static helloworld.constant.Constants.BUCKET_NAME_OUTPUT;
import static helloworld.constant.Constants.FILE_NAME;
import static helloworld.constant.Constants.LAMBDA_FUNCTION_NAME;
import static helloworld.constant.Constants.QUEUE_OUTPUT_NAME;

public class HelloWord {
    public static void main(String[] args) {
        S3Bucket bucket = new S3Bucket(AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_1).build(), BUCKET_NAME_OUTPUT);
        Sqs output_queue = new Sqs(AmazonSQSClientBuilder.defaultClient(), QUEUE_OUTPUT_NAME);

        bucket.putObjectToBucket(FILE_NAME);
        output_queue.getAmazonSQS();
        Lambda.createLambdaFunction(LAMBDA_FUNCTION_NAME);
    }
}
