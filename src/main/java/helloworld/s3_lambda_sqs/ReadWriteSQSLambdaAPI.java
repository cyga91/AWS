package helloworld.s3_lambda_sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import static helloworld.constant.Constants.SQS_RECEIVED_MESSAGE;

public class ReadWriteSQSLambdaAPI implements RequestHandler<SQSEvent, String> {
    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        LambdaLogger logger = context.getLogger();

        String result = null;

        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            result = msg.getBody();
            logger.log(SQS_RECEIVED_MESSAGE + result);
        }

        return "200 Ok";
    }
}
