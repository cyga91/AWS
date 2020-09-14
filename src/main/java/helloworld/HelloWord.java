package helloworld;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import helloworld.s3_lambda_sqs.Lambda;
import helloworld.s3_lambda_sqs.S3Bucket;
import helloworld.s3_lambda_sqs.Sqs;

import static helloworld.constant.Constants.BUCKET_NAME_OUTPUT;
import static helloworld.constant.Constants.LAMBDA_FUNCTION_NAME;
import static helloworld.constant.Constants.QUEUE_OUTPUT_NAME;

public class HelloWord {
    public static void main(String[] args) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        S3Bucket.putObjectToBucket(s3, BUCKET_NAME_OUTPUT);
        Sqs.getAmazonSQS(sqs, QUEUE_OUTPUT_NAME);
        Lambda.createLambdaFunction(LAMBDA_FUNCTION_NAME);
    }
}
