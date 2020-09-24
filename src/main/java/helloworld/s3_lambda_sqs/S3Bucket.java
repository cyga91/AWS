package helloworld.s3_lambda_sqs;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static helloworld.constant.Constants.BUCKET_FOUND;
import static helloworld.constant.Constants.CURRENT_BUCKET;
import static helloworld.constant.Constants.FILE_PATH;
import static helloworld.constant.Constants.SUCCESS_WRITE_TO_S3;
import static helloworld.exception.AmazonServiceException.CANNOT_CREATE_BUCKET;
import static helloworld.exception.AmazonServiceException.CANNOT_FIND_BUCKET;
import static helloworld.exception.AmazonServiceException.CANNOT_PUT_TO_BUCKET;

public class S3Bucket {
    @NonNull
    private final AmazonS3 s3;
    @NonNull
    private final String BUCKET_NAME;

    public S3Bucket(AmazonS3 s3, String BUCKET_NAME) {
        this.s3 = s3;
        this.BUCKET_NAME = BUCKET_NAME;
    }

    private static final Logger logger = LoggerFactory.getLogger(S3Bucket.class);

    public void putObjectToBucket(String fileName) {
        Bucket bucket = checkBucket();
        Objects.requireNonNull(bucket);

        try {
            s3.putObject(BUCKET_NAME, fileName, new File(FILE_PATH));
        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
            throw new AmazonServiceException(CANNOT_PUT_TO_BUCKET);
        }
        logger.info(SUCCESS_WRITE_TO_S3);
    }

    public Bucket checkBucket() {
        Bucket b = null;
        if (s3.doesBucketExistV2(BUCKET_NAME)) {
            b = getBucket(BUCKET_NAME);
            logger.info(BUCKET_FOUND + BUCKET_NAME);
        } else {
            try {
                b = s3.createBucket(BUCKET_NAME);
            } catch (AmazonS3Exception e) {
                logger.error(e.getErrorMessage());
                throw new AmazonS3Exception(CANNOT_CREATE_BUCKET);
            }
        }
        logger.info(CURRENT_BUCKET + BUCKET_NAME);
        return b;
    }

    public Bucket getBucket(String bucketName) {
        List<Bucket> buckets = s3.listBuckets();
        return buckets.stream()
                .filter(awsBucket -> awsBucket.getName().equals(bucketName))
                .findFirst()
                .orElseThrow(() -> new AmazonS3Exception(CANNOT_FIND_BUCKET + bucketName));
    }
}
