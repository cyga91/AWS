package helloworld.s3_lambda;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Handler;

public class ReadWriteS3Lambda implements RequestHandler<S3Event, String> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    @Override
    public String handleRequest(S3Event s3event, Context context) {
        S3EventNotification.S3EventNotificationRecord record = s3event.getRecords().get(0);
        String bucketNameOutput = record.getS3().getBucket().getName();
        String fileKeyOutput = record.getS3().getObject().getUrlDecodedKey();

        String bucketNameInput = "radekincloudbucketinput";
        String file_name = "instr.txt";
        StringBuilder result = new StringBuilder();

        // read from S3
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        try (final S3Object s3Object = s3Client.getObject(bucketNameOutput,
                fileKeyOutput);
             final InputStreamReader streamReader = new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8);
             final BufferedReader reader = new BufferedReader(streamReader)) {
            reader.lines()
                    .forEach(result::append);
        } catch (final IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("File received from S3");

        // validate
        String fileKeyInput = result.toString();
        String validatedFileKeyInput = fileKeyInput.toUpperCase();

        // write do S3
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();
        try {
            s3.putObject(bucketNameInput, file_name, validatedFileKeyInput);
        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
        }
        logger.info("File saved in S3");
        return "Ok";
    }
}
