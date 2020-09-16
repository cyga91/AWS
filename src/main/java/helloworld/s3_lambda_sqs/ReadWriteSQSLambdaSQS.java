package helloworld.s3_lambda_sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import java.util.Objects;

import static helloworld.constant.Constants.QUEUE_INPUT_NAME;
import static helloworld.constant.Constants.SQS_RECEIVED_MESSAGE;

public class ReadWriteSQSLambdaSQS implements RequestHandler<SQSEvent, String> {
    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        LambdaLogger logger = context.getLogger();
        Sqs queue = new Sqs(AmazonSQSClientBuilder.defaultClient(), QUEUE_INPUT_NAME);

        String result = null;

        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            result = msg.getBody();
            logger.log(SQS_RECEIVED_MESSAGE + msg.getBody());
        }

        Objects.requireNonNull(result);
        String resultConverted = result.toUpperCase();

        queue.getAmazonSQS();
        queue.sendSQSMessage(resultConverted);

        return "200 Ok";
    }
}
