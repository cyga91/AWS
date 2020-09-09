package helloworld.s3_lambda_sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Handler;

public class ReadWriteS3LambdaSQS implements RequestHandler<SQSEvent, String> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        return null;
    }
}
