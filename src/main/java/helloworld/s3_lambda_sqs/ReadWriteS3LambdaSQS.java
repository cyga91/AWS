package helloworld.s3_lambda_sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

public class ReadWriteS3LambdaSQS implements RequestHandler<SQSEvent, String> {
    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        LambdaLogger logger = context.getLogger();

        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            logger.log(msg.getBody());
        }
        return null;
    }
}
