package helloworld.lambda1step;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import helloworld.exception.UnsupportedException;
import lombok.extern.slf4j.Slf4j;

import static helloworld.constant.Constants.MESSAGE_SENT_TO_QUEUE;
import static helloworld.constant.Constants.QUEUE_INPUT_NAME;
import static helloworld.constant.Constants.SQS_RECEIVED_MESSAGE;
import static helloworld.constant.Constants.WITH_DELAY_SECONDS;
import static helloworld.exception.UnsupportedException.ORDER_WITH_NO_ID;

@Slf4j
public class Lambda1stStep implements RequestHandler<SQSEvent, String> {

    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        String result = sqsEvent.getRecords().get(0).getBody();
        log.debug(SQS_RECEIVED_MESSAGE + result);

        if (!result.contains("orderId")) {
            throw new UnsupportedException(ORDER_WITH_NO_ID);
        }
        sendSQSMessage(result);

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
        log.debug(MESSAGE_SENT_TO_QUEUE + message);
    }
}
