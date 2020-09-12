package helloworld;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import helloworld.s3_lambda_sqs.Lambda;
import helloworld.s3_lambda_sqs.S3Bucket;

import static helloworld.constant.Constants.BUCKET_NAME_INPUT;
import static helloworld.constant.Constants.LAMBDA_FUNCTION_NAME;

public class HelloWord {
    public static void main(String[] args) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();


        Lambda.createLambdaFunction(LAMBDA_FUNCTION_NAME);
        S3Bucket.putObjectToBucket(s3, BUCKET_NAME_INPUT);
    }
}
