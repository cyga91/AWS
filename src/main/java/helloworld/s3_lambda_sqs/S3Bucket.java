package helloworld.s3_lambda_sqs;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static helloworld.constant.Constants.BUCKET_FOUND;
import static helloworld.constant.Constants.CURRENT_BUCKET;
import static helloworld.constant.Constants.FILE_PATH;
import static helloworld.constant.Constants.KEY_NAME;
import static helloworld.constant.Constants.SUCCESS_WRITE_TO_S3;

public class S3Bucket {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = LoggerFactory.getLogger(S3Bucket.class);

    public static void putObjectToBucket(AmazonS3 s3, String buckeName) {
        Bucket bucket = createBucket(s3, buckeName);
        Objects.requireNonNull(bucket);

        try {
            s3.putObject(buckeName, KEY_NAME, new File(FILE_PATH));
        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
            System.exit(1);
        }
        logger.info(SUCCESS_WRITE_TO_S3);
    }

    public static Bucket createBucket(AmazonS3 s3, String buckeName) {
        Bucket b = null;
        if (s3.doesBucketExistV2(buckeName)) {
            b = getBucket(buckeName);
        } else {
            try {
                b = s3.createBucket(buckeName);
            } catch (AmazonS3Exception e) {
                logger.error(e.getErrorMessage());
                System.exit(1);
            }
        }
        logger.info(CURRENT_BUCKET + buckeName);
        return b;
    }

    public static Bucket getBucket(String bucketName) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();

        Bucket bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucketName)) {
                logger.info(BUCKET_FOUND + bucketName);
                bucket = b;
            }
        }
        return bucket;
    }
}
