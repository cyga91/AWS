package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import static helloworld.constant.Constants.MESSAGE_SENT_TO_QUEUE;
import static helloworld.constant.Constants.QUEUE_INPUT_NAME;
import static helloworld.constant.Constants.SQS_RECEIVED_MESSAGE;
import static helloworld.constant.Constants.WITH_DELAY_SECONDS;

public class ReadWriteSQSLambdaSQS implements RequestHandler<SQSEvent, String> {
    private LambdaLogger logger;

    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        logger = context.getLogger();

        String result = sqsEvent.getRecords().get(0).getBody();
        logger.log(SQS_RECEIVED_MESSAGE + result);

        String resultConverted = result.toUpperCase();
        sendSQSMessage(resultConverted);

        return "200 Ok";
    }

    private void sendSQSMessage(String message) {
        final AmazonSQS queue = AmazonSQSClientBuilder.defaultClient();
        String queueUrl = queue.getQueueUrl(QUEUE_INPUT_NAME).getQueueUrl();

        SendMessageRequest SEND_MSG_REQUEST = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message)
                .withDelaySeconds(WITH_DELAY_SECONDS);
        queue.sendMessage(SEND_MSG_REQUEST);
        logger.log(MESSAGE_SENT_TO_QUEUE + message);
    }
}
